package com.skook.skook.start;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.adapter.ViewPagerAdapter;
import com.skook.skook.map.MapsFragment;
import com.skook.skook.offer.main_offers.MainOffersFragment;
import com.skook.skook.region.RegionFragment;
import com.skook.skook.search.SearchFragment;
import com.skook.skook.utilities.Utility;


public class HomeViewFragment extends Fragment   {


    protected View rootView;
    protected TabLayout tabsTL;
    protected ViewPager swipeVP;
     //
    private String carId;
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_view_fragment, container, false);
        initView(rootView);
        return rootView;
    }


    private void setupTabIcons() {
        @SuppressLint("InflateParams") TextView tabOne = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tabbed_view_layout, null);
        tabOne.setText(getResources().getString(R.string.map));
        tabsTL.getTabAt(3).setCustomView(tabOne);

        @SuppressLint("InflateParams") TextView tabTwo = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tabbed_view_layout, null);
        tabTwo.setText(getResources().getString(R.string.regions));
        tabsTL.getTabAt(2).setCustomView(tabTwo);

        @SuppressLint("InflateParams") TextView tabThree = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tabbed_view_layout, null);
        tabThree.setText(getResources().getString(R.string.search));
        tabsTL.getTabAt(1).setCustomView(tabThree);

        @SuppressLint("InflateParams") TextView tabFour = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tabbed_view_layout, null);
        tabFour.setText(getResources().getString(R.string.all));
        tabsTL.getTabAt(0).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {

          ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MainOffersFragment(),getResources().getString(R.string.all));
        adapter.addFragment(new SearchFragment(),getResources().getString(R.string.search));
        adapter.addFragment(new RegionFragment(),getResources().getString(R.string.regions));
        adapter.addFragment(new MapsFragment(),getResources().getString(R.string.map));
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initView(View rootView) {
        // To be moved when implementing MVP
        //
         //
        swipeVP = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabsTL = (TabLayout) rootView.findViewById(R.id.tabs);
        if (Utility.isNetworkConnected(getContext())) {
            setupViewPager(swipeVP);

            tabsTL.setupWithViewPager(swipeVP);
            setupTabIcons();
        }else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.no_internet),
                    getActivity());
        }


      }



}
