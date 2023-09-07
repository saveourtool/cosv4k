@file:Suppress("LONG_LINE", "TOO_LONG_FUNCTION")

package com.saveourtool.osv4k

import kotlin.test.Test

/**
 * Test for COSV using Jackson
 */
class CosvTestUsingJackson {
    /**
     * A simple test
     */
    @Test
    fun simpleTest() {
        OsvSchemaJacksonJavaTestUtil.doEncodeDecodeAndCompare(
            CosvTest.cosvExample
        )
    }
}
