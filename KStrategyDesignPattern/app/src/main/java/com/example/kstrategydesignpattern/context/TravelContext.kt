package com.example.kstrategydesignpattern.context

import android.content.Context
import com.example.kstrategydesignpattern.interfaces.TravelStrategy

class TravelContext(var travelStrategy: TravelStrategy) {

    fun gotoAirport(context: Context): String {
        return travelStrategy.goToAirport(context)
    }
}