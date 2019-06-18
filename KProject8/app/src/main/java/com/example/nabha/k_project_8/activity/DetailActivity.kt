package com.example.nabha.k_project_8.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.ImageView
import com.example.nabha.k_project_8.R
import com.example.nabha.k_project_8.utils.AppUtils

class DetailActivity : AppCompatActivity() {

    private lateinit var buttonHolder : ConstraintLayout
    private lateinit var fullImage : ImageView
    private lateinit var shareButton : Button
    private lateinit var infoButton: Button

    private var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        validateIntent()
        setupViews()
    }

    private fun setupViews() {
        buttonHolder = findViewById(R.id.buttonHolder)
        fullImage = findViewById(R.id.imageView)
        shareButton = findViewById(R.id.btnShare)
        infoButton = findViewById(R.id.btnInfo)
    }

    private fun validateIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            path = bundle.getString(AppUtils.PHOTO_PATH)
        }
    }
}
