package com.example.kbuilder2.interfaces

import com.example.kbuilder2.model.MealBox

interface MealBoxBuilder {

    fun addMainItem()

    fun addSideItem()

    fun addDrink()

    fun addSweet()

    fun buildMealBox(): MealBox
}