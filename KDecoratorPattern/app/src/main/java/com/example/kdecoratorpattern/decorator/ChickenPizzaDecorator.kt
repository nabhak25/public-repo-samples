package com.example.kdecoratorpattern.decorator

import com.example.kdecoratorpattern.interfaces.Pizza

class ChickenPizzaDecorator(override var pizza: Pizza): PizzaDecorator(pizza) {

    override fun makePizza(): String {
        return pizza.makePizza() + addChickenAndCheese()
    }

    private fun addChickenAndCheese(): String {
        return "    Added Chicken and Cheese"
    }

}