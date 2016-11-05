package com.medisurf.medisurf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

public class saltDescription extends AppCompatActivity {
    EditText txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salt_description);
        String host_name ="The App helps to optimize your medical bill. All you have to do is" +
                " enter your prescription in the app and it will generate the optimized" +
                " medicine bill. This is possible because doctors usually prescribe medicine" +
                " name rather than their generic salts. But there are many alternatives you can" +
                " take because they contain the same generic salt. But changing the brand name might" +
                " change the price drastically. Other features of app include getting generic salts, " +
                "all brands for a particular medicine and all alternatives for a medicine etc." ;
        TextView tvName = (TextView)findViewById(R.id.txtview);
        tvName.setText(host_name);
    }

}
