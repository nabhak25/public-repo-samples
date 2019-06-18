package com.example.kdecoratorpattern.decorator

import com.example.kdecoratorpattern.interfaces.Pizza

class VegPizzaDecorator(override var pizza: Pizza): PizzaDecorator(pizza) {


    override fun makePizza(): String {
        return pizza.makePizza() + addVeggiesAndCheese()
    }

    private fun addVeggiesAndCheese(): String {
        return "    Added Vegetables and Cheese"
    }

}