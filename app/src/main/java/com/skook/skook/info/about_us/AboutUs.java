package com.skook.skook.info.about_us;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.model.AboutUsModel;
import com.skook.skook.network.GetAboutUS;
import com.skook.skook.network.OnLoadCompleteListener;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class AboutUs extends Fragment implements Html.ImageGetter{

    TextView txtAboutUS;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment, container, false);
        declare(view);
        loadData();
        return view;
    }

    private void declare(View view){
        txtAboutUS = view.findViewById(R.id.txtAboutUS);
    }
    private void loadData(){
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {

                AboutUsModel aboutUsModel = (AboutUsModel) result;
                String text =aboutUsModel.getData();
                Spanned marked_up = Html.fromHtml(text);
//                txtAboutUS.setText(marked_up.toString(), TextView.BufferType.SPANNABLE);
//             //   txtAboutUS.setText(Html.fromHtml(text));
                Spanned spanned = Html.fromHtml(text,AboutUs.this, null);
                txtAboutUS.setText(spanned);

                 return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
         new GetAboutUS(getActivity(),onLoadCompleteListener).execute();
    }

    @Override
    public Drawable getDrawable(String source) {
        return null;
    }
}
