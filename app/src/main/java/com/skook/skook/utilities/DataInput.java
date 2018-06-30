package com.skook.skook.utilities;

import android.app.Activity;

import com.skook.skook.R;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/26/2017.
 */

public class DataInput {

    public  ArrayList<String> arrayList(Activity activity){
        ArrayList<String> strings = new ArrayList<>();
        strings.add(activity.getResources().getString(R.string.all_ads));
        strings.add(activity.getResources().getString(R.string.my_adds));
        strings.add(activity.getResources().getString(R.string.add_ads));
        strings.add(activity.getResources().getString(R.string.favorite));
        strings.add(activity.getResources().getString(R.string.settings));
        strings.add(activity.getResources().getString(R.string.about_us));
        strings.add(activity.getResources().getString(R.string.contact_us));

        return strings;
    }
}
