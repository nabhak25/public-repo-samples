package com.example.kunittest.model

import com.example.kunittest.helper.StockService

data class Portfolio(var stockService: StockService? = null, var stocks: List<Stock> = emptyList()) {

    constructor() : this(null, emptyList())

    fun getMarketValue(): Double {
        var marketValue = 0.0

        for (stock in stocks) {
            stockService?.let { marketValue += it.getPrice(stock) * stock.quantity }
        }

        return marketValue
    }


}