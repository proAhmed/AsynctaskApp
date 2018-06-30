package com.skook.skook.network;

import com.skook.skook.utilities.constant.UrlConstant;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class ApI {

    public Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // <- add this
                 .build();
    }
    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();

         httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request original = chain.request();
                original.header("");
                final HttpUrl originalHttpUrl = original.url();

                final HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("username", "demo")
                        .build();

                // Request customization: add request headers
                final Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }
}
