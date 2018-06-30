package com.skook.skook.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.adapter.MainOffersAdapter;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.model.Offers;
import com.skook.skook.model.OffersMain;
import com.skook.skook.model.SearchInput;
import com.skook.skook.network.OfferBasedOnRegionApi;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.SearchApi;
import com.skook.skook.offer.offer_details.OfferDetailsActivity;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity implements AdapterClick {
    protected TextView tvTitle;
    protected RecyclerView rvData;
    protected TextView txtNoOffer;
    ArrayList<Offers> arrayList;
    AdapterClick adapterClick;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search_result);
        adapterClick = this;
        initView();
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            try {
                if (intent.getExtras().getParcelable("search") != null) {

                    SearchInput searchInput = intent.getExtras().getParcelable("search");
                    assert searchInput != null;
                     getBasedOnSearch(searchInput);
                } else {
                    int id = intent.getExtras().getInt("id");
                    tvTitle.setText(getResources().getString(R.string.skok));
                    getBasedOnRegion(id);
                }
            } catch (Exception e) {
                int id = intent.getExtras().getInt("id");
                getBasedOnRegion(id);
                tvTitle.setText(getResources().getString(R.string.skok));
            }
        }
    }

    private void getBasedOnSearch(SearchInput searchInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMain offersMain = (OffersMain) result;
                arrayList = offersMain.getData();
                rvData.setAdapter(new MainOffersAdapter(SearchResult.this, arrayList, adapterClick));
                progress.setVisibility(View.GONE);
                if (arrayList == null || arrayList.size() == 0) {
                    txtNoOffer.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                txtNoOffer.setVisibility(View.VISIBLE);
                rvData.setVisibility(View.INVISIBLE);
                return false;
            }
        };
        new SearchApi(this, onLoadCompleteListener).execute(searchInput);

    }

    private void getBasedOnRegion(int searchInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMain offersMain = (OffersMain) result;
                arrayList = offersMain.getData();
                rvData.setAdapter(new MainOffersAdapter(SearchResult.this, arrayList, adapterClick));
                progress.setVisibility(View.GONE);
                if (arrayList == null || arrayList.size() == 0) {
                    txtNoOffer.setVisibility(View.VISIBLE);
                    rvData.setVisibility(View.INVISIBLE);
                }
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                txtNoOffer.setVisibility(View.VISIBLE);
                rvData.setVisibility(View.INVISIBLE);   return false;
            }
        };
        new OfferBasedOnRegionApi(this, onLoadCompleteListener).execute(searchInput);

    }

    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(this, OfferDetailsActivity.class);
        intent.putExtra("id", arrayList.get(pos).getOffer_id());
        startActivity(intent);
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        rvData = (RecyclerView) findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        progress = (ProgressBar) findViewById(R.id.progress);
        txtNoOffer = (TextView) findViewById(R.id.txtNoOffer);
    }
}
