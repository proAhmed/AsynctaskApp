package com.skook.skook.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.skook.skook.R;
import com.skook.skook.interfaces.AdapterClick;
import com.squareup.picasso.Picasso;
import com.vlk.multimager.utils.Image;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ahmed shaban on 8/2/2017.
 */

public class AddOfferImageAdapter extends RecyclerView.Adapter<AddOfferImageAdapter.DataObjectHolder> {

    private List<Image> offersList;
    private Context context;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private AdapterClick adapterClick;

    static class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgOffers;
        LinearLayout lin;

        DataObjectHolder(View itemView) {
            super(itemView);

            imgOffers = itemView.findViewById(R.id.img);
            lin = itemView.findViewById(R.id.lin);
        }
    }

    public AddOfferImageAdapter(Activity context, ArrayList<Image> offersList, AdapterClick adapterClick) {
        this.offersList = offersList;
        this.context = context;
        this.adapterClick = adapterClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_offers_image_items, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        if (offersList.get(position).uri != null) {
            Picasso.with(context)
                    .load(offersList.get(position).uri)
                    .into(holder.imgOffers);
        } else {

             Picasso.with(context).load(offersList.get(position).imagePath).into(holder.imgOffers);

        }
         holder.imgOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClick.onClick(position);
             }
        });


    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }
}



