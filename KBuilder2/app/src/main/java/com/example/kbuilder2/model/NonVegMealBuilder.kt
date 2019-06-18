package com.example.kbuilder2.model

import com.example.kbuilder2.interfaces.MealBoxBuilder

class NonVegMealBuilder: MealBoxBuilder {

     private var mealBox: MealBox = MealBox()

    override fun addMainItem() {
        mealBox.mainItem = "Chicken Burger"
    }

    override fun addSideItem() {
        mealBox.sideItem = "Chicken Wings"
    }

    override fun addDrink() {
        mealBox.drink = "Pepsi"
    }

    override fun addSweet() {
        mealBox.sweet = "Cake Slice"
    }

    override fun buildMealBox(): MealBox = mealBox
}