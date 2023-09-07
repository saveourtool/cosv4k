@file:Suppress("LONG_LINE", "TOO_LONG_FUNCTION")

package com.saveourtool.osv4k

import kotlin.test.Test

class CosvTestInJava {
    @Test
    fun cosvWithSeverity() {
        OsvSchemaJacksonJavaTestUtil.doEncodeDecodeAndCompare(
            CosvTest.COSV_EXAMPLE
        )
    }
}
