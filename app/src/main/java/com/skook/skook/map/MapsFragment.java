package com.skook.skook.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skook.skook.R;
import com.skook.skook.model.OffersMap;
import com.skook.skook.model.OffersMapMain;
import com.skook.skook.network.OfferOnMapApi;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.offer.offer_details.OfferDetailsActivity;
import com.skook.skook.utilities.MapStateListener;
import com.skook.skook.utilities.TouchableMapFragment;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements

        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    protected View rootView;
    protected RelativeLayout map;
    private GoogleApiClient googleApiClient;
    android.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private String full_address;
    private Marker TempMarker;
    GoogleMap mGoogleMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    double lat;
    double lng;
    LatLng latLng;
    TouchableMapFragment mapFragment;
    ArrayList<Marker> markerArrayList;
    ArrayList<OffersMap> offersMapArrayList;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_maps, container, false);
        mapFragment = (TouchableMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        progressBar = rootView.findViewById(R.id.progressBar);
        mapFragment.getMapAsync(this);
        markerArrayList = new ArrayList<>();

        return rootView;
    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                                Criteria criteria = new Criteria();
                                String provider = locationManager.getBestProvider(criteria, true);
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    return;
                                }
                                  location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                                 if (location != null) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    LatLng latLng = new LatLng(latitude, longitude);

                                    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 9);
                                    mGoogleMap.animateCamera(yourLocation);

                                }
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (googleApiClient != null) {
            try {
               //    googleApiClient.disconnect();
                   LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            } catch (Exception e) {

            }
        }
    }

    LocationManager locationManager;
    Location location;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            mGoogleMap = googleMap;
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            MapActions();
            //Initialize Google Play Services
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Location Permission already granted
                    //buildGoogleApiClient();
                    buildGoogleApiClient();

                    mGoogleMap.setMyLocationEnabled(true);
                    //  loadData(googleMap.latitude+"",latLngs.longitude+"");
                    locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    location = locationManager.getLastKnownLocation(provider);
                } else {
                    //Request Location Permission
                    checkLocationPermission();
                }
            } else {
                //buildGoogleApiClient();
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                location = locationManager.getLastKnownLocation(provider);
            }


            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);

                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 9);
                mGoogleMap.animateCamera(yourLocation);

            }
        }catch (Exception e){

        }
    }


    @Override
    public void onConnected(Bundle bundle) {

        try{
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (userCurrentLocation != null) {
                MarkerOptions currentUserLocation = new MarkerOptions();
                LatLng currentUserLatLang = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
                currentUserLocation.position(currentUserLatLang).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                lat = userCurrentLocation.getLatitude();
                lng = userCurrentLocation.getLongitude();
                loadData(lat + "", lng + "");

                //  mGoogleMap.addMarker(currentUserLocation);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 9));
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }catch (Exception e){

        }
        //   MapActions();
        //setMapClick();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    private void MapActions() {

        new MapStateListener(mGoogleMap, mapFragment, getActivity()) {
            @Override
            public void onMapTouched() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMapReleased() {
                LatLng point = mGoogleMap.getCameraPosition().target;
                loadData(point.latitude + "", point.longitude + "");
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onMapUnsettled() {
                // Map unsettled
            }

            @Override
            public void onMapSettled() {
                // Map settled
            }
        };

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;


        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));

    }

    private void loadData(String lat, String lon) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                OffersMapMain offersMapMain = (OffersMapMain) result;
                offersMapArrayList = offersMapMain.getData();
                if (offersMapArrayList != null)
                    if (offersMapArrayList.size() > 0) {
                        for (int i = 0; i < offersMapArrayList.size(); i++) {
                            LatLng latLng = new LatLng(Double.parseDouble(offersMapArrayList.get(i).getLatitude()),
                                    Double.parseDouble(offersMapArrayList.get(i).getLongitude()));
                            final MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                    .title(offersMapArrayList.get(i).getOffer_title())
                                    .snippet(offersMapArrayList.get(i).getDetails());
                            Object o = offersMapArrayList.get(i).getOffer_id();
                            Marker marker = mGoogleMap.addMarker(markerOptions);
                            marker.setTag(o);

                            markerArrayList.add(marker);
                        }
                    }

                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        try {
                            int id = (int) marker.getTag();
                            Intent intent = new Intent(getActivity(), OfferDetailsActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);

                        } catch (Exception e) {


                        }
                     }
                });

                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new OfferOnMapApi(getActivity(), onLoadCompleteListener).execute(lat, lon);
    }

}
