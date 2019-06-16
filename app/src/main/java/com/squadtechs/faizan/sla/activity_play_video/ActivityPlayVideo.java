package com.squadtechs.faizan.sla.activity_play_video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.squadtechs.faizan.sla.R;

public class ActivityPlayVideo extends AppCompatActivity {

    private VideoView videoView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        initViews();
        playVideo();
    }

    private void playVideo() {
        String url = (String) intent.getExtras().get("video_url");
        MediaController mediaController = new MediaController(ActivityPlayVideo.this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(url);
        videoView.start();
    }

    private void initViews() {
        videoView = findViewById(R.id.video_play);
        intent = getIntent();
    }

}
