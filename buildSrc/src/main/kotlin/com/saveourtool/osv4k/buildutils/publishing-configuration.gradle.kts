package com.saveourtool.osv4k.buildutils

import io.github.gradlenexus.publishplugin.NexusPublishExtension
import io.github.gradlenexus.publishplugin.NexusPublishPlugin
import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutput.Style.Failure
import org.gradle.internal.logging.text.StyledTextOutput.Style.Identifier
import org.gradle.internal.logging.text.StyledTextOutput.Style.Info
import org.gradle.internal.logging.text.StyledTextOutput.Style.Success
import org.gradle.internal.logging.text.StyledTextOutputFactory
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
}

group = rootProject.group
version = rootProject.version

configurePublishing()

/**
 * Configures all aspects of the publishing process.
 */
fun Project.configurePublishing() {
    configureNexusPublishing()
    configureGitHubPublishing()
    configurePublications()
    configureSigning()

    // https://kotlinlang.org/docs/mpp-publish-lib.html#avoid-duplicate-publications
    afterEvaluate {
        val publicationsFromMainHost = listOf(
            "jvm",
            "js",
            "linuxX64", "mingwX64", "macosX64",
            "kotlinMultiplatform", "metadata",
        )
        configure<PublishingExtension> {
            publications.matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach {
                        onlyIf {
                            // main publishing CI job is executed on Linux host
                            DefaultNativePlatform.getCurrentOperatingSystem().isLinux.apply {
                                if (!this) {
                                    logger.lifecycle("Publication ${(it as AbstractPublishToMaven).publication.name} is skipped on current host")
                                }
                            }
                        }
                    }
            }
        }
    }
}

/**
 * Configures Maven Central as the publish destination.
 */
@Suppress("TOO_LONG_FUNCTION")
fun Project.configureNexusPublishing() {
    System.getenv("OSSRH_USERNAME")?.let { sonatypeUsername ->
        extra.set("sonatypeUsername", sonatypeUsername)
    }
    System.getenv("OSSRH_PASSWORD")?.let { sonatypePassword ->
        extra.set("sonatypePassword", sonatypePassword)
    }

    if (!hasProperties("sonatypeUsername", "sonatypePassword")) {
        styledOut(logCategory = "nexus")
            .style(Info)
            .text("Skipping Nexus publishing configuration as either ")
            .style(Identifier)
            .text("sonatypeUsername")
            .style(Info)
            .text(" or ")
            .style(Identifier)
            .text("sonatypePassword")
            .style(Info)
            .text(" are not set")
            .println()
        return
    }

    apply<NexusPublishPlugin>()

    configure<NexusPublishExtension> {
        this@configure.repositories {
            sonatype {
                /*
                 * The default is https://oss.sonatype.org/service/local/.
                 */
                nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
                /*
                 * The default is https://oss.sonatype.org/content/repositories/snapshots/.
                 */
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
                username.set(property("sonatypeUsername") as String)
                password.set(property("sonatypePassword") as String)
            }
        }
    }
}

/**
 * Configures GitHub Packages as the publish destination.
 */
fun Project.configureGitHubPublishing(): Unit =
        publishing {
            repositories {
                maven {
                    name = "GitHub"
                    url = uri("https://maven.pkg.github.com/saveourtool/osv4k")
                    credentials {
                        username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                        password = findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }

/**
 * Configures all publications. The publications must already exist.
 */
@Suppress("TOO_LONG_FUNCTION")
fun Project.configurePublications() {
    @Suppress("GENERIC_VARIABLE_WRONG_DECLARATION")
    val dokkaJarProvider = tasks.register<Jar>("dokkaJar") {
        group = "documentation"
        archiveClassifier.set("javadoc")
        from(tasks.findByName("dokkaHtml"))
    }
    configure<PublishingExtension> {
        publications.withType<MavenPublication>().configureEach {
            artifact(dokkaJarProvider)
            pom {
                name.set(project.name)
                description.set(project.description ?: project.name)
                url.set("https://github.com/saveourtool/osv4k")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("nulls")
                        name.set("Nariman Abdullin")
                        email.set("nulls.narik@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/saveourtool/osv4k")
                    connection.set("scm:git:https://github.com/saveourtool/osv4k.git")
                    developerConnection.set("scm:git:git@github.com:saveourtool/osv4k.git")
                }
            }
        }
    }
}

/**
 * Enables signing of the artifacts if the `signingKey` project property is set.
 *
 * Should be explicitly called after each custom `publishing {}` section.
 */
fun Project.configureSigning() {
    System.getenv("GPG_SEC")?.let {
        extra.set("signingKey", it)
    }
    System.getenv("GPG_PASSWORD")?.let {
        extra.set("signingPassword", it)
    }

    if (hasProperty("signingKey")) {
        /*
         * GitHub Actions.
         */
        configureSigningCommon {
            useInMemoryPgpKeys(property("signingKey") as String?, findProperty("signingPassword") as String?)
        }
    } else if (
        hasProperties(
            "signing.keyId",
            "signing.password",
            "signing.secretKeyRingFile",
        )
    ) {
        /*-
         * Pure-Java signing mechanism via `org.bouncycastle.bcpg`.
         *
         * Requires an 8-digit (short form) PGP key id and a present `~/.gnupg/secring.gpg`
         * (for gpg 2.1, run
         * `gpg --keyring secring.gpg --export-secret-keys >~/.gnupg/secring.gpg`
         * to generate one).
         */
        configureSigningCommon()
    } else if (hasProperty("signing.gnupg.keyName")) {
        /*-
         * Use an external `gpg` executable.
         *
         * On Windows, you may need to additionally specify the path to `gpg` via
         * `signing.gnupg.executable`.
         */
        configureSigningCommon {
            useGpgCmd()
        }
    }
}

/**
 * @param useKeys the block which configures the PGP keys. Use either
 *   [SigningExtension.useInMemoryPgpKeys], [SigningExtension.useGpgCmd], or an
 *   empty lambda.
 * @see SigningExtension.useInMemoryPgpKeys
 * @see SigningExtension.useGpgCmd
 */
@Suppress(
    "MaxLineLength",
    "SpreadOperator",
)
fun Project.configureSigningCommon(useKeys: SigningExtension.() -> Unit = {}) {
    configure<SigningExtension> {
        useKeys()
        val publications = extensions.getByType<PublishingExtension>().publications
        val publicationCount = publications.size
        val message = "The following $publicationCount publication(s) are getting signed: ${publications.map(Named::getName)}"
        val style = when (publicationCount) {
            0 -> Failure
            else -> Success
        }
        styledOut(logCategory = "signing").style(style).println(message)
        sign(*publications.toTypedArray())
    }
}

/**
 * Creates a styled text output.
 *
 * @param logCategory
 * @return [StyledTextOutput]
 */
fun Project.styledOut(logCategory: String): StyledTextOutput =
        serviceOf<StyledTextOutputFactory>().create(logCategory)

/**
 * Determines if this project has all the given properties.
 *
 * @param propertyNames the names of the properties to locate.
 * @return `true` if this project has all the given properties, `false` otherwise.
 * @see Project.hasProperty
 */
fun Project.hasProperties(vararg propertyNames: String): Boolean =
        propertyNames.asSequence().all(this::hasProperty)
