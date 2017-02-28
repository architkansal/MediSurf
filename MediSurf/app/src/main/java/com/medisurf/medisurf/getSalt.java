package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
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
import static android.R.attr.name;


public class getSalt extends AppCompatActivity{

    AutoCompleteTextView med_name;
    Button btnsalt;
    String medicine_name;
    static getSalt mInstance;

    public static synchronized getSalt getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salt);
        mInstance = this;
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        med_name = (AutoCompleteTextView)findViewById(R.id.med_name);
        btnsalt = (Button)findViewById(R.id.btnsalt);
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,medicines);

        med_name.setAdapter(adapter);
        med_name.setThreshold(1);

        btnsalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    medicine_name = med_name.getText().toString();
                    NetworkRequests.genericsalt(medicine_name);
                    HashMap postData = new HashMap();
//                    postData.put("med_name", );
//                    System.out.println(med_name.getText().toString());
//                    PostResponseAsyncTask loginTask =
//                            new PostResponseAsyncTask(getSalt.this, postData);
//                    loginTask.execute("http://" + ip + "/GenericSalt.php");
//                    System.out.println("After Logging in....");
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


    public void processFinish(String output) {
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
