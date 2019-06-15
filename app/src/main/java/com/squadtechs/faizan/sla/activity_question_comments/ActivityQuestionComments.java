package com.squadtechs.faizan.sla.activity_question_comments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squadtechs.faizan.sla.R;
import com.squadtechs.faizan.sla.activity_add_comment.ActivityAddComment;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityQuestionComments extends AppCompatActivity {

    private TextView txtRegistrationNumber, txtTime, txtTitle, txtBody;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private Intent intent;
    private String node_ref;
    private CommentsAdapter adapter;
    private ArrayList<CommentsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_comments);
        initViews();
        populateToolbar();
        populateQuestionDetails();
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityQuestionComments.this));
        recyclerView.setAdapter(adapter);
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("questions").child(node_ref).child("comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        CommentsModel obj = ds.getValue(CommentsModel.class);
                        list.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ActivityQuestionComments.this, "No comment found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateQuestionDetails() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(intent.getStringExtra("uid"));
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                    txtRegistrationNumber.setText(map.get("registration_number"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        txtTime.setText(intent.getStringExtra("time"));
        txtTitle.setText(intent.getStringExtra("title"));
        txtBody.setText(intent.getStringExtra("body"));
    }

    private void populateToolbar() {
        toolbar.setTitle("Question Details");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.inflateMenu(R.menu.menu_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_add: {
                        Intent intent = new Intent(ActivityQuestionComments.this, ActivityAddComment.class);
                        intent.putExtra("node_ref", node_ref);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void initViews() {
        intent = getIntent();
        toolbar = findViewById(R.id.main_toolbar);
        txtTitle = findViewById(R.id.txt_title);
        txtBody = findViewById(R.id.txt_body);
        txtTime = findViewById(R.id.txt_time);
        txtRegistrationNumber = findViewById(R.id.txt_registration_number);
        recyclerView = findViewById(R.id.recycler_comments);
        node_ref = intent.getExtras().get("node_key").toString();
        Log.i("dxdiag", node_ref);
        ref = FirebaseDatabase.getInstance().getReference("questions").child(intent.getStringExtra("node_key"));
        list = new ArrayList<>();
        adapter = new CommentsAdapter(ActivityQuestionComments.this, list);
    }

}
