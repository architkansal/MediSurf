package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import jp.wasabeef.blurry.Blurry;

import static com.medisurf.medisurf.URLGenerator.ip;

public class MainActivity extends AppCompatActivity {
    TextView counts;
    static MainActivity mInstance;

    public static synchronized MainActivity getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        NetworkRequests.Home();
        setContentView(R.layout.activity_main);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

//        Blurry.with(MainActivity.this)
//                .radius(25)
//                .sampling(1)
//                .color(Color.argb(66, 0, 255, 255))
//                .async()
//                .capture(findViewById(R.id.my_pic))
//                .into((ImageView) findViewById(R.id.my_pic));

        counts = (TextView) (findViewById(R.id.text_number));
        counts.setText("₹11523");
        LinearLayout l4 = (LinearLayout) findViewById(R.id.l4);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth() / 2;
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        tv1.setText("PRESCRIBED");
        tv2.setText("ALTERNATIVE");
        tv1.setTextColor(Color.parseColor("#01579B"));
        tv2.setTextColor(Color.parseColor("#01579B"));
        tv1.setTypeface(null, Typeface.BOLD);
        tv2.setTypeface(null, Typeface.BOLD);
        tv1.setGravity(Gravity.CENTER);
        tv2.setGravity(Gravity.CENTER);
        tv1.setTextSize(25);
        tv2.setTextSize(25);
        l.addView(tv1,lp);
        l.addView(tv2, lp);
        l4.addView(l);
    }

    public void processFinish(JSONObject jObj) {

        try {
            if (jObj.getString("success").equals("1"))
            {
                counts.setText("₹"+jObj.getString("count").toString());
                JSONArray jarr = jObj.getJSONArray("results");

                for(int i=0;i<jarr.length();i++)
                {
                    String orig = jarr.getJSONObject(i).getString("original");
                    String alter = jarr.getJSONObject(i).getString("alternative");

                    LinearLayout l4 = (LinearLayout) findViewById(R.id.l4);
                    Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                    int width = display.getWidth() / 2;
                    LinearLayout l = new LinearLayout(this);
                    l.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv1 = new TextView(this);
                    TextView tv2 = new TextView(this);
                    tv1.setTextColor(Color.parseColor("#01579B"));
                    tv2.setTextColor(Color.parseColor("#01579B"));
                    tv1.setTypeface(null, Typeface.BOLD);
                    tv2.setTypeface(null, Typeface.BOLD);
                    tv1.setText(orig);
                    tv2.setText(alter);
                    tv2.setGravity(Gravity.CENTER);
                    tv1.setGravity(Gravity.CENTER);
                    tv2.setTextSize(20);
                    tv1.setTextSize(20);
                    l.addView(tv1,lp);
                    l.addView(tv2, lp);
                    l4.addView(l);
                }
            }
            else
            {
                counts.setText("11523");
                Toast.makeText(this, "Connection Failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
