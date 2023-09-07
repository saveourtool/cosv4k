@file:Suppress("LONG_LINE", "TOO_LONG_FUNCTION")

package com.saveourtool.osv4k

import com.saveourtool.osv4k.OsvSchemaTestUtil.doEncodeDecodeAndCompare
import kotlin.test.Test

class CosvTest {
    @Test
    fun cosvWithSeverity() {
        doEncodeDecodeAndCompare(COSV_EXAMPLE)
    }

    companion object {
        // language=JSON
        val COSV_EXAMPLE = """
                        {
                          "schema_version": "1.0.0",
                          "id": "DSA-3029-1",
                          "modified": "2014-09-20T08:18:07Z",
                          "published": "2014-09-20T00:00:01Z",
                          "withdrawn": "2015-09-20T00:00:02Z",
                          "aliases": [
                            "CVE-2014-3616"
                          ],
                          "related": [
                            "TEST-2023-1"
                          ],
                          "cwe_ids": [
                            "CWE-1",
                            "CWE-2"
                          ],
                          "cwe_names": [
                            "Stub 1",
                            "Stub 2"
                          ],
                          "timeline": [
                            {
                              "type": "introduced",
                              "value": "2014-09-20T11:11:11Z"
                            },
                            {
                              "type": "found",
                              "value": "2014-09-20T12:22:22Z"
                            },
                            {
                              "type": "fixed",
                              "value": "2014-09-20T13:33:33Z"
                            },
                            {
                              "type": "disclosed",
                              "value": "2014-09-20T14:44:44Z"
                            }
                          ],
                          "summary": "nginx - security update",
                          "details": "\nAntoine Delignat-Lavaud and Karthikeyan Bhargavan discovered that it was\npossible to reuse cached SSL sessions in unrelated contexts, allowing\nvirtual host confusion attacks in some configurations by an attacker in\na privileged network position.\n\n\nFor the stable distribution (wheezy), this problem has been fixed in\nversion 1.2.1-2.2+wheezy3.\n\n\nFor the testing distribution (jessie), this problem has been fixed in\nversion 1.6.2-1.\n\n\nFor the unstable distribution (sid), this problem has been fixed in\nversion 1.6.2-1.\n\n\nWe recommend that you upgrade your nginx packages.\n\n\n",
                          "severity": [
                            {
                              "type": "CVSS_V3",
                              "score": "some_value",
                              "level": "None"
                            }
                          ],
                          "affected": [
                            {
                              "package": {
                                "ecosystem": "Debian:7",
                                "name": "nginx"
                              },
                              "ranges": [
                                {
                                  "type": "ECOSYSTEM",
                                  "repo": "https://github.com/company/repo",
                                  "events": [
                                    {
                                      "introduced": "0"
                                    },
                                    {
                                      "fixed": "1.2.1-2.2+wheezy3"
                                    }
                                  ]
                                }
                              ],
                              "versions": [
                                "1.1",
                                "1.2"
                              ],
                              "patches_detail": [
                                {
                                  "patch_url": "https://github.com/company/repo/pull/1",
                                  "issue_url": "https://github.com/company/repo/issue/2",
                                  "main_language": "Java",
                                  "author": "user1",
                                  "committer": "user1",
                                  "branches": [
                                    "bugfix/fix-1#1"
                                  ],
                                  "tags": [
                                    "1.2.1"
                                  ]
                                }
                              ]
                            }
                          ],
                          "references": [
                            {
                              "type": "ADVISORY",
                              "url": "https://www.debian.org/security/2014/dsa-3029"
                            }
                          ],
                          "credits": [
                            {
                              "name": "user1",
                              "contact": [
                                "https://x.com/user1",
                                "https://saveourtool.com/profile/user1",
                                "https://github.com/user1"
                              ],
                              "type": "COORDINATOR"
                            },
                            {
                              "name": "user2",
                              "contact": [
                                "https://x.com/user2",
                                "https://saveourtool.com/profile/user2",
                                "https://github.com/user2"
                              ],
                              "type": "REPORTER"
                            }
                          ],
                          "contributors": [
                            {
                              "org": "H company",
                              "name": "user1",
                              "email": "user1@mail.com",
                              "contributions": "Just a good guy"
                            },
                            {
                              "org": "G company",
                              "name": "user2",
                              "email": "user2@mail.com",
                              "contributions": "A much better guy"
                            }
                          ],
                          "confirm_type": "manual_confirmed"
                        }
            """.trimIndent()
    }
}
