package com.medisurf.medisurf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anshul Goyal on 05-11-2016.
 */

public class Drawer extends Fragment {

    private ActionBarDrawerToggle mdrawertoggle;
    private static DrawerLayout mdrawerlayout;
    private RecyclerView recyclerView;
    private View drawer_container;
    private R_ViewAdapter adapter;
    public Drawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.r_view);

        adapter=new R_ViewAdapter(getActivity(),get_data());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public List<List_item> get_data(){
        List<List_item> data=new ArrayList<>();
//<<<<<<< HEAD
//        int[] icons={R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,R.drawable.d5,R.drawable.d6,R.drawable.d7,R.drawable.d8,R.drawable.d9,R.drawable.d1};
//        String[] titles={"About","Events","Spotlights","Proshow","Initiative","Workshop","Attraction","Team","Login/Register"};
//        SharedPreferences prefs = this.getActivity().getSharedPreferences(Constants.APP_PREFERENCE, this.getActivity().MODE_PRIVATE);
//        String restoredText = prefs.getString(Constants.isSignedIN, null);
//=======
        String[] titles={"* Home Page","* About MediSurf","* Customize Bill","* Get Alternatives","* Get Brands","* Get Generic Name"};

        for (int i=0;i<titles.length;i++){
            List_item current= new List_item();
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }
    public void setup(int drawer_fragment, DrawerLayout drawerLayout, Toolbar toolbar) {
        mdrawerlayout=drawerLayout;
        drawer_container=getActivity().findViewById(drawer_fragment);
        mdrawertoggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void OnDrawerOpened(View drawer_view){
                super.onDrawerOpened(drawer_view);
                getActivity().invalidateOptionsMenu();
            }
            public void OnDrawerClosed(View drawer_view){
                super.onDrawerClosed(drawer_view);
                getActivity().invalidateOptionsMenu();
            }
        };
        mdrawerlayout.setDrawerListener(mdrawertoggle);
        mdrawerlayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawertoggle.syncState();
            }
        });
    }

    public static void close() {
        mdrawerlayout.closeDrawers();
    }
}
