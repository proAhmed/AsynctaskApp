package com.skook.skook.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skook.skook.model.CheckTokenModelMain;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.constant.NetworkConstant;
import com.skook.skook.utilities.constant.UrlConstant;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/18/2017.
 */

public class CheckTokenApi extends AsyncTask<String, Void, CheckTokenModelMain> {

    private final static String URL = UrlConstant.BASE_URL + UrlConstant.CHECK_TOKEN_API;
    private OnLoadCompleteListener callback;
    private Context context;

    public CheckTokenApi(Context context, OnLoadCompleteListener cb) {
        callback = cb;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected CheckTokenModelMain doInBackground(String... params) {
        String responseJSON = null;
        CheckTokenModelMain obj = null;

        try {
            responseJSON = makeRequest();
            Log.d("hhhpppplll", responseJSON);

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
                obj = gson.fromJson(responseJSON, CheckTokenModelMain.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return obj;
    }

    @Override
    protected void onPostExecute(CheckTokenModelMain result) {

        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onFailure();
        }
    }


    private String makeRequest() throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        Log.d("iiiiitttttt", new StoreData(context).getUserId()+"   "+  new StoreData(context).getToken());
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TOKEN, new StoreData(context).getToken()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TYPE, NetworkConstant.ANDROID));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.USER_ID, new StoreData(context).getUserId()+""));
         StringBuilder total = new StringBuilder();

        //   httpPost.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
                || response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {

            InputStream instream = response.getEntity().getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream), 8000);
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            instream.close();
        } else {
            total.append("error");
        }
        return total.toString();

    }

}
