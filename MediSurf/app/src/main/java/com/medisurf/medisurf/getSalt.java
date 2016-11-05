package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
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

import static android.R.attr.name;

public class getSalt extends AppCompatActivity implements AsyncResponse{

    EditText med_name;
    Button btnsalt;
    String ip = "172.30.102.171:8088/medisurf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salt);

        med_name = (EditText)findViewById(R.id.med_name);
        btnsalt = (Button)findViewById(R.id.btnsalt);

        btnsalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {

                    HashMap postData = new HashMap();
                    postData.put("btnLogin", "Login");
                    postData.put("mobile", "android");
                    postData.put("med_name", med_name.getText().toString());
                    System.out.println(med_name.getText().toString());
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(getSalt.this, postData);
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

                Toast.makeText(this, "Account created Successfully",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, Display_Salt.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("salt", jObj.getString("generic_salt"));
                i.putExtra("desc" , jObj.getString("description"));
                startActivity(i);
                this.finish();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
