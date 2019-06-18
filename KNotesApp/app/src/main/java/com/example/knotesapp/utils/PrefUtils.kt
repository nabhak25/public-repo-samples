package com.example.knotesapp.utils

import android.content.Context
import android.content.SharedPreferences

object PrefUtils {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
    }

    fun storeApiKey(context: Context, apiKey: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("API_KEY", apiKey)
        editor.apply()
    }

    fun getApiKey(context: Context): String? {
        return getSharedPreferences(context).getString("API_KEY", null)
    }

}