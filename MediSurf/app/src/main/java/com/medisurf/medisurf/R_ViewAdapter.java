package com.medisurf.medisurf;

/**
 * Created by Anshul Goyal on 05-11-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by A on 2/5/2016.
 */
public class R_ViewAdapter extends RecyclerView.Adapter<R_ViewAdapter.R_ViewHolder> {

    private LayoutInflater inflater;
    List<List_item> data = Collections.emptyList();
    private Context item_context;

    public R_ViewAdapter(Context context, List<List_item> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        item_context = context;
    }

    @Override
    public R_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        R_ViewHolder holder = new R_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(R_ViewHolder holder, int position) {
        List_item current = data.get(position);
        holder.title.setText(current.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class R_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        public R_ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.list_item_title);
        }

        @Override
        public void onClick(View v) {


                Drawer.close();
                Intent I = null;
                String type = title.getText().toString().toLowerCase();

                //System.out.println("ANSHUL GOYAL" + type);
                if (type.equals("* optimize bill")) {
                    I = new Intent(item_context, OptimizeBill.class);
                } else if (type.equals("* get alternatives")) {
                    I = new Intent(item_context, getAlternatives.class);
                } else if (type.equals("* get brands")) {
                    I = new Intent(item_context, GetBrands.class);
                } else if (type.equals("* get generic name")) {
                    I = new Intent(item_context, getSalt.class);
                }
                else if (type.equals("* about medisurf")) {
                    I = new Intent(item_context, saltDescription.class);
                }


                item_context.startActivity(I);



        }
    }
}