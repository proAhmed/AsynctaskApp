package com.skook.skook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.interfaces.AdapterClick;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ahmed on 1/19/2016.
 */
public class DrawerAdapters extends BaseAdapter {

    private List<String> stringList;
    private Context context;
    TextView tvTitle, tvContent;
    AdapterClick adapterClick;

    public DrawerAdapters(Context context, ArrayList<String> stringList, AdapterClick adapterClick) {
        this.context = context;
        this.stringList = stringList;
        this.adapterClick = adapterClick;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.nav_items, parent, false);
        }

        tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(stringList.get(position));
        LinearLayout lin = convertView.findViewById(R.id.lin);
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClick.onClick(position);
            }
        });


        return convertView;
    }


//    @Override
//    public boolean isEnabled(int position) {
//
//        return true;
//    }
}
