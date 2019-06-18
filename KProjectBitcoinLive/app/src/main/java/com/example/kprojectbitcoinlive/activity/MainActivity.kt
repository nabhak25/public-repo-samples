package com.example.kprojectbitcoinlive.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.kprojectbitcoinlive.R
import com.example.kprojectbitcoinlive.adapter.CountryAdapter
import com.example.kprojectbitcoinlive.helpers.CountryListener
import com.example.kprojectbitcoinlive.helpers.GetBitcoinPrice
import com.example.kprojectbitcoinlive.model.CountryModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GetBitcoinPrice.PriceReceiverCallback, CountryListener {

    override fun onCountrySelected(country: CountryModel) {
        GetBitcoinPrice(CountryAdapter.URL +"BTC${country.code}", this, country.symbol).getBitcoinPrice()
    }

    override fun onPriceReceived(value: String) {
        price.text = value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CountryAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}
