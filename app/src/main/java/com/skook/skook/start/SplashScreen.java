package com.skook.skook.start;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.skook.skook.R;
import com.skook.skook.model.CheckTokenModelMain;
import com.skook.skook.network.CheckTokenApi;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;

public class SplashScreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    StoreData storeData;
    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        storeData = new StoreData(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

          connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connectivityReceiver!=null){
            unregisterReceiver(connectivityReceiver);
        }
    }

    private void checkToken() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                CheckTokenModelMain normalResponse = (CheckTokenModelMain) result;
                Log.d("iiii",normalResponse.getMsg()+"  "+normalResponse.getError());
                if (normalResponse.getError() == 0) {
                    finish();
                    Intent intent = new Intent(SplashScreen.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                    Intent intent = new Intent(SplashScreen.this, NavigationActivity.class);
                    startActivity(intent);
                    storeData.setToken("");
                    storeData.clearData();
                }

//            finish();
//            new StoreData(this).setToken("");
                return false;
            }

            @Override
            public boolean onFailure() {
                finish();
                Intent intent = new Intent(SplashScreen.this, NavigationActivity.class);
                startActivity(intent);
                storeData.setToken("");
                storeData.clearData();
                return false;
            }

        };
        new CheckTokenApi(this, onLoadCompleteListener).execute();
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
             checkToken();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.no_internet),
                    this);
        }
    }
}