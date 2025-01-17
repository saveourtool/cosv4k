@file:Suppress(
    "LONG_LINE",
    "TOO_LONG_FUNCTION",
    "FUNCTION_NAME_INCORRECT_CASE",
)

package com.saveourtool.osv4k

import com.saveourtool.osv4k.OsvSchemaTestUtil.doEncodeDecodeAndCompare
import kotlin.test.Test

class DebianTest {
    @Test
    fun dsa_3029_1() {
        // language=JSON
        doEncodeDecodeAndCompare(
            """
                        {
                          "schema_version": "1.0.0",
                          "id": "DSA-3029-1",
                          "modified": "2014-09-20T08:18:07Z",
                          "published": "2014-09-20T00:00:01Z",
                          "aliases": [
                            "CVE-2014-3616"
                          ],
                          "summary": "nginx - security update",
                          "details": "\nAntoine Delignat-Lavaud and Karthikeyan Bhargavan discovered that it was\npossible to reuse cached SSL sessions in unrelated contexts, allowing\nvirtual host confusion attacks in some configurations by an attacker in\na privileged network position.\n\n\nFor the stable distribution (wheezy), this problem has been fixed in\nversion 1.2.1-2.2+wheezy3.\n\n\nFor the testing distribution (jessie), this problem has been fixed in\nversion 1.6.2-1.\n\n\nFor the unstable distribution (sid), this problem has been fixed in\nversion 1.6.2-1.\n\n\nWe recommend that you upgrade your nginx packages.\n\n\n",
                          "affected": [
                            {
                              "package": {
                                "ecosystem": "Debian:7",
                                "name": "nginx"
                              },
                              "ranges": [
                                {
                                  "type": "ECOSYSTEM",
                                  "events": [
                                    {
                                      "introduced": "0"
                                    },
                                    {
                                      "fixed": "1.2.1-2.2+wheezy3"
                                    }
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
                          ]
                        }
            """.trimIndent()
        )
    }
}
