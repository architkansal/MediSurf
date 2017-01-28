package com.medisurf.medisurf;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.medisurf.medisurf.URLGenerator.ip;

public class saltDescription extends AppCompatActivity {
    EditText txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salt_description);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        String host_name ="The App helps to optimize your medical bill. All you have to do is" +
                " enter your prescription in the app and it will generate the optimized" +
                " medicine bill. This is possible because doctors usually prescribe medicine" +
                " name rather than their generic salts. But there are many alternatives you can" +
                " take because they contain the same generic salt. But changing the brand name might" +
                " change the price drastically. Other features of app include getting generic salts, " +
                "all brands for a particular medicine and all alternatives for a medicine etc."+
                "\n\n\n" +
                "DISCLAIMER : This app displays alternatives based on generic salts from the national drug databases." +
                "users are requested to consult their doctor or any other professional beofore completely relying on" +
                "the information provided .app developers are not responsible for any harm caused to the user based on " +
                "the information available on the app" ;
        TextView tvName = (TextView)findViewById(R.id.txtview);
        tvName.setText(host_name);

        }

}
