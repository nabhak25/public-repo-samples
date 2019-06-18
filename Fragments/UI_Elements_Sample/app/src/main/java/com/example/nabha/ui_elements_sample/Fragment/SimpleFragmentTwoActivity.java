package com.example.nabha.ui_elements_sample.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nabha.ui_elements_sample.R;

public class SimpleFragmentTwoActivity extends AppCompatActivity {

    Button activityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_fragment_two);
// get the reference of Button
        activityButton = (Button) findViewById(R.id.activity_button);

// perform setOnClickListener event on Button
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// display a message by using a Toast
                Toast.makeText(getApplicationContext(), "Activity's Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
