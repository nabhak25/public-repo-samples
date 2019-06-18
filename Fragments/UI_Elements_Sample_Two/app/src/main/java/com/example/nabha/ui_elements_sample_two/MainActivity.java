package com.example.nabha.ui_elements_sample_two;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nabha.ui_elements_sample_two.MultipleFragment.MultipleFragmentActivity;
import com.example.nabha.ui_elements_sample_two.SimpleFragment.SimpleFragmentActivity;
import com.example.nabha.ui_elements_sample_two.ViewPagerFragment.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleFragmentActivity(View view) {
        Intent simpleIntent = new Intent(this, SimpleFragmentActivity.class);
        startActivity(simpleIntent);
    }

    public void multipleFragmentActivity(View view) {
        Intent multiIntent = new Intent(this, MultipleFragmentActivity.class);
        startActivity(multiIntent);
    }

    public void viewPagerActivity(View view) {
        Intent viewPagerIntent = new Intent(this, ViewPagerActivity.class);
        startActivity(viewPagerIntent);
    }
}
