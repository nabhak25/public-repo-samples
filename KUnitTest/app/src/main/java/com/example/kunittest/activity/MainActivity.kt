package com.example.kunittest.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kunittest.R
import com.example.kunittest.helper.CharacterLimit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

     fun printCharacterLimit(view: View) {
        val string = edittext.text.toString()

        when {
            CharacterLimit.isLessThan10(string) -> {
                Toast.makeText(applicationContext, "Less than 10", Toast.LENGTH_LONG).show()
            }
            CharacterLimit.isEqualTo10(string) -> {
                Toast.makeText(applicationContext, "Equal to 10", Toast.LENGTH_LONG).show()
            }
            CharacterLimit.isGreaterThan10(string) -> {
                Toast.makeText(applicationContext, "Greater than 10", Toast.LENGTH_LONG).show()
            }
        }
    }
}
