package com.skook.skook.offer.offer_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.skook.skook.R;
import com.skook.skook.utilities.TouchImageView;
import com.squareup.picasso.Picasso;

public class ImageOfferDetails extends AppCompatActivity {

    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_offer_details);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TouchImageView touchImageView = (TouchImageView) findViewById(R.id.imgDetails);
        if (getIntent().getExtras() != null) {
            String imagePath = getIntent().getExtras().getString("image_path");
            Picasso.with(this).load(imagePath).into(touchImageView);
        }
    }
}
