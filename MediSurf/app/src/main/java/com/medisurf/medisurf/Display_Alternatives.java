package com.medisurf.medisurf;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Display_Alternatives extends AppCompatActivity {
    String results;
    String desc;
    Bundle extras;
    TextView salt_name;
    TextView Desc;
    JSONArray jarr;

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

        LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth() / 4;
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv1.setText("NAME");
        tv2.setText("BRAND");
        tv3.setText("PRICE");
        tv4.setText("mg/ml");
        l.addView(tv1,lp);
        l.addView(tv2,lp);
        l.addView(tv3,lp);
        l.addView(tv4,lp);
        //l.addView(tv2,lp);
        l3.addView(l);

        try {
            jarr = new JSONArray(results);
            for(int i=0;i<jarr.length();i++)
            {
                String mn = jarr.getJSONObject(i).getString("name");
                String bn = jarr.getJSONObject(i).getString("brand_name");
                String pr = jarr.getJSONObject(i).getString("price");
                String mg = jarr.getJSONObject(i).getString("mg_ml");
                add_dynamic(mn,bn,pr,mg);
            }
        }
        catch(Exception e)
        {

        }

        //System.out.println("ppppppppppppppppppppppppppppppppppppp"+results);
    }

    public void add_dynamic(String med,String bn,String pr,String mg)
    {
        LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth() / 4;
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv1.setText(med);
        tv2.setText(bn);
        tv3.setText(pr);
        tv4.setText(mg);
        l.addView(tv1,lp);
        l.addView(tv2,lp);
        l.addView(tv3,lp);
        l.addView(tv4,lp);
        //l.addView(v2,lp);
        l3.addView(l);
    }

}
