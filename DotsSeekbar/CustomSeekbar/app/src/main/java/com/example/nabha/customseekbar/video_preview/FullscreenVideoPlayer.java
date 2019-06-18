package com.example.nabha.customseekbar.video_preview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.nabha.customseekbar.R;

public class FullscreenVideoPlayer extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageView mThumbnailImage;
    private ImageButton mPlayback;
    private FrameLayout mTouchOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video_player);

        setupViews();
        startPlayer();
    }

    private void setupViews() {
        mVideoView = (VideoView) findViewById(R.id.player_view);
        mThumbnailImage = (ImageView) findViewById(R.id.dummyImage);
        mPlayback = (ImageButton) findViewById(R.id.movie_play_pause);
        mTouchOverlay = (FrameLayout) findViewById(R.id.touchoverlay_preview);
    }

    private void startPlayer() {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.batman));
    }


}


