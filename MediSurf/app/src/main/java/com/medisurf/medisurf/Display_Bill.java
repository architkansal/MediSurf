package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import static com.medisurf.medisurf.URLGenerator.ip;

public class Display_Bill extends AppCompatActivity implements AsyncResponse{
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
    float Optimize_total;
    float Prescribed_total;
    TextView ob;
    TextView pb;
    HashMap postData = new HashMap();
    Button btnfeed;
    JSONArray org;
    JSONObject orig = new JSONObject();
    JSONObject finals = new JSONObject();
    String nou[];
    String mg[];
    Vector<Integer> total_units;

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
        String s1 = extras.getString("nou");
        String s2 = extras.getString("mg_ml");


        nou = s1.split("\\s");
        mg = s2.split("\\s");

        total_units = new Vector<>();

        for(int i=1;i<nou.length;i++)
        {
            total_units.add(Integer.valueOf(nou[i])*Integer.valueOf(mg[i]));
            //System.out.println("hello sumit : " + nou[i]);
        }

        //for(int i=0;i<total_units.size();i++) System.out.println("Bye"+total_units);

        try {
            jobj = new JSONObject(extras.getString("data"));
            int count = 0;
            int k=0;
            org = jobj.getJSONArray("originals");
//            orig = jobj.getJSONObject("originals");
            for(int i=1;i<jobj.length()-2;i++)
            {
                JSONArray jo = jobj.getJSONArray(Integer.toString(count));

                JSONObject j1 =jo.getJSONObject(0);
                hm.put(count,0);
                String oname = org.getString(k); k++;
                cnt.put(count,jo.length());
                String oprice = org.getString(k); k++;
                String omgml = org.getString(k); k++;
                postData.put("originals"+Integer.toString(count),oname);
                add_dynamic(count,oname,j1.getString("name"),oprice,j1.getString("price"),omgml,j1.getString("mg_ml"));
                count++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        pb = (TextView) findViewById(R.id.pb_total);
        ob = (TextView) findViewById(R.id.ob_total);

        String sf1 = String.format("%.2f",Prescribed_total);
        pb.setText("₹"+sf1);

        String sf2 = String.format("%.2f",Optimize_total);
        ob.setText("₹"+sf2);

        // Render Data Now...

        for(int i=0;i<v.size();i++) {
            clicked_button = i;
            v.get(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    for(int x=0;x<v.size();x++)
                    {
                        if(view==v.get(x)){
                            int cur = hm.get(x);
                            int pcur = cur;
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
                                ct.setText("Alternate: "+nm);
                                ct.setTextSize(15);
                                ct.setGravity(Gravity.CENTER);
                                float f = Float.valueOf(pz)/Float.valueOf(j1.getString("mg_ml"));
                                f = f * total_units.get(x);
                                String sf1 = String.format("%.2f",f);
                                cp.setText("₹"+sf1);
                                cp.setTextSize(20);
                                cp.setGravity(Gravity.CENTER);

                                JSONObject j2 = jo.getJSONObject(pcur);
                                String pz2 = j2.getString("price");
                                float f1 = Float.valueOf(pz2)/Float.valueOf(j2.getString("mg_ml"));
                                f1 = f1 * total_units.get(x);
                                Optimize_total = Optimize_total - f1 + f;

                                String sof1 = String.format("%.2f",Optimize_total);
                                ob.setText("₹"+String.valueOf(sof1));
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

        btnfeed = (Button)findViewById(R.id.btnfeedback);

        btnfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nn = 0;
                if(isNetworkAvailable())
                {
                    try {

                        for (int w = 0; w < txt.size(); w++) {
                            String str = (txt.get(w)).getText().toString();
                            int z=0;
                            while(str.charAt(z)!=':') z++;
                            str = str.substring(z+1,str.length()-1);
                            finals.put(Integer.toString(w),str);
                            postData.put("finals"+Integer.toString(w),str);
                            nn++;
                        }

                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }

                    postData.put("btnLogin", "Login");
                    postData.put("mobile", "android");
//                    postData.put("originals", orig.toString());
//                    postData.put("finals", finals.toString());
                    postData.put("org_price", pb.getText());
                    postData.put("altered_price", ob.getText());
                    postData.put("num" , Integer.toString(nn));
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(Display_Bill.this, postData);
                    System.out.println("Before Logging in");
                    loginTask.execute("http://" + ip + "/saveStat.php");
                    System.out.println("After Logging in....");

                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "No network found. Try again later!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

    }


    @Override
    public void processFinish(String output) {
        System.out.println(" o/p is : ");
        System.out.println(output);
        System.out.println("===================  " + output.toString() + "    =============");
        JSONObject jObj= new JSONObject();
        try {
            jObj = new JSONObject(output.toString());
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            if (jObj.getString("success").equals("1"))
            {

                //Toast.makeText(this, "Account created Successfully",
                //      Toast.LENGTH_LONG).show();
//                this.finish();
            }
            else
            {
                Toast.makeText(this, "Connection Failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }


    public void add_dynamic(int count,String med1,String med2,String p1,String p2,String mg1,String mg2)
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
        tv1.setText("Original : " +med1);
        tv1.setTextSize(15);
        tv1.setGravity(Gravity.CENTER);
        float f1 = Float.valueOf(p1)/Float.valueOf(mg1);

        f1 = f1 * total_units.get(count);
        System.out.println(f1);
        String sf1 = String.format("%.2f",f1);

        tv2.setText("₹"+sf1);
        tv2.setTextSize(20);
        tv2.setGravity(Gravity.CENTER);

        Prescribed_total = Prescribed_total + f1;

        l.addView(tv1,lp);
        l.addView(tv2,lp);
        l2.addView(l);


        width = display.getWidth() / 2;
        l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv3.setText("Alternative :"+med2);
        tv3.setTextSize(15);
        tv3.setGravity(Gravity.CENTER);
        float f2 = Float.valueOf(p2)/Float.valueOf(mg2);
        f2 = f2 * total_units.get(count);
        String sf2 = String.format("%.2f",f2);
        tv4.setText("₹"+sf2);
        tv4.setTextSize(20);
        tv4.setGravity(Gravity.CENTER);

        Optimize_total = Optimize_total + f2;


        txt.add(tv3);
        prc.add(tv4);
        l.addView(tv3,lp);
        l.addView(tv4,lp);
        l2.addView(l);

        width = display.getWidth() / 1;
        l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button b = new Button(this);
        b.setText("Another Alternative");
        b.setMinimumHeight(8);
        b.setGravity(Gravity.CENTER);
        l.addView(b,lp);
        v.add(b);
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

        //System.out.println("SUMIT BYE");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
