package com.example.kdecoratorpattern.decorator

import com.example.kdecoratorpattern.interfaces.Pizza

open class PizzaDecorator(open var pizza: Pizza): Pizza {

    override fun makePizza(): String = pizza.makePizza()

}