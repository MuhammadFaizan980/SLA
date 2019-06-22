package com.squadtechs.faizan.sla.activity_add_comment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squadtechs.faizan.sla.R;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class ActivityAddComment extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtComment;
    private Button btnAddComment;
    private ProgressBar progressBar;
    private DatabaseReference ref;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        initViews();
        populateToolbar();
        setListener();
    }

    private void setListener() {
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtComment.getText().toString().trim().equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("comment_text", edtComment.getText().toString().trim());
                    map.put("uid", FirebaseAuth.getInstance().getUid());
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:a");
                    map.put("time", sdf.format(new Date()));
                    ref.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityAddComment.this, "Comment Added", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ActivityAddComment.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityAddComment.this, "Add a comment first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateToolbar() {
        toolbar.setTitle("Add Comment");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        intent = getIntent();
        toolbar = findViewById(R.id.main_toolbar);
        edtComment = findViewById(R.id.edt_comment);
        btnAddComment = findViewById(R.id.btn_add_comment);
        progressBar = findViewById(R.id.progress_comment);
        String node_ref = intent.getStringExtra("node_ref");
        ref = FirebaseDatabase.getInstance().getReference("questions").child(node_ref).child("comments");
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
    }
    //dummy comment
}
