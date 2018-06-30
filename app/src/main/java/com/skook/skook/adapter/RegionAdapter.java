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
import com.skook.skook.model.Region;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahmed shaban on 8/2/2017.
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.DataObjectHolder> {

    private List<Region> regionList;
    public Context context;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    AdapterClick adapterClick;

    static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txtRegion;
        LinearLayout linRegion;

        DataObjectHolder(View itemView) {
            super(itemView);
            txtRegion = itemView.findViewById(R.id.txtRegion);
            linRegion = itemView.findViewById(R.id.linRegion);
        }
    }

    public RegionAdapter(Activity context, ArrayList<Region> regionList, AdapterClick adapterClick) {
        this.regionList = regionList;
        this.context = context;
        this.adapterClick = adapterClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.region_items, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.txtRegion.setText(regionList.get(position).getTitle());

        holder.linRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClick.onClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }
}



