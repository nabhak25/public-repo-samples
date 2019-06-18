package com.example.kprojectbitcoinlive.helpers

import android.util.Log
import com.example.kprojectbitcoinlive.model.BitCoin
import com.example.kprojectbitcoinlive.rest.ApiClient
import com.example.kprojectbitcoinlive.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetBitcoinPrice(
    private val url: String,
    private val priceReceiverCallback: PriceReceiverCallback?,
    private val currencySign: String
) {

    fun getBitcoinPrice() {
        var askingPrice: String

        val apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        val call = apiInterface?.getAskingPrice(url)
        call?.enqueue(object : Callback<BitCoin> {
            override fun onFailure(call: Call<BitCoin>, t: Throwable) {
                Log.e("BitCoin", t.toString())
            }

            override fun onResponse(call: Call<BitCoin>, response: Response<BitCoin>) {
                askingPrice = response.body()?.price.toString()
                Log.i("BitCoin", askingPrice)
                priceReceiverCallback?.onPriceReceived(value = currencySign + askingPrice)
            }
        })
    }

    interface PriceReceiverCallback {
        fun onPriceReceived(value: String)
    }

}