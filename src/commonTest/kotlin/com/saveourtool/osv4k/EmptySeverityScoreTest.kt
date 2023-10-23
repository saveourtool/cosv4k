@file:Suppress("LONG_LINE", "TOO_LONG_FUNCTION")

package com.saveourtool.osv4k

import com.saveourtool.osv4k.OsvSchemaTestUtil.doEncodeDecodeAndCompare

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFails
import kotlinx.serialization.json.Json

class EmptySeverityScoreTest {
    @Test
    fun cosvWithEmptySeverityScore() {
        doEncodeDecodeAndCompare(cosvWithEmptySeverityScoreJson)
    }

    @Test
    fun cosvWithMissingSeverityScore() {
        val exception = assertFails {
            Json.decodeFromString<RawOsvSchema>(cosvWithMissingSeverityScoreJson)
        }
        assertContains(exception.message.orEmpty(), "Field 'score' is required for type with serial name 'com.saveourtool.osv4k.Severity'")
    }

    companion object {
        // language=JSON
        private val cosvWithEmptySeverityScoreJson = """
            {
              "schema_version": "1.4.0",
              "id": "GHSA-cr45-98w9-gwqx",
              "modified": "2023-10-19T19:32:39Z",
              "published": "2023-10-19T16:13:50Z",
              "aliases": [
                "CVE-2023-45815"
              ],
              "summary": "Viewing wget extractor output while logged in as an admin allows archived JS to execute in the admins context",
              "details": "### Impact\n\nAny users who are using the `wget` extractor and view the content it outputs. \n\nThe impact is potentially severe if you are logged in to the ArchiveBox admin site in the same browser session and view an archived malicious page designed to target your ArchiveBox instance. Malicious JS could potentially act using your logged-in admin credentials and add/remove/modify snapshots, add/remove/modify ArchiveBox users, and generally do anything an admin user could do. \n\nThe impact is less severe for non-logged-in users, as malicious JS cannot *modify* any archives, but it can still *read* all the other archived content by fetching the snapshot index and iterating through it.\n\nBecause all of ArchiveBox's archived content is served from the same host and port as the admin panel, when archived pages are viewed the JS executes in the same context as all the other archived pages (and the admin panel), defeating most of the browser's usual CORS/CSRF security protections and leading to this issue.\n\n### Patches\n\nFollow here for progress on mitigating this issue: https://github.com/ArchiveBox/ArchiveBox/issues/239\n\n### Workarounds\n\nDisable the wget extractor by setting [`archivebox config --set SAVE_WGET=False`](https://github.com/ArchiveBox/ArchiveBox/wiki/Configuration#save_wget), ensure you are always logged out, or serve only a [static HTML version](https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#2-export-and-host-it-as-static-html) of your archive.\n\n### References\n\n- https://en.wikipedia.org/wiki/Cross-site_request_forgery\n- https://github.com/ArchiveBox/ArchiveBox#caveats\n- https://github.com/ArchiveBox/ArchiveBox/wiki/Security-Overview\n- https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#security-concerns",
              "severity": [
                {
                  "type": "CVSS_V3",
                  "score": ""
                }
              ],
              "affected": [
                {
                  "package": {
                    "ecosystem": "PyPI",
                    "name": "archivebox"
                  },
                  "ranges": [
                    {
                      "type": "ECOSYSTEM",
                      "events": [
                        {
                          "introduced": "0"
                        },
                        {
                          "last_affected": "0.6.2"
                        }
                      ]
                    }
                  ],
                  "ecosystem_specific": {
                    "affected_functions": [
                      ""
                    ]
                  }
                }
              ],
              "references": [
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/security/advisories/GHSA-cr45-98w9-gwqx"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/issues/239"
                },
                {
                  "type": "WEB",
                  "url": "https://en.wikipedia.org/wiki/Cross-site_request_forgery"
                },
                {
                  "type": "PACKAGE",
                  "url": "https://github.com/ArchiveBox/ArchiveBox"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox#caveats"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Configuration#save_wget"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#2-export-and-host-it-as-static-html"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#security-concerns"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Security-Overview"
                }
              ],
              "database_specific": {
                "cwe_ids": [
                ],
                "severity": "MODERATE",
                "github_reviewed": true,
                "github_reviewed_at": "2023-10-19T16:13:50Z",
                "nvd_published_at": null
              }
            }
            """.trimIndent()

        // language=JSON
        private val cosvWithMissingSeverityScoreJson = """
            {
              "id": "GHSA-cr45-98w9-gwqx",
              "summary": "Viewing wget extractor output while logged in as an admin allows archived JS to execute in the admins context",
              "details": "### Impact\n\nAny users who are using the `wget` extractor and view the content it outputs. \n\nThe impact is potentially severe if you are logged in to the ArchiveBox admin site in the same browser session and view an archived malicious page designed to target your ArchiveBox instance. Malicious JS could potentially act using your logged-in admin credentials and add/remove/modify snapshots, add/remove/modify ArchiveBox users, and generally do anything an admin user could do. \n\nThe impact is less severe for non-logged-in users, as malicious JS cannot *modify* any archives, but it can still *read* all the other archived content by fetching the snapshot index and iterating through it.\n\nBecause all of ArchiveBox's archived content is served from the same host and port as the admin panel, when archived pages are viewed the JS executes in the same context as all the other archived pages (and the admin panel), defeating most of the browser's usual CORS/CSRF security protections and leading to this issue.\n\n### Patches\n\nFollow here for progress on mitigating this issue: https://github.com/ArchiveBox/ArchiveBox/issues/239\n\n### Workarounds\n\nDisable the wget extractor by setting [`archivebox config --set SAVE_WGET=False`](https://github.com/ArchiveBox/ArchiveBox/wiki/Configuration#save_wget), ensure you are always logged out, or serve only a [static HTML version](https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#2-export-and-host-it-as-static-html) of your archive.\n\n### References\n\n- https://en.wikipedia.org/wiki/Cross-site_request_forgery\n- https://github.com/ArchiveBox/ArchiveBox#caveats\n- https://github.com/ArchiveBox/ArchiveBox/wiki/Security-Overview\n- https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#security-concerns",
              "aliases": [
                "CVE-2023-45815"
              ],
              "modified": "2023-10-19T19:47:39.037119Z",
              "published": "2023-10-19T16:13:50Z",
              "database_specific": {
                "nvd_published_at": null,
                "cwe_ids": [],
                "severity": "MODERATE",
                "github_reviewed": true,
                "github_reviewed_at": "2023-10-19T16:13:50Z"
              },
              "references": [
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/security/advisories/GHSA-cr45-98w9-gwqx"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/issues/239"
                },
                {
                  "type": "WEB",
                  "url": "https://en.wikipedia.org/wiki/Cross-site_request_forgery"
                },
                {
                  "type": "PACKAGE",
                  "url": "https://github.com/ArchiveBox/ArchiveBox"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox#caveats"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Configuration#save_wget"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#2-export-and-host-it-as-static-html"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Publishing-Your-Archive#security-concerns"
                },
                {
                  "type": "WEB",
                  "url": "https://github.com/ArchiveBox/ArchiveBox/wiki/Security-Overview"
                }
              ],
              "affected": [
                {
                  "package": {
                    "name": "archivebox",
                    "ecosystem": "PyPI",
                    "purl": "pkg:pypi/archivebox"
                  },
                  "ranges": [
                    {
                      "type": "ECOSYSTEM",
                      "events": [
                        {
                          "introduced": "0"
                        },
                        {
                          "last_affected": "0.6.2"
                        }
                      ]
                    }
                  ],
                  "versions": [
                    "0.4.11",
                    "0.4.12",
                    "0.4.13",
                    "0.4.14",
                    "0.4.15",
                    "0.4.16",
                    "0.4.17",
                    "0.4.18",
                    "0.4.19",
                    "0.4.20",
                    "0.4.21",
                    "0.4.6",
                    "0.4.8",
                    "0.4.9",
                    "0.5.0",
                    "0.5.1",
                    "0.5.2",
                    "0.5.3",
                    "0.5.4",
                    "0.6.0",
                    "0.6.1",
                    "0.6.2"
                  ],
                  "ecosystem_specific": {
                    "affected_functions": [
                      ""
                    ]
                  },
                  "database_specific": {
                    "source": "https://github.com/github/advisory-database/blob/main/advisories/github-reviewed/2023/10/GHSA-cr45-98w9-gwqx/GHSA-cr45-98w9-gwqx.json"
                  }
                }
              ],
              "schema_version": "1.6.0",
              "severity": [
                {
                  "type": "CVSS_V3"
                }
              ]
            }
            """.trimIndent()
    }
}
