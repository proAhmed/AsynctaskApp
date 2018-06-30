package com.skook.skook.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.model.Offers;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Ahmed shaban on 8/2/2017.
 */

public class MainOffersAdapters extends RecyclerView.Adapter<MainOffersAdapters.DataObjectHolder> {

    private final int TYPE_OFFERS = 0;
    private final int TYPE_LOAD = 1;

    private Context context;
    private List<Offers> offersList;
    private OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false, isMoreDataAvailable = true;
    private int count;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public MainOffersAdapters(Context context, List<Offers> offersList, int count) {
        this.context = context;
        this.offersList = offersList;
        this.count = count;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_main_items, parent, false);

        return new MainOffersAdapters.DataObjectHolder(view);

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)== TYPE_OFFERS){
            holder.bindData(offersList.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(count==0){
            return TYPE_OFFERS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    /* VIEW HOLDERS */

     class DataObjectHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtMainLocation,txtLocationDetails,txtPrice, txtArea;
        ImageView imgLocation;
        ConstraintLayout conOffer;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtMainLocation = itemView.findViewById(R.id.txtMainLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtArea = itemView.findViewById(R.id.txtArea);
            txtLocationDetails = itemView.findViewById(R.id.txtLocationDetails);
            imgLocation = itemView.findViewById(R.id.imgOffers);
            conOffer = itemView.findViewById(R.id.conOffer);
        }

        void bindData(Offers offers){
            txtTitle.setText(offers.getOffer_title());
            txtMainLocation.setText(offers.getRegion_title());
            txtPrice.setText(offers.getEdge()+"");
            txtArea.setText(offers.getDistance()+"");
            txtLocationDetails.setText(offers.getCity_title());
            Picasso.with(context).load(offers.getImage()).into(imgLocation);

         }
    }

    private  class LoadHolder extends RecyclerView.ViewHolder{
        LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}



