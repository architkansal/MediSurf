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

    Button btnfeed;
    JSONArray org;
    JSONArray finals = new JSONArray();

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
            org = jobj.getJSONArray("originals");
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

        pb = (TextView) findViewById(R.id.pb_total);
        ob = (TextView) findViewById(R.id.ob_total);

        pb.setText(String.valueOf(Prescribed_total));
        ob.setText(String.valueOf(Optimize_total));

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
                                ct.setText(nm);
                                cp.setText(pz);
                                JSONObject j2 = jo.getJSONObject(pcur);
                                String pz2 = j2.getString("price");

                                Optimize_total = Optimize_total - Float.valueOf(pz2) + Float.valueOf(pz);

                                ob.setText(String.valueOf(Optimize_total));
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

                if(isNetworkAvailable())
                {
                    try {

                        for (int w = 0; w < txt.size(); w++) {
                            finals.put(w, txt.get(w).toString());
                        }

                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                        HashMap postData = new HashMap();

                    postData.put("btnLogin", "Login");
                    postData.put("mobile", "android");
                    postData.put("originals", org.toString());
                    postData.put("finals", finals.toString());
                    postData.put("org_price", pb.getText());
                    postData.put("altered_price", ob.getText());
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(Display_Bill.this, postData);
                    System.out.println("Before Logging in");
                    loginTask.execute("http://" + ip + "/GenericSalt.php");
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

                Intent i = new Intent(this, Display_Salt.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("name","Salt");
                i.putExtra("salt", jObj.getString("generic_salt"));
                i.putExtra("desc" , jObj.getString("description"));
                startActivity(i);
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

        Prescribed_total = Prescribed_total + Float.valueOf(p1);

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

        Optimize_total = Optimize_total + Float.valueOf(p2);

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
