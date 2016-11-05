package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.medisurf.medisurf.URLGenerator.ip;

public class MainActivity extends AppCompatActivity implements AsyncResponse{
    TextView counts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        counts = (TextView) (findViewById(R.id.text_number));
        counts.setText("11523");
        if(isNetworkAvailable()) {

            HashMap postData = new HashMap();
            postData.put("btnLogin", "Login");
            postData.put("mobile", "android");
            PostResponseAsyncTask loginTask =
                    new PostResponseAsyncTask(MainActivity.this, postData);
            System.out.println("Before Logging in");
            loginTask.execute("http://" + ip + "/get_count.php");
            System.out.println("After Logging in....");

            HashMap postData2 = new HashMap();
            postData.put("btnLogin", "Login");
            postData.put("mobile", "android");
            PostResponseAsyncTask loginTask2 =
                    new PostResponseAsyncTask(MainActivity.this, postData);
            System.out.println("Before Logging in");
            loginTask2.execute("http://" + ip + "/most_frequent.php");
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
                counts.setText(jObj.getString("count").toString());
            }
            else if(jObj.getString("success").equals("2"))
            {
                System.out.println(jObj.toString());
                // anshul --->> print the klist of 5 most used original and alternative medicines, result is sorted already in descending order

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
