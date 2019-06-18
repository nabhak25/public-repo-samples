package com.example.nabha.ui_elements_sample.Gridview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nabha.ui_elements_sample.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Intent dialogIntent = getIntent();
        if (dialogIntent != null) {
            int foodItemImageId = dialogIntent.getIntExtra("foodItemImage",R.drawable.panipuri);
            String dishName = dialogIntent.getStringExtra("dishName");

            ImageView foodImageView = (ImageView) findViewById(R.id.dialogImageView);
            foodImageView.setImageResource(foodItemImageId);

            TextView dishNameTextView = (TextView) findViewById(R.id.dialogTextView);
            dishNameTextView.setText(dishName);
        }
    }

    public void dismissDialog(View view) {
        finish();
    }
}
