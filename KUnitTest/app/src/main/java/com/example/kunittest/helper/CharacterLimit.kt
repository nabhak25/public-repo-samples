package com.example.kunittest.helper

class CharacterLimit {

    companion object {

        fun isLessThan10(string: String) = string.length < 10

        fun isEqualTo10(string: String) = string.length == 10

        fun isGreaterThan10(string: String) = string.length > 10
    }
}