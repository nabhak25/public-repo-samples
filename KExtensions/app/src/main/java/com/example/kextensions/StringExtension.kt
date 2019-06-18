package com.example.kextensions

class StringExtension {

    private var characterMap = HashMap<Char, Int>()


    fun String.occurence(): String {
        val str = this.toCharArray()
        var occurenceString = ""

        for (c in str) {
            if (characterMap.containsKey(c)) {
                characterMap[c] = (characterMap[c] ?: 1) + 1
            } else {
                characterMap[c] = 1
            }
            occurenceString = "$occurenceString$c" + characterMap[c]
        }

        return occurenceString
    }

}