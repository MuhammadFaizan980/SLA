package com.squadtechs.faizan.sla.activity_content_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.FirebaseDatabase;
import com.squadtechs.faizan.sla.R;
import com.squadtechs.faizan.sla.activity_play_video.ActivityPlayVideo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ActivityContentPage extends AppCompatActivity {

    private PDFView pdfView;
    private Toolbar toolbar;
    private ImageView imgPlay;
    private Intent intent;
    private long oldTime = 0;
    private long newTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_page);
        initViews();
        populateToolbar();
        loadData();
        setListener();
    }

    private void initViews() {
        pdfView = findViewById(R.id.pdfView);
        toolbar = findViewById(R.id.main_toolbar);
        imgPlay = findViewById(R.id.img_play_video);
        intent = getIntent();
    }

    private void populateToolbar() {
        toolbar.setTitle("Study");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        String mURL = (String) intent.getExtras().get("pdf_url");
        Log.i("dxdiag", "url: " + mURL);
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityContentPage.this);
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mURL, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                InputStream stream = new ByteArrayInputStream(response);
                pdfView.fromStream(stream)
                        .enableSwipe(true)
                        .defaultPage(0)
                        .spacing(3)
                        .enableAntialiasing(true)
                        .load();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("dxdiag", error.toString());
            }
        }, null);
        requestQueue.add(request);
    }

    private void setListener() {
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ActivityContentPage.this, ActivityPlayVideo.class);
                mIntent.putExtra("video_url", (String) intent.getExtras().get("video_url"));
                startActivity(mIntent);
            }
        });
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
        map.put("email", preferences.getString("email", "N/A"));
        map.put("content", "PDF");
        map.put("time", String.valueOf(totalMinutes / 60) + " minutes");
        FirebaseDatabase.getInstance().getReference("user_data").push().setValue(map);
    }
}
