package com.skook.skook.region;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skook.skook.R;
import com.skook.skook.adapter.RegionAdapter;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.model.Region;
import com.skook.skook.network.GetRegion;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.search.SearchResult;
import com.skook.skook.utilities.Utility;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class RegionFragment extends Fragment implements AdapterClick {
    protected RecyclerView rvRegions;
    AdapterClick adapterClick;
    ArrayList<Region> regionArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regions_fragment, container, false);
        initView(view);
        try {
            if (Utility.isNetworkConnected(getContext())) {

                load();
            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        getActivity());
            }
        } catch (Exception e) {

        }
        return view;
    }

    private void initView(View rootView) {
        rvRegions = rootView.findViewById(R.id.rvRegions);
        rvRegions.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterClick = this;
    }

    private void load() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                regionArrayList = (ArrayList<Region>) result;
                rvRegions.setAdapter(new RegionAdapter(getActivity(), regionArrayList, adapterClick));
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetRegion(getActivity(), onLoadCompleteListener).execute();
    }

    @Override
    public void onClick(int pos) {

        Intent intent = new Intent(getActivity(), SearchResult.class);
        intent.putExtra("id", regionArrayList.get(pos).getRegion_id());
        startActivity(intent);

    }
}
