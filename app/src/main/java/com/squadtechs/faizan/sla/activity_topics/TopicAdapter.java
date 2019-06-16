package com.squadtechs.faizan.sla.activity_topics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squadtechs.faizan.sla.R;
import com.squadtechs.faizan.sla.activity_content_page.ActivityContentPage;
import com.squadtechs.faizan.sla.activity_main_screen.LanguageAdapter;
import com.squadtechs.faizan.sla.activity_main_screen.LanguageModel;

import java.util.ArrayList;
import java.util.Random;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {


    private Context context;
    private ArrayList<TopicsModel> list;
    private String language_key;

    public TopicAdapter(Context context, ArrayList<TopicsModel> list, String language_key) {
        this.context = context;
        this.list = list;
        this.language_key = language_key;
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TopicHolder(LayoutInflater.from(context).inflate(R.layout.languages_row_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        final TopicsModel obj = list.get(position);
        holder.txtTitle.setText(obj.Topic);
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        holder.colorView.setBackgroundColor(Color.rgb(r, g, b));
        holder.touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityContentPage.class);
                intent.putExtra("language_key", language_key);
                intent.putExtra("topic_key", obj.Topic);
                intent.putExtra("video_url", obj.Video);
                intent.putExtra("pdf_url", obj.PDF);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class TopicHolder extends RecyclerView.ViewHolder {
        View touchView, colorView;
        TextView txtTitle;

        public TopicHolder(@NonNull View itemView) {
            super(itemView);
            touchView = itemView.findViewById(R.id.touch_view);
            colorView = itemView.findViewById(R.id.color_view);
            txtTitle = itemView.findViewById(R.id.txt_language_name);
        }
    }
}
