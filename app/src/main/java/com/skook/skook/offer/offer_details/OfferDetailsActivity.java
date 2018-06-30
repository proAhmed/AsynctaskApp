package com.skook.skook.offer.offer_details;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skook.skook.R;
import com.skook.skook.model.FavoriteResponse;
import com.skook.skook.model.OfferData;
import com.skook.skook.model.OfferMainData;
import com.skook.skook.network.GetSingleOffer;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostAddOrDeleteFav;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

/**
 * Created by user on 11/16/2017.
 */

public class OfferDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    protected ImageView imageView2;
    protected ImageView imageView7;
    protected Button btnContactDetails;
    private GoogleMap mMap;
    protected TextView txtOfferType;
    protected RelativeLayout relativeLayout;
    protected TextView txtTitle;
    protected TextView txtRegion;
    protected TextView txtCity;
    protected ImageView imgLike;
    protected ImageView imgShare;
    protected TextView txtTime;
    protected TextView txtDate;
    protected TextView txtArea;
    protected TextView txtMinCost;
    protected TextView txtMaxCost;
    protected TextView txtDetails;
    ArrayList<String> imageArrayList;
    OfferData offer;
    private SliderLayout mDemoSlider;
    SupportMapFragment mapFragment;
    RelativeLayout map;
    StoreData storeData;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Utility utility;
    TextView txtContactPhone;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.offer_details_activity);
        initView();
        imageArrayList = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt("id") != 0) {
                int offerId = getIntent().getExtras().getInt("id");
                if (Utility.isNetworkConnected(this)) {

                    loadData(offerId);
            }else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        this);
            }
             }
        }
    }


    private void loadData(final int offerId) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OfferMainData offerMain = (OfferMainData) result;
                offer = offerMain.getData();
                 txtTitle.setText(offer.getOfferTitle());
                txtRegion.setText(offer.getRegionTitle());
                txtCity.setText(offer.getCityTitle());
                txtDate.setText(offer.getOfferDate());
                txtArea.setText(offer.getDistance());
                txtDetails.setText(offer.getDetails());
                txtOfferType.setText(offer.getOfferType());
                txtMinCost.setText(offer.getPrice());
                txtMaxCost.setText(offer.getEdge());
                imageArrayList.addAll(offerMain.getImages());
                LatLng sydney = new LatLng(Double.parseDouble(offer.getLatitude())
                        , Double.parseDouble(offer.getLongitude()));
                if (Double.parseDouble(offer.getLongitude()) == 0 ||
                        Double.parseDouble(offer.getLatitude()) == 0) {
                    map.setVisibility(View.GONE);
                } else {
                    map.setVisibility(View.VISIBLE);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(offer.getTitle()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 7.0f));
                    mMap.getUiSettings().setMapToolbarEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
                for (String name : imageArrayList) {
                    TextSliderView textSliderView = new TextSliderView(OfferDetailsActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .image(name)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(OfferDetailsActivity.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider.addSlider(textSliderView);
                }
                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider.setDuration(4000);
                mDemoSlider.addOnPageChangeListener(OfferDetailsActivity.this);
                mMap.setMinZoomPreference(5);

                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetSingleOffer(this, onLoadCompleteListener).execute(offerId);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgLike) {
            if (!storeData.getToken().isEmpty()) {
                imgLike.setEnabled(false);
                addToFav(Integer.parseInt(offer.getOfferId()));
            } else {
                Toast.makeText(this, getResources().getString(R.string.need_login), Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.imgShare) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    offer.getUrl());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (view.getId() == R.id.btnContactDetails) {
            if(offer!=null)
            dialog();
        } else if (view.getId() == R.id.imgBack) {
            finish();
        }
    }

    private void initView() {
        storeData = new StoreData(this);
        utility = new Utility();
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtOfferType = (TextView) findViewById(R.id.txtOfferType);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtCity = (TextView) findViewById(R.id.txtCity);
        imgLike = (ImageView) findViewById(R.id.imgLike);
        imgLike.setOnClickListener(OfferDetailsActivity.this);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgShare.setOnClickListener(OfferDetailsActivity.this);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtMinCost = (TextView) findViewById(R.id.txtMinCost);
        txtMaxCost = (TextView) findViewById(R.id.txtMaxCost);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        map = (RelativeLayout) findViewById(R.id.map);
        map.setVisibility(View.GONE);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        btnContactDetails = (Button) findViewById(R.id.btnContactDetails);
        btnContactDetails.setOnClickListener(OfferDetailsActivity.this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera

    }

    private void addToFav(int offerId) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                imgLike.setEnabled(true);

                FavoriteResponse normalResponse = (FavoriteResponse) result;
                int integer = normalResponse.getFav();
                if (integer == 1) {
                    Toast.makeText(OfferDetailsActivity.this, getResources().getString(R.string.favorite_added), Toast.LENGTH_SHORT).show();
                    imgLike.setImageResource(R.drawable.un_like);
                } else {
                    Toast.makeText(OfferDetailsActivity.this, getResources().getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();
                    imgLike.setImageResource(R.drawable.like);

                }
                return false;
            }

            @Override
            public boolean onFailure() {
                imgLike.setEnabled(true);
                return false;
            }
        };
        new PostAddOrDeleteFav(this, onLoadCompleteListener).execute(offerId);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(this, ImageOfferDetails.class);
        intent.putExtra("image_path", slider.getUrl());
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void dialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(OfferDetailsActivity.this);
        dialog.setContentView(R.layout.dialog_contact);
        assert  dialog.getWindow() != null;
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        TextView txtContactTitle = (TextView) dialog.findViewById(R.id.txtContactTitle);
        TextView txtContactEmail = (TextView) dialog.findViewById(R.id.txtContactEmail);
        txtContactPhone = (TextView) dialog.findViewById(R.id.txtContactPhone);
        ImageView imgCancel = (ImageView) dialog.findViewById(R.id.imgCancel);
        assert imgCancel != null;
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        assert txtContactEmail != null;
        if (offer != null)
            txtContactEmail.setText(offer.getEmail());
        txtContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utility.sendEmail(OfferDetailsActivity.this, offer.getEmail());
            }
        });
        assert txtContactPhone != null;
        if (offer != null)
            txtContactPhone.setText(offer.getMobile());
        txtContactPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (!checkPermission()) {
                        requestPermission();
                    }
                } else {
                    utility.makeCall(OfferDetailsActivity.this, offer.getMobile());

                }
            }
        });
        assert txtContactTitle != null;
        if (offer != null)
            txtContactTitle.setText(offer.getTitle());

        dialog.show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, CALL_PHONE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted) {
                        if (!offer.getMobile().isEmpty())
                            utility.makeCall(OfferDetailsActivity.this, offer.getMobile());
                    }
                }


                break;
        }
    }
}
