package com.skook.skook.start;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.skook.skook.R;
import com.skook.skook.adapter.DrawerAdapters;
import com.skook.skook.favorite.FavoriteFragment;
import com.skook.skook.info.about_us.AboutUs;
import com.skook.skook.info.contact_us.ContactUs;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.logged_actions.add_offer.AddAdsFragment;
import com.skook.skook.logged_actions.my_offers.MyOffers;
import com.skook.skook.login_system.MainChooseLogin;
import com.skook.skook.login_system.SettingActivity;
import com.skook.skook.region.RegionFragment;
import com.skook.skook.utilities.DataInput;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterClick, ConnectivityReceiver.ConnectivityReceiverListener {

    ImageView imgToggle, imageView, imgLogout;
    StoreData storeData;
    boolean isLogin;
    ListView lstNav;
    DrawerLayout drawer;
    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgToggle = toolbar.findViewById(R.id.imgToggole);
        imgLogout = toolbar.findViewById(R.id.imgLogout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        storeData = new StoreData(this);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


         isLogin = !storeData.getToken().isEmpty();
        if (isLogin) {
            imgLogout.setVisibility(View.VISIBLE);
            imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    logout();
                }
            });
        }else {
            imgLogout.setVisibility(View.GONE);

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationContentDescription(null);
//        toolbar.setNavigationIcon(0);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_left);

        lstNav = navigationView.findViewById(R.id.lstNav);
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.nav_header_navigation, lstNav, false);
        header.setMinimumHeight(40);
        lstNav.addHeaderView(header);
        imageView = header.findViewById(R.id.imageView);
        // imageView.setVisibility(View.GONE);
        //   Picasso.with(this).load(storeData.getPicture()).into(imageView);
        DataInput dataInput = new DataInput();
        lstNav.setAdapter(new DrawerAdapters(this, dataInput.arrayList(this), this));
        navigationView.setNavigationItemSelectedListener(this);
        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.END);
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.main, new HomeViewFragment(), "").commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (count >= 1) {
                super.onBackPressed();
            } else {
                // Show your root fragment  here
                Utility.showExsitDialog(NavigationActivity.this,
                        getResources().getString(R.string.exsit_app),
                        this);
            }
         }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Picasso.with(this).load(storeData.getPicture()).into(imageView);

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Picasso.with(this).load(storeData.getPicture()).into(imageView);

//
//        if (id == R.id.nav_all_ads) {
//            // Handle the camera action
//        } else if (id == R.id.nav_my_adds) {
//
//        } else if (id == R.id.nav_add_ads) {
//
//        } else if (id == R.id.nav_favorite) {
//            if(!isLogin){
//                Intent intent= new Intent(this, MainChooseLogin.class);
//                startActivity(intent);
//            }
//        } else if (id == R.id.nav_settings) {
//       if(!isLogin){
//          Intent intent= new Intent(this, MainChooseLogin.class);
//           startActivity(intent);
//       }
//
//        } else if (id == R.id.nav_about_us) {
//            getSupportFragmentManager().beginTransaction().add(R.id.main, new AboutUs()).commit();
//        } else if (id == R.id.nav_contact_us) {
//            getSupportFragmentManager().beginTransaction().add(R.id.main, new ContactUs()).commit();
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void openAllOffers() {
        getSupportFragmentManager().beginTransaction().add(R.id.main, new HomeViewFragment(), "").commit();
    }

    public void openRegion() {
        getSupportFragmentManager().beginTransaction().add(R.id.main, new RegionFragment(), "").commit();
    }

    @Override
    public void onClick(int pos) {
        Log.d("tttt2", storeData.getToken());
        switch (pos) {
            case 0:
                navigate(new HomeViewFragment());
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 1:
                if (!isLogin) {
                    isLogined();
                } else {
                    navigate(new MyOffers());
                }
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 2:
                if (!isLogin) {
                    isLogined();
                } else {
                    navigate(new AddAdsFragment());
                }
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 3:
                if (!isLogin) {
                    isLogined();
                } else {
                    navigate(new FavoriteFragment());
                }
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 4:
                if (!isLogin) {
                    isLogined();
                } else {
                    navigate(new SettingActivity());

                }
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 5:
                navigate(new AboutUs());
                drawer.closeDrawer(GravityCompat.END);
                break;
            case 6:
                navigate(new ContactUs());
                drawer.closeDrawer(GravityCompat.END);
                break;
        }

    }
//    public void openRegion(){
//        getSupportFragmentManager().beginTransaction().add(R.id.main,new RegionFragment(),"").commit();
//    }

    private void isLogined() {
        Intent intent = new Intent(this, MainChooseLogin.class);
        startActivity(intent);
    }

    private void navigate(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.main, fragment).addToBackStack("").commit();

    }
    ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

          connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);

        if (storeData.getPicture().contains("/storage")) {
            File imgFile = new File(storeData.getPicture());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        } else {
            if (!storeData.getPicture().isEmpty())
                Picasso.with(this).load(storeData.getPicture()).into(imageView);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connectivityReceiver!=null){
            unregisterReceiver(connectivityReceiver);
        }
    }

    private void logout() {
        setNotify("تم تسجيل الخروج بنجاح");

        finishAffinity();
        imgLogout.setVisibility(View.GONE);
        new StoreData(this).clearData();
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);

    }
    public void login() {
        setNotify("تم تسجيل الدخول بنجاح");
        imgLogout.setVisibility(View.VISIBLE);

    }

    private void setNotify(String s){
        snackbar = Snackbar.make(coordinatorLayout, s,6000);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.show();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        //showSnack(isConnected);
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
      }
}
