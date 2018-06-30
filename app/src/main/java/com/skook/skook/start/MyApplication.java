package com.skook.skook.start;

import android.app.Application;

import com.skook.skook.utilities.TypefaceUtil;

/**
 * Created by ahmed radwan on 12/20/2017.
 */

public class MyApplication  extends Application {

    private static MyApplication mInstance;

        @Override
        public void onCreate() {
            super.onCreate();
            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/arabicfont.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
