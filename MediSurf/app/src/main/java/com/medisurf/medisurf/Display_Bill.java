package com.medisurf.medisurf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class Display_Bill extends AppCompatActivity {
    String salt;
    String desc;
    Bundle extras;
    TextView salt_name;
    JSONObject jobj;
    TextView Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__bill);

        extras = getIntent().getExtras();
        try {
            jobj = new JSONObject(extras.getString("data"));
        } catch (Exception e) {
            System.out.println(e);
        }

        // Render Data Now...
    }


}
