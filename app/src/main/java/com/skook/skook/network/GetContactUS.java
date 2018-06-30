package com.skook.skook.network;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skook.skook.model.ContactUsModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ahmed radwan on 11/19/2017.
 */

public class GetContactUS extends AsyncTask<String,Void,ContactUsModel> {

     private String finalUrl;
    private OnLoadCompleteListener callback;
    private Context context;
    String responseJSON;
    public GetContactUS(Context context, OnLoadCompleteListener cb) {
         callback = cb;
        this.context = context;
        finalUrl =   "http://skook.sa/api_contact_data";
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ContactUsModel doInBackground(String... params) {

        ContactUsModel obj = null;

        try {
            responseJSON = invokeJSONWS();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Gson gson = new Gson();
        if (responseJSON != null && responseJSON.length() > 1) {

            GsonBuilder gb = new GsonBuilder();
            gb.serializeNulls();
            gson = gb.create();
            try {
                obj = gson.fromJson(responseJSON, ContactUsModel.class);
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }
        return obj;
    }

    @Override
    protected void onPostExecute(ContactUsModel responseHome) {
        super.onPostExecute(responseHome);


        if (responseHome.getData()!=null) {
            callback.onSuccess(responseHome);
        } else {
            callback.onFailure();
        }
    }

    private String invokeJSONWS() throws IOException {
        HttpURLConnection httpConn = null;
        InputStream in = null;
        int response = -1;
        String responseJSON;
        URL url = new URL(finalUrl);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
            httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(20000);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);

            httpConn.connect();

            response = httpConn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            responseJSON = out.toString();

        } catch (Exception e) {
            throw new IOException("Error connecting");
        }finally {

            httpConn.disconnect();
        }
        return responseJSON;
    }
}
