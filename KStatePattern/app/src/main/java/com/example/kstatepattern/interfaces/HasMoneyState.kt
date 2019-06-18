package com.example.kstatepattern.interfaces

import android.util.Log
import com.example.kstatepattern.model.VendingMachineState

class HasMoneyState: VendingMachineState {

    override fun selectProduct(product: String, amount: Int) {
        Log.i("VMS:", "Already product is selected and money is inserted. Wait for dispensing process to finish")
    }

    override fun dispenseProduct() {
        Log.i("VMS:", "Vending machine dispensed the product")
    }
}