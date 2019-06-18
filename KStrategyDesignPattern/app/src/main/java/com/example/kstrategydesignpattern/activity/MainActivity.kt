package com.example.kstrategydesignpattern.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kstrategydesignpattern.R
import com.example.kstrategydesignpattern.context.TravelContext
import com.example.kstrategydesignpattern.travel_strategy.AutoTravel
import com.example.kstrategydesignpattern.travel_strategy.BusTravel
import com.example.kstrategydesignpattern.travel_strategy.TaxiTravel
import com.example.kstrategydesignpattern.travel_strategy.TrainTravel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var travelContext: TravelContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun gotoAirport(view: View) {
        val enteredString = editText.text.toString().toLowerCase()
        if (enteredString.isNotEmpty() || enteredString.isNotBlank()) {
            when (enteredString) {
                "bus" -> {
                    travelContext = TravelContext(BusTravel())
                    textView.text = travelContext.gotoAirport(applicationContext)
                }

                "taxi" -> {
                    travelContext = TravelContext(TaxiTravel())
                    textView.text = travelContext.gotoAirport(applicationContext)
                }

                "train" -> {
                    travelContext = TravelContext(TrainTravel())
                    textView.text = travelContext.gotoAirport(applicationContext)
                }

                "auto" -> {
                    travelContext = TravelContext(AutoTravel())
                    textView.text = travelContext.gotoAirport(applicationContext)
                }

                else -> Toast.makeText(applicationContext, "Enter valid Transport", Toast.LENGTH_SHORT).show()

            }
        } else {
            editText.error = "Cannot be empty!"
            editText.requestFocus()
        }

    }
}