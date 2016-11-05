package com.medisurf.medisurf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Display_Alternatives extends AppCompatActivity {
    String results;
    String desc;
    Bundle extras;
    TextView salt_name;
    TextView Desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__alternatives);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        extras = getIntent().getExtras();
        results = extras.getString("results");
    }
}
