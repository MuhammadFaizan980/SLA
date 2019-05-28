package com.squadtechs.faizan.sla.activity_content_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;
import com.squadtechs.faizan.sla.R;

public class ActivityContentPage extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_page);
        initViews();
    }

    private void initViews() {
        pdfView = findViewById(R.id.pdfView);
    }

}
