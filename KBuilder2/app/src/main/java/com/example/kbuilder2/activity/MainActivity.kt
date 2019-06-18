package com.example.kbuilder2.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kbuilder2.R
import com.example.kbuilder2.model.Attendant
import com.example.kbuilder2.model.NonVegMealBuilder
import com.example.kbuilder2.model.VegMealBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val vegMealBuilder = VegMealBuilder()
        var attendant = Attendant(vegMealBuilder)
        attendant.constructMeal()
        Log.i("meal", attendant.getMeal().toString())

        val nonVegMealBuilder = NonVegMealBuilder()
        attendant = Attendant(nonVegMealBuilder)
        attendant.constructMeal()
        Log.i("meal", attendant.getMeal().toString())
    }
}
