package com.skook.skook.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skook.skook.R;
import com.skook.skook.adapter.FavoriteOffersAdapter;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.interfaces.DeleteClick;
import com.skook.skook.model.FavoriteResponse;
import com.skook.skook.model.Offers;
import com.skook.skook.model.OffersMain;
import com.skook.skook.network.GetFavOffer;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostAddOrDeleteFav;
import com.skook.skook.offer.offer_details.OfferDetailsActivity;
import com.skook.skook.utilities.StoreData;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/28/2017.
 */

public class FavoriteFragment extends Fragment implements AdapterClick, DeleteClick {


    protected RecyclerView rvMainOffers;
    protected ProgressBar progress;
    protected TextView txtNoOffer;
    AdapterClick adapterClick;
    DeleteClick deleteClick;
    ArrayList<Offers> arrayList;
    FavoriteOffersAdapter favoriteOffersAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_favorite, container, false);
        initView(view);
        try {
            load(new StoreData(getActivity()).getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void load(String token) {

        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMain offersMain = (OffersMain) result;
                progress.setVisibility(View.GONE);
                arrayList = offersMain.getData();
                favoriteOffersAdapter = new FavoriteOffersAdapter(getActivity(), arrayList, adapterClick, deleteClick);
                rvMainOffers.setAdapter(favoriteOffersAdapter);
                if (arrayList == null || arrayList.size() == 0) {
                    txtNoOffer.setVisibility(View.VISIBLE);
                    rvMainOffers.setVisibility(View.GONE);
                }
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                txtNoOffer.setVisibility(View.VISIBLE);
                rvMainOffers.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }
        };
        new GetFavOffer(getActivity(), onLoadCompleteListener).execute(token);
    }


    private void initView(View rootView) {
        rvMainOffers = rootView.findViewById(R.id.rvMyFavorite);
        rvMainOffers.setHasFixedSize(true);
        rvMainOffers.setItemViewCacheSize(20);
        //  rvMainOffers.setDrawingCacheEnabled(true);
        //    rvMainOffers.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        rvMainOffers.setLayoutManager(linearLayoutManager);
        adapterClick = this;
        deleteClick = this;
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        txtNoOffer = (TextView) rootView.findViewById(R.id.txtNoOffer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList = new ArrayList<>();
                load(new StoreData(getActivity()).getToken());
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
        intent.putExtra("id", arrayList.get(pos).getOffer_id());
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int pos) {
        deleteFromFav(arrayList.get(pos).getOffer_id(), pos);
    }

    private void deleteFromFav(int offerId, final int pos) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progress.setVisibility(View.GONE);

                FavoriteResponse normalResponse = (FavoriteResponse) result;
                int integer = normalResponse.getFav();
                if (integer == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();
                    arrayList.remove(pos);
                    favoriteOffersAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                return false;
            }
        };
        new PostAddOrDeleteFav(getContext(), onLoadCompleteListener).execute(offerId);
    }

}
