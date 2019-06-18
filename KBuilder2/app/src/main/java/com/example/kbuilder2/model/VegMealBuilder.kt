package com.example.kbuilder2.model

import com.example.kbuilder2.interfaces.MealBoxBuilder

class VegMealBuilder: MealBoxBuilder {

    private var mealBox = MealBox()

    override fun addMainItem() {
        mealBox.mainItem = "Veg Burger"
    }

    override fun addSideItem() {
        mealBox.sideItem = "Potato Wedges"
    }

    override fun addDrink() {
        mealBox.drink = "Fanta"
    }

    override fun addSweet() {
        mealBox.sweet = "Gulab Jamun"
    }

    override fun buildMealBox(): MealBox = mealBox

}