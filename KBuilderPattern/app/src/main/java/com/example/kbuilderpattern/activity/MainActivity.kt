package com.example.kbuilderpattern.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kbuilderpattern.R
import com.example.kbuilderpattern.model.HotelWaiter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var beverage = HotelWaiter.takeOrder("Tea")
        Toast.makeText(applicationContext, "Your ${beverage.beverageName} is ready!", Toast.LENGTH_LONG).show()

        beverage = HotelWaiter.takeOrder("Coffee")
        Toast.makeText(applicationContext, "Your ${beverage.beverageName} is ready!", Toast.LENGTH_LONG).show()

    }
}
