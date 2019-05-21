package com.squadtechs.faizan.sla.activity_main_screen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squadtechs.faizan.sla.R;

import java.util.ArrayList;

public class ActivityMainScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<LanguageModel> list;
    private LanguageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initViews();
        populateToolbar();
        prepareRecyclerView();
    }

    private void initViews() {
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.recycler_language);
        list = new ArrayList<LanguageModel>();
        adapter = new LanguageAdapter(ActivityMainScreen.this, list);
    }

    private void populateToolbar() {
        toolbar.setTitle("Languages");
    }

    private void prepareRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMainScreen.this));
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Languages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        LanguageModel obj = i.getValue(LanguageModel.class);
                        list.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ActivityMainScreen.this, "No language found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
