package com.medisurf.medisurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, Home.class);
        Intent intent = new Intent(this,OptimizeBill.class);
        startActivity(intent);
        this.finish();

    }
}
