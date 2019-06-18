package com.example.kbuilderpattern.interfaces

import com.example.kbuilderpattern.model.Beverage

abstract class BeverageBuilder {

    lateinit var beverage: Beverage

    abstract fun setBeverageType()
    abstract fun setWater()
    abstract fun setMilk()
    abstract fun setSugar()
    abstract fun setPowderQuantity()
    abstract fun createBeverage(): Beverage

    fun buildBeverage(): Beverage {
        val beverage = createBeverage()
        this.beverage = beverage
        setBeverageType()
        setWater()
        setMilk()
        setSugar()
        setPowderQuantity()
        return beverage
    }
}