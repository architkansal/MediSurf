package com.medisurf.medisurf;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
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
import static com.medisurf.medisurf.R.id.med;
import static com.medisurf.medisurf.URLGenerator.ip;

public class getAlternatives extends AppCompatActivity {
    AutoCompleteTextView medc_name;
    Button btnalternative;
    String medicine_name;
    static getAlternatives minstance;

    public static synchronized getAlternatives getInstance(){
        return minstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("inside alternatives oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_alternatives);
        minstance = this;
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        medc_name = (AutoCompleteTextView) findViewById(med);
        btnalternative = (Button) findViewById(R.id.btnalter);
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,medicines);

        medc_name.setAdapter(adapter);
        medc_name.setThreshold(1);

        btnalternative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {

                    HashMap postData = new HashMap();
                    medicine_name = medc_name.getText().toString();
                    NetworkRequests.getbrands(medicine_name);
//                    postData.put("med_name", medc_name.getText().toString());
//                    PostResponseAsyncTask loginTask =
//                            new PostResponseAsyncTask(getAlternatives.this, postData);
//                    System.out.println("Before Logging in");
//                    loginTask.execute("http://" + ip + "/GetAlternatives.php");
//                    System.out.println("After Logging in....");

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

//    @Override
//    public void processFinish(String output) {
//        JSONObject jObj = new JSONObject();
//        try {
//            jObj = new JSONObject(output.toString());
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//        try {
//            if (jObj.getString("success").equals("1")) {
//                Intent i = new Intent(this, Display_Alternatives.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra("name","Alternatives");
//                i.putExtra("results", jObj.getString("results"));
//                startActivity(i);
//            } else {
//                Toast.makeText(this, "Operation Failed!",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void onSuccess(JSONObject response) throws JSONException {
        System.out.println(response);
        Intent i = new Intent(this, Display_Alternatives.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("name","Alternatives");
        i.putExtra("results", response.getString("results"));
        startActivity(i);
    }

    public void onFailure(JSONObject response) throws JSONException {
        String message = "Not Found";
        message = response.getString("message");
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}


