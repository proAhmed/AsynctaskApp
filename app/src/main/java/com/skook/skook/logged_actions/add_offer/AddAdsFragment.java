package com.skook.skook.logged_actions.add_offer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.skook.skook.adapter.AddOfferImageAdapter;
import com.skook.skook.interfaces.AdapterClick;
import com.skook.skook.model.AddOfferInput;
import com.skook.skook.model.Categories;
import com.skook.skook.model.Cities;
import com.skook.skook.model.NormalResponse;
import com.skook.skook.model.Region;
import com.skook.skook.network.GetCategories;
import com.skook.skook.network.GetCities;
import com.skook.skook.network.GetRegion;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostAdd;
import com.skook.skook.utilities.ScrollGoogleMap;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;
import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.activities.MultiCameraActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ahmed radwan on 11/22/2017.
 */

public class AddAdsFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, AdapterClick {

    protected TextView textView;
    protected EditText etName;
    protected EditText etPhone;
    protected EditText etEmail;
    protected Spinner spAdsType;
    protected Spinner spBuildingType;
    protected EditText etArea;
    protected Spinner spArea;
    protected Spinner spGovernment;
    protected EditText etDistrict;
    protected EditText etInitialPrice;
    protected EditText etMaxPrice;
    protected EditText etDetails;
    protected Button btnAddImages;
    protected RecyclerView recyclerView;
    protected ScrollGoogleMap maps;
    protected Button btnAddOffer;
    protected View rootView;
    protected EditText etAdsType;
    protected EditText etBuildingType;
    protected EditText etSpArea;
    protected EditText etGovernment;
    protected ProgressBar progress;
    private GoogleApiClient googleApiClient;
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    double lat;
    double lng;
    ArrayList<Image> imagesList;
    AddOfferImageAdapter addOfferImageAdapter;
    double userLat, userLong;
    StoreData storeData;
    ArrayList<Categories> categoriesArrayList;
    ArrayList<Region> regionArrayList;
    ArrayList<Cities> citiesArrayList;
    int categoryId = 0, regionId = 0, cityId = 0;
    ArrayList<String> types;
    String typeValue;
    Utility utility;
    ArrayList<String> base64List;
    ScrollView scroll;
    LinearLayout lin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_ads_fragment, container, false);

        initView(view);
        if (Utility.isNetworkConnected(getContext())) {

            loadArea();
            loadCategories();
            loadType();
        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.no_internet),
                    getActivity());
        }
        return view;
    }


    private void initiateMultiCapture() {
        Intent intent = new Intent(getActivity(), MultiCameraActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
//        params.setToolbarColor(selectedColor);
//        params.setActionButtonColor(selectedColor);
//        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);
    }

    private void initiateMultiPicker() {
        Intent intent = new Intent(getActivity(), GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setPickerLimit(10);
//        params.setToolbarColor(selectedColor);
//        params.setActionButtonColor(selectedColor);
//        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnAddImages) {
            initiateMultiPicker();
        } else if (view.getId() == R.id.btnAddOffer) {
            if (Utility.isNetworkConnected(getContext())) {
                checkParameter();

            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        getActivity());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
//            case Constants.TYPE_MULTI_CAPTURE:
//                handleResponseIntent(intent);
//                break;
            case Constants.TYPE_MULTI_PICKER:
                handleResponseIntent(intent);
                break;
        }
    }

    private void handleResponseIntent(Intent intent) {
        imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
        for (int i = 0; i < imagesList.size(); i++) {
            String s = imagesList.get(i).imagePath;
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            String bitmapImage = utility.getEncoded64ImageStringFromBitmap(bitmap);
            base64List.add(bitmapImage);

        }
        recyclerView.setHasFixedSize(true);
//        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getColumnCount(), GridLayoutManager.VERTICAL);
//        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        addOfferImageAdapter = new AddOfferImageAdapter(getActivity(), imagesList, this);
        // imageAdapter = new GalleryImagesAdapter(this, imagesList, getColumnCount(), new Params());
        recyclerView.setAdapter(addOfferImageAdapter);
    }

    private void initView(View rootView) {
        utility = new Utility();
        base64List = new ArrayList<>();
        storeData = new StoreData(getActivity());
        lin = rootView.findViewById(R.id.lin);
        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        scroll = rootView.findViewById(R.id.scroll);
        textView = rootView.findViewById(R.id.textView);
        etName = rootView.findViewById(R.id.etName);
        etPhone = rootView.findViewById(R.id.etPhone);
        etEmail = rootView.findViewById(R.id.etEmail);
        spAdsType = rootView.findViewById(R.id.spAdsType);
        spBuildingType = rootView.findViewById(R.id.spBuildingType);
        etArea = rootView.findViewById(R.id.etArea);
        spArea = rootView.findViewById(R.id.spArea);
        spGovernment = rootView.findViewById(R.id.spGovernment);
        etDistrict = rootView.findViewById(R.id.etDistrict);
        etInitialPrice = rootView.findViewById(R.id.etInitialPrice);
        etMaxPrice = rootView.findViewById(R.id.etMaxPrice);
        etDetails = rootView.findViewById(R.id.etDetails);
        btnAddImages = rootView.findViewById(R.id.btnAddImages);
        btnAddImages.setOnClickListener(AddAdsFragment.this);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        maps = (ScrollGoogleMap) getChildFragmentManager()
                .findFragmentById(R.id.maps);
        maps.getMapAsync(this);
        ((ScrollGoogleMap) getChildFragmentManager()
                .findFragmentById(R.id.maps))
                .setListener(new ScrollGoogleMap.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scroll.requestDisallowInterceptTouchEvent(true);
                    }
                });

        btnAddOffer = rootView.findViewById(R.id.btnAddOffer);
        btnAddOffer.setOnClickListener(AddAdsFragment.this);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        etAdsType = (EditText) rootView.findViewById(R.id.etAdsType);
        etBuildingType = (EditText) rootView.findViewById(R.id.etBuildingType);
        etSpArea = (EditText) rootView.findViewById(R.id.etSpArea);
        etGovernment = (EditText) rootView.findViewById(R.id.etGovernment);
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (userCurrentLocation != null) {
                MarkerOptions currentUserLocation = new MarkerOptions();
                LatLng currentUserLatLang = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
                currentUserLocation.position(currentUserLatLang).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                lat = userCurrentLocation.getLatitude();
                lng = userCurrentLocation.getLongitude();

                //  mGoogleMap.addMarker(currentUserLocation);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 7));
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.setMyLocationEnabled(true);
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
    public void onLocationChanged(Location location) {
        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        //   mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    Marker markers;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                //buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                //  loadData(googleMap.latitude+"",latLngs.longitude+"");
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            //buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            userLat = latitude;
            userLong = longitude;
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mGoogleMap.animateCamera(yourLocation);

        }

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mGoogleMap.clear();
                if (markers != null) {
                    markers.setPosition(point);
                } else {
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(point.latitude, point.longitude)).title("ads location");

                    mGoogleMap.addMarker(marker);
                    userLat = point.latitude;
                    userLong = point.longitude;

                }

                System.out.println(point.latitude + "---" + point.longitude);
            }
        });
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
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
    public void onClick(int pos) {
        dialog(pos);
    }

    private void dialog(final int pos) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_remove);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesList.remove(pos);
                addOfferImageAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void addOffer(AddOfferInput addOfferInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                NormalResponse normalResponse = (NormalResponse) result;
                Toast.makeText(getActivity(), normalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("pppp", result.toString());
                progress.setVisibility(View.GONE);
                btnAddOffer.setEnabled(true);

                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                btnAddOffer.setEnabled(true);
                return false;
            }
        };
        new PostAdd(getActivity(), onLoadCompleteListener).execute(addOfferInput);
    }

    private void checkParameter() {
        AddOfferInput addOfferInput = new AddOfferInput();

        if (!etName.getText().toString().isEmpty()) {

            addOfferInput.setUserName(etName.getText().toString());
            if (!typeValue.isEmpty()) {
                addOfferInput.setShowType("" + typeValue);
                if (categoryId != 0) {
                    addOfferInput.setCategory("" + categoryId);
                    if (!etArea.getText().toString().isEmpty()) {
                        addOfferInput.setDistance(etArea.getText().toString());
                        if (regionId != 0) {
                            addOfferInput.setRegion(regionId + "");
                            if (cityId != 0) {
                                addOfferInput.setCity(cityId + "");
                                if (!etDistrict.getText().toString().isEmpty()) {
                                    addOfferInput.setDistrict(etDistrict.getText().toString());
                                    if (!etInitialPrice.getText().toString().isEmpty()) {
                                        addOfferInput.setPrice(etInitialPrice.getText().toString());
                                        if (!etMaxPrice.getText().toString().isEmpty()) {
                                            addOfferInput.setEdge(etMaxPrice.getText().toString());
                                            if (!etDetails.getText().toString().isEmpty()) {
                                                addOfferInput.setDetails(etDetails.getText().toString());

                                                if (userLong != 0) {
                                                    addOfferInput.setUserLong(userLong + "");
                                                    if (userLat != 0) {
                                                        addOfferInput.setUserLat(userLat + "");
                                                        addOfferInput.setEmail(etEmail.getText().toString());
                                                        addOfferInput.setPhone(etPhone.getText().toString());
                                                        if (base64List.size() > 0) {
                                                            addOfferInput.setImage(base64List);
                                                            progress.setVisibility(View.VISIBLE);
                                                            btnAddOffer.setEnabled(false);
                                                            addOffer(addOfferInput);
                                                        } else {
                                                            progress.setVisibility(View.VISIBLE);
                                                            btnAddOffer.setEnabled(false);
                                                            addOffer(addOfferInput);
                                                        }
                                                    }
                                                }

                                            } else {
                                                etMaxPrice.setError(getResources().getString(R.string.insert_details));
                                            }
                                        } else {
                                            etMaxPrice.setError(getResources().getString(R.string.insert_edge));
                                        }
                                    } else {
                                        etInitialPrice.setError(getResources().getString(R.string.insert_price));
                                    }
                                } else {
                                    etDistrict.setError(getResources().getString(R.string.insert_district));
                                }


                            } else {
                                etGovernment.setError(getResources().getString(R.string.insert_value));
                            }

                        } else {
                            etSpArea.setError(getResources().getString(R.string.insert_value));
                        }

                    } else {
                        etArea.setError(getResources().getString(R.string.insert_area));
                    }

                } else {
                    etBuildingType.setError(getResources().getString(R.string.insert_value));
                }

            } else {
                etAdsType.setError(getResources().getString(R.string.insert_value));
            }
        } else {
            etName.setError(getResources().getString(R.string.insert_userName));
        }


        if (!etEmail.getText().toString().isEmpty()) {
            addOfferInput.setEmail(etEmail.getText().toString());
        }
        if (etPhone.getText().toString().isEmpty()) {
            addOfferInput.setEmail(storeData.getMobile());
        } else if (!etEmail.getText().toString().isEmpty()) {
            addOfferInput.setEmail(etPhone.getText().toString());
        }

    }


    private void loadCategories() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progress.setVisibility(View.GONE);
                categoriesArrayList = (ArrayList<Categories>) result;
                categoriesArrayList.add(0, new Categories(getResources().getString(R.string.building_type)));
                ArrayAdapter<Categories> dataAdapter = new ArrayAdapter<Categories>(getActivity(),
                        android.R.layout.simple_spinner_item, categoriesArrayList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBuildingType.setAdapter(dataAdapter);

                spBuildingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            categoryId = categoriesArrayList.get(position).getCategory_id();
//                            etBuildingType.setText("");
                        } else {
                            categoryId = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        categoryId = 0;

                    }
                });
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetCategories(getActivity(), onLoadCompleteListener).execute();
    }

    private void loadArea() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {

                regionArrayList = (ArrayList<Region>) result;
                regionArrayList.add(0, new Region(getResources().getString(R.string.area)));
                ArrayAdapter<Region> dataAdapter = new ArrayAdapter<Region>(getActivity(),
                        android.R.layout.simple_spinner_item, regionArrayList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spArea.setAdapter(dataAdapter);

                spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            regionId = regionArrayList.get(position).getRegion_id();
                            loadCities(regionId);
                        } else {
                            regionId = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        regionId = 0;

                    }
                });
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetRegion(getActivity(), onLoadCompleteListener).execute();
    }

    private void loadType() {
        types = new ArrayList<>();
        types.add(0, "نوع الاعلانات");
        types.add("بيع");
        types.add("ايجار");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdsType.setAdapter(dataAdapter);

        spAdsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    typeValue = types.get(position);
//                    etSearchType.setText("");
                } else {
                    typeValue = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeValue = "";

            }
        });

    }

    private void loadCities(int id) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                citiesArrayList = (ArrayList<Cities>) result;
                citiesArrayList.add(0, new Cities(getResources().getString(R.string.government)));
                ArrayAdapter<Cities> dataAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, citiesArrayList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spGovernment.setAdapter(dataAdapter);

                spGovernment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            cityId = citiesArrayList.get(position).getCity_id();
                        } else {
                            cityId = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        cityId = 0;
                    }
                });
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetCities(getActivity(), onLoadCompleteListener, id).execute();
    }


}
