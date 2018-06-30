package com.skook.skook.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skook.skook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            View view = (View) object;
            ImageView imgView = (ImageView) view.findViewById(R.id.image);
            BitmapDrawable bmpDrawable = (BitmapDrawable) imgView.getDrawable();
            if (bmpDrawable != null && bmpDrawable.getBitmap() != null) {
                // This is the important part
                bmpDrawable.getBitmap().recycle();
            }
            (container).removeView(view);
        }catch (Exception e){

        }
 }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.image_items, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.imgSlider);
       // myImage.setImageResource(images.get(position));
         Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                         //Here your log

                    }
                })
                .build();
        picasso.load(images.get(position))
                .fit()
                .into(myImage);




//        Picasso.with(context).load(images.get(position)).into(myImage, new Callback() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//
       view.addView(myImageLayout, 0);
     return myImageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}