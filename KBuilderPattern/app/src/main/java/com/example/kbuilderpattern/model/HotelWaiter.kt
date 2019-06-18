package com.example.kbuilderpattern.model

import android.util.Log
import com.example.kbuilderpattern.builder.CoffeeBuilder
import com.example.kbuilderpattern.builder.TeaBuilder
import com.example.kbuilderpattern.interfaces.BeverageBuilder

object HotelWaiter {

    lateinit var beverageBuilder: BeverageBuilder

    fun takeOrder(beverageType: String): Beverage {

       when (beverageType) {
           "Coffee" -> {
            beverageBuilder = CoffeeBuilder()
           }

           "Tea" -> {
                beverageBuilder = TeaBuilder()
           }

           else -> Log.i("Error", "Sorry we dont take order for $beverageType")
       }

        return beverageBuilder.buildBeverage()
    }
}