package com.example.kextensions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val occurence = "Abba Dabba Jabba".occurrence()

    }

    private var characterMap = HashMap<Char, Int>()


    private fun String.occurrence(): String {
        val str = this.removeWhiteSpace().toCharArray()
        var occurrenceString = ""

        for (c in str) {
            if (characterMap.containsKey(c)) {
                characterMap[c] = (characterMap[c] ?: 1) + 1
            } else {
                characterMap[c] = 1
            }
        }

        characterMap.forEach {
            occurrenceString = "$occurrenceString\n${it.key}: ${it.value}"
        }

        return occurrenceString
    }

    private fun String.removeWhiteSpace(): String {
        return this.replace(" ", "")
    }
}
