package com.squadtechs.faizan.sla.activity_questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squadtechs.faizan.sla.R;
import com.squadtechs.faizan.sla.activity_add_question.ActivityAddQuestion;

import java.util.ArrayList;
import java.util.List;

public class ActivityQuestions extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<QuestionModel> list;
    private QuestionAdapter adapter;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        initViews();
        populateToolbar();
        setListeners();
        fetchData();
    }

    private void fetchData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityQuestions.this));
        recyclerView.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        QuestionModel obj = ds.getValue(QuestionModel.class);
                        list.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ActivityQuestions.this, "No data found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityQuestions.this, ActivityAddQuestion.class));
            }
        });
    }

    private void populateToolbar() {
        toolbar.setTitle("Problem Questions");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.main_toolbar);
        fab = findViewById(R.id.fab_add_question);
        recyclerView = findViewById(R.id.recycler_questions);
        list = new ArrayList<QuestionModel>();
        adapter = new QuestionAdapter(list, ActivityQuestions.this);
        ref = FirebaseDatabase.getInstance().getReference("questions");
    }

}
