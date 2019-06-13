package com.squadtechs.faizan.sla.activity_questions;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squadtechs.faizan.sla.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionModel> list;
    private Context context;

    public QuestionAdapter(ArrayList<QuestionModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new QuestionViewHolder(LayoutInflater.from(context).inflate(R.layout.question_row_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionModel obj = list.get(position);
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        holder.colorView.setBackgroundColor(Color.rgb(r, g, b));
        holder.txtCount.setText(String.valueOf(position));
        holder.txtTitle.setText(obj.question_title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView txtCount, txtTitle;
        View colorView, touchView;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.color_view);
            touchView = itemView.findViewById(R.id.touch_view);
            txtCount = itemView.findViewById(R.id.txt_question_count);
            txtTitle = itemView.findViewById(R.id.txt_question_title);
        }
    }
}
