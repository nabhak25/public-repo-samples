package com.example.kunittest.helper

import com.example.kunittest.model.Stock

interface StockService {

    fun getPrice(stock: Stock): Double
}