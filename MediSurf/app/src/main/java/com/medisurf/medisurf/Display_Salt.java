package com.medisurf.medisurf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Display_Salt extends AppCompatActivity {
    String salt;
    String desc;
    Bundle extras;
    TextView salt_name;
    TextView Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__salt);

//        DisplayMetrics dm=new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width=dm.widthPixels;
//        int height=dm.heightPixels;

//        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.35));
        extras = getIntent().getExtras();
        salt = extras.getString("salt");
        desc = extras.getString("desc");
        salt_name  = (TextView) findViewById(R.id.salt_name);
        Desc = (TextView) findViewById(R.id.salt_description);
        salt_name.setText(salt.toString());
        Desc.setText(desc.toString());
    }

}
