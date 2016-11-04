package com.medisurf.medisurf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.medisurf.medisurf.R.id.med;

public class getAlternatives extends AppCompatActivity  implements AsyncResponse {
    EditText medc_name;
    Button btnalternative;
    String ip = "172.30.102.171:8088/medisurf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("inside alternatives oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_alternatives);

        medc_name = (EditText) findViewById(med);
        btnalternative = (Button) findViewById(R.id.btnalter);

        btnalternative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {

                    HashMap postData = new HashMap();
                    postData.put("btnLogin", "Login");
                    postData.put("mobile", "android");
                    postData.put("med_name", medc_name.getText().toString());
                    System.out.println(medc_name.getText().toString());
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(getAlternatives.this, postData);
                    System.out.println("Before Logging in");
                    loginTask.execute("http://" + ip + "/GetAlternatives.php");
                    System.out.println("After Logging in....");

                } else {
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
        JSONObject jObj = new JSONObject();
        try {
            jObj = new JSONObject(output.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            if (jObj.getString("success").equals("1")) {
                Toast.makeText(this, "Account created Successfully",
                        Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(this, "Login Failed!!!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
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

