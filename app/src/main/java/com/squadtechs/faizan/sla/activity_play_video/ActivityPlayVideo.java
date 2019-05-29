package com.squadtechs.faizan.sla.activity_play_video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;
import com.squadtechs.faizan.sla.R;

public class ActivityPlayVideo extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        initViews();
    }

    private void initViews() {
        videoView = findViewById(R.id.video_play);
    }

}
