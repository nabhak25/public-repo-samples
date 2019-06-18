package com.example.kunittest

import com.example.kunittest.helper.CharacterLimit
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CharacterLimitTest {


    @Test
    fun characterLimit_isLessThan10() {
        assertTrue(CharacterLimit.isLessThan10("Apple"))
    }

    @Test
    fun characterLimit_isEqualTo10() {
        assertTrue(CharacterLimit.isEqualTo10("Pineapples"))
    }

    @Test
    fun characterLimit_isGreaterThan10() {
        assertTrue(CharacterLimit.isGreaterThan10("The Mentalist"))
    }
}