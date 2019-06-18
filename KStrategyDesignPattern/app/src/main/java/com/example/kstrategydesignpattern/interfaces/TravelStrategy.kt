package com.example.kstrategydesignpattern.interfaces

import android.content.Context

interface TravelStrategy {

    fun goToAirport(context: Context): String
}