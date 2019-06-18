package com.example.kproject9

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var timeText : TextView
    lateinit var startTimer : Button
    lateinit var addTime : Button
    lateinit var removeTime : Button

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        timeText = findViewById(R.id.timerText)
        startTimer = findViewById(R.id.start)
        addTime = findViewById(R.id.add)
        removeTime = findViewById(R.id.subtract)

        startTimer.setOnClickListener(this)
        addTime.setOnClickListener(this)
        removeTime.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.start -> Log.i(TAG, "Start")
            R.id.add -> Log.i(TAG, "Add")
            R.id.subtract -> Log.i(TAG, "Subtract")
        }
    }
}
