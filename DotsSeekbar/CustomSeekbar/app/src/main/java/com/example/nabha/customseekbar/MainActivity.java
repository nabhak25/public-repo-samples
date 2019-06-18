package com.example.nabha.customseekbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nabha.customseekbar.seekbar.CustomSeekbar;
import com.example.nabha.customseekbar.video_preview.FullscreenVideoPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomSeekbar seekbar = (CustomSeekbar) findViewById(R.id.seekbar);
//        final TextView tv = (TextView) findViewById(R.id.textView);
    }

    public void presentNextScreen(View view) {
        Intent next = new Intent(this, FullscreenVideoPlayer.class);
        startActivity(next);
    }
}
