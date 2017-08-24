package com.medisurf.medisurf;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Kiran Konduru on 1/29/2017.
 */
public class Popup_final extends AppCompatActivity {

    HashMap postData = new HashMap();
    String age;
    String gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_final);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));

        Intent intent = getIntent();
        postData = (HashMap<String,String>)intent.getSerializableExtra("map");

        findViewById(R.id.submit_Price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e1 = (EditText) findViewById(R.id.pop_age);
                EditText e2 = (EditText) findViewById(R.id.pop_gender);
                gender = e2.getText().toString();
                age = e1.getText().toString();
                if(gender.equals("male")) gender = "M";
                else gender  = "F";
                postData.put("gender",gender);
                postData.put("age",age);
                NetworkRequests.savestat(postData);
                finish();
            }
        });
    }
}
