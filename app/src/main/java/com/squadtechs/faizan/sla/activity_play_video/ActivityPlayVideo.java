package com.squadtechs.faizan.sla.activity_play_video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.database.FirebaseDatabase;
import com.squadtechs.faizan.sla.R;

import java.util.HashMap;

public class ActivityPlayVideo extends AppCompatActivity {

    private VideoView videoView;
    private Intent intent;
    private long oldTime, newTime;

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

    @Override
    protected void onStart() {
        super.onStart();
        oldTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        newTime = System.currentTimeMillis();
        double totalMinutes = ((newTime - oldTime) / 1000);
        HashMap<String, String> map = new HashMap<>();
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        map.put("reg_number", preferences.getString("reg_number", "N/A"));
        map.put("email", preferences.getString("user_email", "N/A"));
        map.put("content", "Video");
        map.put("time", String.valueOf(totalMinutes / 60) + " minutes");
        FirebaseDatabase.getInstance().getReference("user_data").push().setValue(map);
    }

}
