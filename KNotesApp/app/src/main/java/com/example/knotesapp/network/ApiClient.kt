package com.example.knotesapp.network

import android.content.Context
import com.example.knotesapp.app.Constants
import com.example.knotesapp.utils.PrefUtils
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val REQUEST_TIMEOUT = 60L
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null


    fun getClient(context: Context): Retrofit? {
        if (okHttpClient == null) initOkHttp(context)

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun initOkHttp(context: Context) {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
            // Adding Authorization token (API Key)
            // Requests will be denied without API key
            val apiKey = PrefUtils.getApiKey(context)
            apiKey?.let { str ->
                if (str.isNotEmpty() || str.isNotBlank()) {
                    requestBuilder.addHeader("Authorization", apiKey)
                }
            }

            val request = requestBuilder.build()
            return@addInterceptor it.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}