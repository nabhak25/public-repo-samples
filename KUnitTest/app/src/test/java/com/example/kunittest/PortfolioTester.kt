package com.example.kunittest

import com.example.kunittest.helper.StockService
import com.example.kunittest.model.Portfolio
import com.example.kunittest.model.Stock
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PortfolioTester {

    private lateinit var stockService: StockService
    private lateinit var portfolio: Portfolio

    @Before
    fun initMocks() {
        //Create a portfolio object which is to be tested
        portfolio = Portfolio()

        //Create the mock object of stock service
        stockService = mock(StockService::class.java)

        //set the stockService to the portfolio
        portfolio.stockService = stockService
    }

    @Test
    fun portfolioTester_getPriceOfStocks() {

        //Creates a list of stocks to be added to the portfolio
        val stocks = ArrayList<Stock>()
        val googleStock = Stock("1", "Google", 10)
        val microsoftStock = Stock("2", "Microsoft", 100)


        stocks.add(googleStock)
        stocks.add(microsoftStock)

        //add stocks to the portfolio
        portfolio.stocks = stocks

        //mock the behavior of stock service to return the value of various stocks
        given(stockService.getPrice(googleStock)).willReturn(50.00)
        given(stockService.getPrice(microsoftStock)).willReturn(1000.00)

        val marketValue = portfolio.getMarketValue()
        assertTrue(marketValue == 100500.00)
    }
}

