package com.medisurf.medisurf;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

//        findViewById(R.id.optimize_bill).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),OptimizeBill.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        findViewById(R.id.get_alternatives).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),getAlternatives.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        findViewById(R.id.get_brands).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),GetBrands.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        findViewById(R.id.get_generic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),getSalt.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }
}
