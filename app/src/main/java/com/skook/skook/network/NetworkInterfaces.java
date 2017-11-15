package com.skook.skook.network;


import com.skook.skook.model.CityMain;
import com.skook.skook.model.NormalResponse;
import com.skook.skook.model.RegionMain;
import com.skook.skook.model.User;
import com.skook.skook.utilities.constant.UrlConstant;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public interface NetworkInterfaces {

    interface LoginApi{
        @POST()
        Observable<User> loginAndRegister(@Url String url, @Body HashMap<String, String> user);
    }


    interface EditAccountApi{
        @POST(UrlConstant.EDIT_ACCOUNT_URL)
        Observable<User> editAccountAndForgetPass(@Url String url, @Body HashMap<String, String> user);
    }

    interface Region{
        @GET(UrlConstant.REGION_URL)
        Observable<RegionMain> allRegion();
    }
    interface AllCities{
        @GET(UrlConstant.CITY_ALL_URL)
        Observable<CityMain> allCities();
    }
    interface SingleCity{
        @GET(UrlConstant.CITY_BY_REGION_ID)
        Observable<CityMain> allRegion(@Path("id") int id);
    }

    interface GetOffers{
        @POST(UrlConstant.OFFERS_URL)
        Observable<NormalResponse> getOffers(@Body HashMap<String, String> user);
    }

    interface MainPost{
        @POST()
        Observable<NormalResponse> getOffers(@Url String url ,@Body HashMap<String, String> user);
    }
}
