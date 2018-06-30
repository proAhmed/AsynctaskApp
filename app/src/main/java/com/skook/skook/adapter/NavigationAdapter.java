package com.skook.skook.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.interfaces.AdapterClick;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahmed shaban on 8/2/2017.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.DataObjectHolder> {

    private List<String> stringList;
    public Context context;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    AdapterClick adapterClick;

    static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        LinearLayout lin;

        DataObjectHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            lin = itemView.findViewById(R.id.lin);
        }
    }

    public NavigationAdapter(Activity context, ArrayList<String> stringList, AdapterClick adapterClick) {
        this.stringList = stringList;
        this.context = context;
        this.adapterClick = adapterClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_items, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.tvTitle.setText(stringList.get(position));

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClick.onClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}



