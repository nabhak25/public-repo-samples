package com.example.nabha.ui_elements_sample.Fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nabha.ui_elements_sample.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }

    public void simpleFragment(View view) {
        Intent simpleFragmentIntent = new Intent(getApplicationContext(), SimpleFragmentActivity.class);
        startActivity(simpleFragmentIntent);
    }

    public void simpleFragmentTwo(View view) {
        Intent simpleFragmentIntent = new Intent(getApplicationContext(), SimpleFragmentTwoActivity.class);
        startActivity(simpleFragmentIntent);
    }
}
