package com.example.kdecoratorpattern.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import com.example.kdecoratorpattern.PlainPizza
import com.example.kdecoratorpattern.R
import com.example.kdecoratorpattern.decorator.ChickenPizzaDecorator
import com.example.kdecoratorpattern.decorator.VegPizzaDecorator
import com.example.kdecoratorpattern.interfaces.Pizza
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var plainPizza: Pizza

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plainCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chickenCheck.isChecked = false
                vegCheck.isChecked = false
                plainPizza = PlainPizza()
                Toast.makeText(applicationContext, plainPizza.makePizza(), Toast.LENGTH_LONG).show()
            }

        }

        chickenCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                plainCheck.isChecked = false
                vegCheck.isChecked = false
                plainPizza = PlainPizza()
                val chickenPizzaDecorator = ChickenPizzaDecorator(plainPizza)
                Toast.makeText(applicationContext, chickenPizzaDecorator.makePizza(), Toast.LENGTH_LONG).show()
            }

        }

        vegCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                plainCheck.isChecked = false
                chickenCheck.isChecked = false
                plainPizza = PlainPizza()
                val vegPizzaDecorator = VegPizzaDecorator(plainPizza)
                Toast.makeText(applicationContext, vegPizzaDecorator.makePizza(), Toast.LENGTH_LONG).show()
            }

        }

    }
}
