package com.example.kbuilder2.model

import com.example.kbuilder2.interfaces.MealBoxBuilder

data class Attendant(var builder: MealBoxBuilder) {

     fun getMeal() = builder.buildMealBox()

     fun constructMeal() {
        builder.addMainItem()
        builder.addSideItem()
        builder.addDrink()
        builder.addSweet()
    }

}