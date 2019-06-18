package com.example.kstatepattern.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kstatepattern.R
import com.example.kstatepattern.interfaces.VendingMachine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vendingMachine = VendingMachine()
        vendingMachine.dispenseProduct()

        vendingMachine.selectProduct("Pepsi", 30)
        vendingMachine.dispenseProduct()
    }
}
