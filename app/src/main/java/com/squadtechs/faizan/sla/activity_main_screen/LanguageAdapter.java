package com.squadtechs.faizan.sla.activity_main_screen;

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
import java.util.Random;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.Holder> {
    private Context context;
    private ArrayList<LanguageModel> list;

    public LanguageAdapter(Context context, ArrayList<LanguageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.languages_row_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        LanguageModel obj = list.get(position);
        holder.txtTitle.setText(obj.Language);
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        holder.colorView.setBackgroundColor(Color.rgb(r, g, b));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class Holder extends RecyclerView.ViewHolder {
        View touchView, colorView;
        TextView txtTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            touchView = itemView.findViewById(R.id.touch_view);
            colorView = itemView.findViewById(R.id.color_view);
            txtTitle = itemView.findViewById(R.id.txt_language_name);
        }
    }
}
