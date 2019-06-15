package com.squadtechs.faizan.sla.activity_question_comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squadtechs.faizan.sla.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder> {
    private Context context;
    private ArrayList<CommentsModel> list;

    public CommentsAdapter(Context context, ArrayList<CommentsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.comment_row_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {
        CommentsModel obj = list.get(position);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(obj.uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                    holder.txtRegistrationNumber.setText(map.get("registration_number"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.txtTime.setText(obj.time);
        holder.txtComment.setText(obj.comment_text);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView txtRegistrationNumber, txtTime, txtComment;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            txtRegistrationNumber = itemView.findViewById(R.id.txt_registration_number);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtComment = itemView.findViewById(R.id.txt_comment_body);
        }
    }
}
