package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Anshul Goyal on 04-11-2016.
 */

public class OptimizeBill extends AppCompatActivity implements AsyncResponse{

    String ip = "172.30.102.171:8088/medisurf";
    LinearLayout l1;
    Vector<EditText> v;
    int flag=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimiziebill);
        v = new Vector<EditText>();
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
                    EditText et1 = new EditText(view.getContext());
                    if(i==0) tv1.setText("Medicine Name : ");
                    else if(i==1) tv1.setText("No. of Units : ");
                    else tv1.setText("mg/ml (Optional) : ");
                    l.addView(tv1,lp);
                    l.addView(et1, lp);
                    v.add(et1);
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
                    for (int i = 0; i < v.size(); i++) {

                        EditText et_mn = v.get(i);
                        i++;
                        EditText et_nou = v.get(i);
                        i++;
                        EditText et_mg = v.get(i);

                        postData.put("med_name"+String.valueOf(j), et_mn.getText());
                        postData.put("unit"+String.valueOf(j), et_nou.getText());
                        postData.put("mg_ml"+String.valueOf(j), et_mg.getText());

                        j++;

                    }
                    System.out.println("HELLLOOOO" + postData);
                    postData.put("total_med",j);
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(OptimizeBill.this, postData);
                    System.out.println("Before Logging in");
                    loginTask.execute("http://" + ip + "/OptimizeBill.php");
                    System.out.println("After Logging in....");
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
                Toast.makeText(this, "Account created Successfully",
                        Toast.LENGTH_LONG).show();
                this.finish();
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