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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.medisurf.medisurf.OptimizeBill.medicines;
import static com.medisurf.medisurf.URLGenerator.ip;

public class GetBrands extends AppCompatActivity implements  AsyncResponse{

    AutoCompleteTextView med_name;
    Button btnbrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_brands);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        med_name = (AutoCompleteTextView) findViewById(R.id.medicine);
        btnbrand = (Button)findViewById(R.id.btnbrand);

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,medicines);

        med_name.setAdapter(adapter);
        med_name.setThreshold(1);

        btnbrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {

                    HashMap postData = new HashMap();
                    postData.put("btnLogin", "Login");
                    postData.put("mobile", "android");
                    postData.put("med_name", med_name.getText().toString());
                    System.out.println(med_name.getText().toString());
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(GetBrands.this, postData);
                    System.out.println("Before Logging in");
                    loginTask.execute("http://" + ip + "/ShowBrands.php");
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
                Intent i = new Intent(this, Display_Brands.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("name","Brand");
                i.putExtra("salt", jObj.getString("brand_name"));
                i.putExtra("desc" , jObj.getString("description"));
                startActivity(i);
//     this.finish();
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
