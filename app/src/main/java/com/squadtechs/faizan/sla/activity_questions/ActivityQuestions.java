package com.squadtechs.faizan.sla.activity_questions;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.squadtechs.faizan.sla.R;

public class ActivityQuestions extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        initViews();
        setListeners();
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: start activity for adding question
            }
        });
    }

    private void initViews() {
        fab = findViewById(R.id.fab_add_question);
    }

}
