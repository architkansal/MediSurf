package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import static com.medisurf.medisurf.URLGenerator.ip;

/**
 * Created by Anshul Goyal on 04-11-2016.
 */

public class OptimizeBill extends AppCompatActivity{

    static OptimizeBill mInstance;
    LinearLayout l1;
    Vector<AutoCompleteTextView> v;
    int flag=0;
    String nou="1 ";
    String mg="1 ";
    public static String[] medicines={"Discorb","Acarex","Glubose","Liofen XL","Parafon","Spinofen","Defnom","Laza","Defzar","Triben-v","Clima forte","Camyda"};

    public static synchronized OptimizeBill getInstance(){
        return mInstance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimiziebill);
        mInstance = this;
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        v = new Vector<AutoCompleteTextView>();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            for(int i=0;i<3;i++) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
                Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                int width = display.getWidth() / 2;
                LinearLayout l = new LinearLayout(view.getContext());
                l.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv1 = new TextView(view.getContext());
                AutoCompleteTextView et1 = new AutoCompleteTextView(view.getContext());
                if(i==0) tv1.setText("Medicine Name : ");
                else if(i==1) tv1.setText("No. of Units : ");
                else tv1.setText("mg/ml : ");
                tv1.setTextSize(18);
                l.addView(tv1,lp);
                l.addView(et1, lp);
                v.add(et1);
                ArrayAdapter adapter = new
                        ArrayAdapter(view.getContext(),android.R.layout.simple_list_item_1,medicines);

                et1.setAdapter(adapter);
                et1.setThreshold(1);

                ll.addView(l);
                if(i==2)
                {
                    l1 = (LinearLayout) findViewById(R.id.ll);
                    display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                    width = display.getWidth() / 1;
                    l = new LinearLayout(view.getContext());
                    l.setOrientation(LinearLayout.HORIZONTAL);
                    lp = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                            ImageView iv = new ImageView(view.getContext());
                    iv.setMinimumHeight(2);
                    iv.layout(0,5,0,5);
                    iv.setBackgroundColor(Color.parseColor("#2b282e"));
                    l.addView(iv,lp);
                    l1.addView(l);
                }
            }
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (isNetworkAvailable()) {
                HashMap postData = new HashMap();
                int j=0;
                String mn = null,ut = null,mgml = null;
                for (int i = 0; i < v.size(); i++) {
                    EditText et_mn = v.get(i);
                    i++;
                    EditText et_nou = v.get(i);
                    i++;
                    EditText et_mg = v.get(i);
                    mn = "med_name"+Integer.toString(j);
                    ut = "unit"+Integer.toString(j);
                    mgml = "mg_ml"+Integer.toString(j);

                    nou = nou + et_nou.getText().toString() +" ";
                    mg = mg + et_mg.getText().toString() + " ";

                    postData.put(mn, ""+et_mn.getText().toString());
                    postData.put(ut, ""+et_nou.getText().toString());
                    postData.put(mgml , ""+et_mg.getText().toString());
                    j++;
                }
                postData.put("total_med",Integer.toString(j));
                String total_med = Integer.toString(j);
                NetworkRequests.optimisebill(postData);

//                PostResponseAsyncTask loginTask =
//                        new PostResponseAsyncTask(OptimizeBill.this, postData);
//                System.out.println("Before Logging in");
//                loginTask.execute("http://" + ip + "/OptimiseBill.php");
//                System.out.println("After Logging in....");
            }

            else {
                Context context = getApplicationContext();
                CharSequence text = "No network found. Try again later!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            }
        });
    }


    public void processFinish(String output) {
        System.out.println(" o/p is : ");
        System.out.println(output);
        System.out.println("===================  " + output.toString() + "    =============");
        JSONObject jObj= new JSONObject();
        try {
            jObj = new JSONObject(output.toString());
            System.out.println(jObj);
            System.out.println("Here");
        }
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            System.out.println("Here now");
            if (jObj.getString("success").equals("1"))
            {
                //Toast.makeText(this, "Account created Successfully",
                  //      Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, Display_Bill.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("data", jObj.toString());
                i.putExtra("nou",nou);
                i.putExtra("mg_ml",mg);
                startActivity(i);

            }
            else
            {
                Toast.makeText(this, "Login Failed!!!",
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
