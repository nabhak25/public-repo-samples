package com.example.kbuilderpattern.builder

import com.example.kbuilderpattern.interfaces.BeverageBuilder
import com.example.kbuilderpattern.model.Beverage

class TeaBuilder : BeverageBuilder() {

    override fun setBeverageType() {
        beverage.beverageName = "Tea"
    }

    override fun setWater() {
        beverage.water = 40
    }

    override fun setMilk() {
        beverage.milk = 50
    }

    override fun setSugar() {
        beverage.sugar = 10
    }

    override fun setPowderQuantity() {
        beverage.powderQuantity = 10
    }

    override fun createBeverage(): Beverage {
        return Beverage()
    }

}