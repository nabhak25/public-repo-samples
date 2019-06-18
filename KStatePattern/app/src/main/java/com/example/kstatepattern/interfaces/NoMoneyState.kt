package com.example.kstatepattern.interfaces

import android.util.Log
import com.example.kstatepattern.model.VendingMachineState

class NoMoneyState: VendingMachineState {

    override fun selectProduct(product: String, amount: Int) {
        Log.i("VMS:", "$amount has been inserted and $product is selected")
    }

    override fun dispenseProduct() {
        Log.i("VMS:", "Vending machine cannot dispense product because no product is selected and no money is inserted")
    }
}