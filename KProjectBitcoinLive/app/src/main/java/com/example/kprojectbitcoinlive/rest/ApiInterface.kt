package com.example.kprojectbitcoinlive.rest

import com.example.kprojectbitcoinlive.model.BitCoin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

  @GET
  fun getAskingPrice(@Url url: String): Call<BitCoin>

}