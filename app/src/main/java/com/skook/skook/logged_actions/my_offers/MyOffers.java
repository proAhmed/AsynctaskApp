package com.skook.skook.logged_actions.my_offers;

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
import com.skook.skook.logged_actions.edit_offer.EditMyAdsFragment;
import com.skook.skook.model.NormalResponse;
import com.skook.skook.model.Offers;
import com.skook.skook.model.OffersMain;
import com.skook.skook.network.DeleteUserOffer;
import com.skook.skook.network.GetMyAdd;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/30/2017.
 */

public class MyOffers extends Fragment implements AdapterClick, DeleteClick {

    protected RecyclerView rvMyOffers;
    protected TextView txtNoOffer;
    ArrayList<Offers> offersArrayList;
    AdapterClick adapterClick;
    DeleteClick deleteClick;
    FavoriteOffersAdapter favoriteOffersAdapter;
    ProgressBar progress;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_offers, container, false);
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

    private void initView(View rootView) {
        rvMyOffers = rootView.findViewById(R.id.rvMyOffers);
        progress = rootView.findViewById(R.id.progress);
        rvMyOffers.setHasFixedSize(true);
        rvMyOffers.setItemViewCacheSize(20);
        //  rvMainOffers.setDrawingCacheEnabled(true);
        //    rvMainOffers.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        rvMyOffers.setLayoutManager(linearLayoutManager);
        adapterClick = this;
        deleteClick = this;
        txtNoOffer = rootView.findViewById(R.id.txtNoOffer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offersArrayList = new ArrayList<>();
                load();
            }
        });
    }

    private void load() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progress.setVisibility(View.GONE);
                OffersMain offersMain = (OffersMain) result;
                if (offersMain.getCode() == 0) {
                    rvMyOffers.setVisibility(View.GONE);
                    txtNoOffer.setVisibility(View.VISIBLE);
                }
                offersArrayList = offersMain.getData();
                favoriteOffersAdapter = new FavoriteOffersAdapter(getActivity(), offersArrayList, adapterClick, deleteClick);
                rvMyOffers.setAdapter(favoriteOffersAdapter);
                if (offersArrayList == null || offersArrayList.size() == 0) {
                    rvMyOffers.setVisibility(View.GONE);
                    txtNoOffer.setVisibility(View.VISIBLE);
                }
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                rvMyOffers.setVisibility(View.GONE);
                txtNoOffer.setVisibility(View.VISIBLE);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }
        };
        new GetMyAdd(getActivity(), onLoadCompleteListener).execute(new StoreData(getActivity()).getToken());
    }

    @Override
    public void onClick(int pos) {
        int id = offersArrayList.get(pos).getOffer_id();

        Fragment fragment = new EditMyAdsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main, fragment, "").addToBackStack("").commit();
    }


    @Override
    public void onDeleteClick(int pos) {
        progress.setVisibility(View.VISIBLE);
        txtNoOffer.setEnabled(false);
        deleteOffer(offersArrayList.get(pos).getOffer_id(), pos);
    }

    private void deleteOffer(int offerId, final int pos) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                txtNoOffer.setEnabled(true);

                NormalResponse normalResponse = (NormalResponse) result;
                String integer = normalResponse.getMessage();
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), integer, Toast.LENGTH_SHORT).show();
                if (normalResponse.getCode() == 1) {
                    offersArrayList.remove(pos);
                    favoriteOffersAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                txtNoOffer.setEnabled(true);
                return false;
            }
        };
        new DeleteUserOffer(getContext(), onLoadCompleteListener).execute(offerId);
    }
}
