package com.example.kstatepattern.interfaces

import android.util.Log
import com.example.kstatepattern.model.VendingMachineState

data class VendingMachine(var vendingMachineState: VendingMachineState = NoMoneyState()):
    VendingMachineState {
    override fun selectProduct(product: String, amount: Int) {
        vendingMachineState.selectProduct(product, amount)
        val hasMoneyState = HasMoneyState()

        if (vendingMachineState is NoMoneyState) {
            vendingMachineState = hasMoneyState
            Log.i("VMS: ", "Vending machine's state is now ${vendingMachineState.javaClass.simpleName}")
        }

    }

    override fun dispenseProduct() {
        vendingMachineState.dispenseProduct()
        val noMoneyState = NoMoneyState()

        if (vendingMachineState is HasMoneyState) {
            vendingMachineState = noMoneyState
            Log.i("VMS: ", "Vending machine's state is now ${vendingMachineState.javaClass.simpleName}")
        }
    }


}