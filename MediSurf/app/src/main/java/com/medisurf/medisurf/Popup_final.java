package com.medisurf.medisurf;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

/**
 * Created by Kiran Konduru on 1/29/2017.
 */
public class Popup_final extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_final);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));
        EditText e1 = (EditText) findViewById(R.id.pop_age);
        EditText e2 = (EditText) findViewById(R.id.pop_gender);

        String gender = e2.getText().toString();
        String age = e1.getText().toString();

        findViewById(R.id.submit_Price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
