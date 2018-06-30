package com.skook.skook.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.skook.skook.R;
import com.skook.skook.model.Categories;
import com.skook.skook.model.Cities;
import com.skook.skook.model.Region;
import com.skook.skook.model.SearchInput;
import com.skook.skook.network.GetAllCities;
import com.skook.skook.network.GetCategories;
import com.skook.skook.network.GetCities;
import com.skook.skook.network.GetRegion;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.utilities.Utility;

import java.util.ArrayList;

/**
 * Created by user on 11/16/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    protected TextView textView;
    protected Spinner spSearchType;
    protected Spinner spBuildingType;
    protected Spinner spArea;
    protected Spinner spGovernment;
    protected EditText etDistrict;
    protected Button btnSearch;
    protected EditText etSearchType;
    protected EditText etBuildingType;
    protected EditText etArea;
    protected EditText etGovernment;
    ArrayList<Categories> categoriesArrayList;
    ArrayList<Region> regionArrayList;
    ArrayList<Cities> citiesArrayList;
    int categoryId = 0, regionId = 0, cityId = 0;
    ArrayList<String> types;
    String typeValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);
        initView(view);
        try{
            if (Utility.isNetworkConnected(getContext())) {
                loadArea();
                loadCategories();
                loadType();
                loadAllCities();
            }else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        getActivity());
            }
        }catch (Exception e){
            Log.d("ppp",e.toString());

        }

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSearch) {
            if (Utility.isNetworkConnected(getContext())) {

                SearchInput searchInput = new SearchInput();
                searchInput.setCity(cityId);
                searchInput.setRegion(regionId);
                searchInput.setCategory(categoryId);
                searchInput.setType(typeValue);
                searchInput.setHay(etDistrict.getText().toString());

                Intent intent = new Intent(getActivity(), SearchResult.class);
                intent.putExtra("search", searchInput);
                startActivity(intent);
                intent = null;
            }else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.no_internet),
                        getActivity());
            }

        }
    }

    private void initView(View rootView) {
        textView = rootView.findViewById(R.id.textView);
        spSearchType = rootView.findViewById(R.id.spSearchType);
        spBuildingType = rootView.findViewById(R.id.spBuildingType);
        spArea = rootView.findViewById(R.id.spArea);
        spGovernment = rootView.findViewById(R.id.spGovernment);
        etDistrict = rootView.findViewById(R.id.etDistrict);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(SearchFragment.this);
        etSearchType = rootView.findViewById(R.id.etSearchType);
        etBuildingType = rootView.findViewById(R.id.etBuildingType);
        etArea = rootView.findViewById(R.id.etArea);
        etGovernment = rootView.findViewById(R.id.etGovernment);
    }

    private void loadCategories() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                try {
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
                                etBuildingType.setText("");
                            } else {
                                categoryId = 0;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            categoryId = 0;

                        }
                    });
                }catch (Exception e){

                }
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
                try {
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

                                citiesArrayList = new ArrayList<>();
                                citiesArrayList.clear();
                                loadCities(regionId);
                                etArea.setText("");
                            } else {
                                regionId = 0;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            regionId = 0;

                        }
                    });
                }catch (Exception e){

                }
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
        types.add(0, getResources().getString(R.string.search_type));
        types.add("بيع");
        types.add("ايجار");
        types.add("مطلوب");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchType.setAdapter(dataAdapter);

        spSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    typeValue = types.get(position);
                     etSearchType.setText("");
                }else {
                    typeValue ="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeValue ="";

            }
        });

    }

    private void loadCities(int id) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                try {
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
                                etGovernment.setText("");
                            } else {
                                cityId = 0;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            cityId = 0;
                        }
                    });
                }catch (Exception e){

                }
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetCities(getActivity(), onLoadCompleteListener, id).execute();
    }

    private void loadAllCities() {
        try {
            OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
                @Override
                public boolean onSuccess(Object result) {
                    try {
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
                                    etGovernment.setText("");
                                } else {
                                    cityId = 0;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                cityId = 0;
                            }
                        });
                    }catch (Exception e){

                    }
                    return false;
                }

                @Override
                public boolean onFailure() {
                    return false;
                }
            };
            new GetAllCities(getActivity(), onLoadCompleteListener).execute();
        }catch (Exception e){

        }
    }
}
