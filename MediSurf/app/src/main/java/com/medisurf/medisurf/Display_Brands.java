package com.medisurf.medisurf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Display_Brands extends AppCompatActivity {
    String salt;
    String desc;
    Bundle extras;
    TextView salt_name;
    TextView Desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__brands);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        extras = getIntent().getExtras();
        salt = extras.getString("salt");
        desc = extras.getString("desc");
        salt_name  = (TextView) findViewById(R.id.brand_name);
        Desc = (TextView) findViewById(R.id.brand_desc);
        salt_name.setText(salt.toString());
        Desc.setText(desc.toString());
    }
}
