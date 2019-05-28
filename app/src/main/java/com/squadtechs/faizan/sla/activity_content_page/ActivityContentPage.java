package com.squadtechs.faizan.sla.activity_content_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.barteksc.pdfviewer.PDFView;
import com.squadtechs.faizan.sla.R;

public class ActivityContentPage extends AppCompatActivity {

    private PDFView pdfView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_page);
        initViews();
        populateToolbar();
        loadData();
    }

    private void initViews() {
        pdfView = findViewById(R.id.pdfView);
        toolbar = findViewById(R.id.main_toolbar);
    }

    private void populateToolbar() {
        toolbar.setTitle("Study");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {

    }

}
