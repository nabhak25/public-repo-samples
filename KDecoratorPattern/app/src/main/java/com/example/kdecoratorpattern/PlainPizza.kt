package com.example.kdecoratorpattern

import com.example.kdecoratorpattern.interfaces.Pizza

class PlainPizza: Pizza {

    override fun makePizza(): String {
        return "Plain Pizza"
    }

}