package com.example.kstatepattern.model

interface VendingMachineState {

    fun selectProduct(product: String, amount: Int)

    fun dispenseProduct()

}