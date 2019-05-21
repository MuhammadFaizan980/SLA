package com.squadtechs.faizan.sla.activity_main_screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.squadtechs.faizan.sla.R;

public class LanguageAdapter {
    class Holder extends RecyclerView.ViewHolder {

        View touchView;
        TextView txtTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            touchView = itemView.findViewById(R.id.touch_view);
            txtTitle = itemView.findViewById(R.id.txt_language_name);
        }
    }
}
