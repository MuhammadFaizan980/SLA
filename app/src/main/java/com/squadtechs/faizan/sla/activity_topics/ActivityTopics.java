package com.squadtechs.faizan.sla.activity_topics;

import android.support.annotation.NonNull;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squadtechs.faizan.sla.R;
import com.squadtechs.faizan.sla.activity_main_screen.LanguageAdapter;
import com.squadtechs.faizan.sla.activity_main_screen.LanguageModel;

import java.util.ArrayList;

public class ActivityTopics extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<TopicsModel> list;
    private TopicAdapter adapter;
    private String LANGUAGE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        initViews();
        populateToolbar();
        prepareRecyclerView();
    }

    private void initViews() {
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.recycler_topics);
        list = new ArrayList<TopicsModel>();
        LANGUAGE_KEY = (String) getIntent().getExtras().get("language_key");
        adapter = new TopicAdapter(ActivityTopics.this, list, LANGUAGE_KEY);
    }

    private void populateToolbar() {
        toolbar.setTitle("Topics");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void prepareRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityTopics.this));
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Languages").child(LANGUAGE_KEY).child("Topics")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot i : dataSnapshot.getChildren()) {
                                TopicsModel obj = i.getValue(TopicsModel.class);
                                list.add(obj);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(ActivityTopics.this, "No topic found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

}
