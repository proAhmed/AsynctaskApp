package com.skook.skook.offer.main_offers;

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

import com.skook.skook.R;
import com.skook.skook.adapter.MainOffersAdapter;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.model.Offers;
import com.skook.skook.model.OffersMain;
import com.skook.skook.network.GetOffers;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.offer.offer_details.OfferDetailsActivity;
import com.skook.skook.utilities.EndlessRecyclerOnScrollListener;
import com.skook.skook.utilities.Utility;

import java.util.ArrayList;

/**
 * Created by user on 11/16/2017.
 */

public class MainOffersFragment extends Fragment implements AdapterClick {


    protected RecyclerView rvMainOffers;
     protected ProgressBar progressBar;
    protected ProgressBar progressBarLoadMore;
    AdapterClick adapterClick;
    ArrayList<Offers> arrayList;
    int num = 0;
    int all;
    MainOffersAdapter mainOffersAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_main, container, false);

        initView(view);
        try {
            if (Utility.isNetworkConnected(getContext())) {

                load();
            }else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        getActivity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void load() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMain offersMain = (OffersMain) result;
                arrayList = offersMain.getData();
                all = offersMain.getAll();
                progressBar.setVisibility(View.GONE);
                mainOffersAdapter = new MainOffersAdapter(getActivity(), arrayList, adapterClick);
                rvMainOffers.setAdapter(mainOffersAdapter);
                if(mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progressBar.setVisibility(View.GONE);
                if(mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }
        };
        new GetOffers(getActivity(), onLoadCompleteListener).execute(""+num);
    }

    private void initView(View rootView) {
        rvMainOffers = rootView.findViewById(R.id.rvMainOffers);
        rvMainOffers.setHasFixedSize(true);
        rvMainOffers.setItemViewCacheSize(20);
      //  rvMainOffers.setDrawingCacheEnabled(true);
    //    rvMainOffers.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBarLoadMore = (ProgressBar) rootView.findViewById(R.id.progressBarLoadMore);
        rvMainOffers.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList= new ArrayList<>();
                load();

            }
        });
        rvMainOffers.setOnScrollListener(new EndlessRecyclerOnScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do somthing...
                progressBarLoadMore.setVisibility(View.VISIBLE);
                loadMoreData();

            }

        });
        adapterClick = this;

    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
        intent.putExtra("id", arrayList.get(pos).getOffer_id());
        startActivity(intent);

    }

    private void loadMoreData() {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request
         if (num < all) {
            num = num + 20;
            addMoreData();
        }
    }

    private void addMoreData() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMain offersMain = (OffersMain) result;
                ArrayList<Offers> arrayLists = offersMain.getData();
                all = offersMain.getAll();
                arrayList.addAll(arrayLists);
                // rvMainOffers.setAdapter(new MainOffersAdapter(getActivity(),arrayList,adapterClick));
                mainOffersAdapter.notifyDataSetChanged();
                progressBarLoadMore.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onFailure() {
                progressBarLoadMore.setVisibility(View.GONE);
                return false;
            }
        };
        new GetOffers(getActivity(), onLoadCompleteListener).execute(""+ num);
    }
}
