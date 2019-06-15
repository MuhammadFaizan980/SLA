package com.squadtechs.faizan.sla.activity_add_question;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squadtechs.faizan.sla.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ActivityAddQuestion extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtQuestionTitle, edtQuestionDescription;
    private Button btnAdd;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        initViews();
        populateToolbar();
        addQuestionToFirebase();
    }

    private void initViews() {
        toolbar = findViewById(R.id.main_toolbar);
        edtQuestionTitle = findViewById(R.id.edt_question_title);
        edtQuestionDescription = findViewById(R.id.edt_question_body);
        btnAdd = findViewById(R.id.btn_add_question);
        progressBar = findViewById(R.id.progress_question);
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("questions");
    }

    private void populateToolbar() {
        toolbar.setTitle("Add Question");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addQuestionToFirebase() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtQuestionTitle.getText().toString().trim().equals("") && !edtQuestionDescription.getText().toString().trim().equals("")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("question_title", edtQuestionTitle.getText().toString().trim());
                    map.put("question_description", edtQuestionDescription.getText().toString().trim());
                    map.put("uid", firebaseAuth.getUid());
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:a");
                    map.put("time", sdf.format(new Date()));
                    ref.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityAddQuestion.this, "Question Added", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ActivityAddQuestion.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityAddQuestion.this, "Add a question first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
