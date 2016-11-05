package com.medisurf.medisurf;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

public class Display_Bill extends AppCompatActivity {
    String salt;
    String desc;
    Bundle extras;
    TextView salt_name;
    JSONObject jobj;
    TextView Desc;
    Vector<Button> v;
    HashMap<Integer,Integer> hm;
    HashMap<Integer, Integer> cnt;
    int clicked_button;
    Vector<TextView> txt;
    Vector<TextView> prc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__bill);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        v = new Vector<Button>();
        hm = new HashMap<>();
        cnt  = new HashMap<>();
        txt = new Vector<>();
        prc = new Vector<>();
        extras = getIntent().getExtras();
        try {
            jobj = new JSONObject(extras.getString("data"));
            int count = 0;
            int k=0;
            JSONArray org = jobj.getJSONArray("originals");
            for(int i=1;i<jobj.length()-2;i++)
            {
                JSONArray jo = jobj.getJSONArray(Integer.toString(count));

                JSONObject j1 =jo.getJSONObject(0);
                hm.put(count,0);
                String oname = org.getString(k); k++;
                cnt.put(count,jo.length());
                String oprice = org.getString(k); k++;

                add_dynamic(oname,j1.getString("name"),oprice,j1.getString("price"));
                count++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Render Data Now...

        for(int i=0;i<v.size();i++) {
            clicked_button = i;
            v.get(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    for(int x=0;x<v.size();x++)
                    {
                        if(view==v.get(x)){
                            int cur = hm.get(x);
                            int siz = cnt.get(x);
                            cur = (cur+1)%siz;
                            hm.put(x,cur);
                            TextView ct = txt.get(x);
                            TextView cp = prc.get(x);
                            try {
                                JSONArray jo = jobj.getJSONArray(Integer.toString(x));
                                JSONObject j1 =jo.getJSONObject(cur);
//                                String oname = org.getString(k); k++;
//                                cnt.put(count,jo.length());
//                                String oprice = org.getString(k); k++;
                                String nm = j1.getString("name");
                                String pz = j1.getString("price");
                                ct.setText(nm);
                                cp.setText(pz);
                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }


                        }
                    }
                }
            });
        }
    }

    public void add_dynamic(String med1,String med2,String p1,String p2)
    {
        System.out.println("Anshul " + med1 + med2 + p1 + p2);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth() / 2;
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        tv1.setText("Original :" +med1);
        tv2.setText(p1);
        l.addView(tv1,lp);
        l.addView(tv2,lp);
        l2.addView(l);


        width = display.getWidth() / 3;
        l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv3.setText("Alternative :"+med2);
        tv4.setText(p2);
        Button b = new Button(this);
        b.setText("Change");
        b.setMinimumHeight(8);
        l.addView(tv3,lp);
        l.addView(tv4,lp);
        l.addView(b,lp);
        v.add(b);
        txt.add(tv3);
        prc.add(tv4);
        l2.addView(l);


        width = display.getWidth() / 1;
        l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView iv = new ImageView(this);
        iv.setMinimumHeight(2);
        iv.layout(0,5,0,5);
        iv.setBackgroundColor(Color.parseColor("#2b282e"));
        l.addView(iv,lp);
        l2.addView(l);
    }
}
