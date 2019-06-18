package com.example.nabha.slideshow.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nabha.slideshow.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView headerTextView = (TextView) findViewById(R.id.header);
        headerTextView.setSelected(true);
    }

    public void showGallery(View view) {
        Intent libraryIntent = new Intent(this, LibraryActivity.class);
        startActivity(libraryIntent);
    }
}
