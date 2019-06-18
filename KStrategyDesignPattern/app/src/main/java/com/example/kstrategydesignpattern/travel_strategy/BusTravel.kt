package com.example.kstrategydesignpattern.travel_strategy

import android.content.Context
import com.example.kstrategydesignpattern.R
import com.example.kstrategydesignpattern.interfaces.TravelStrategy

class BusTravel: TravelStrategy {
    override fun goToAirport(context: Context): String {
        return context.getString(R.string.you_want_to_take_bus)
    }
}