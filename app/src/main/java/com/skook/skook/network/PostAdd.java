package com.skook.skook.network;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skook.skook.model.AddOfferInput;
import com.skook.skook.model.NormalResponse;
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

public class PostAdd extends AsyncTask<AddOfferInput, Void, NormalResponse> {

    private final static String URL = UrlConstant.BASE_URL+UrlConstant.ADD_OFFER_URL;
    private OnLoadCompleteListener callback;
    private Context context;

    public PostAdd(Context context, OnLoadCompleteListener cb) {
        callback = cb;
        this.context = context;
     }



    @Override
    protected NormalResponse doInBackground(AddOfferInput... params) {
        String responseJSON = null;
        NormalResponse obj = null;

        try {
            responseJSON = makeRequest(params[0]);

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
                obj = gson.fromJson(responseJSON, NormalResponse.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return obj;
    }

    @Override
    protected void onPostExecute(NormalResponse result) {

        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onFailure();
        }
    }

    private String makeRequest(AddOfferInput addOfferInput) throws Exception {
        String[] hamadal = new String[0];
         HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.NAME, addOfferInput.getUserName()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.PHONE, addOfferInput.getPhone()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.USER_ID, new StoreData(context).getUserId()+""));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EMAIL, addOfferInput.getEmail()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TOKEN, new StoreData(context).getToken()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.REGION, addOfferInput.getRegion()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.CITY, addOfferInput.getCity()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.CAT,addOfferInput.getCategory()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EDGE, addOfferInput.getEdge()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.PRICE, addOfferInput.getPrice()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.HAY, addOfferInput.getDistrict()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.DISTANCE, addOfferInput.getDistance()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.DETAILS, addOfferInput.getDetails()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SHOW_TYPE, addOfferInput.getShowType()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.LONGITUDE, addOfferInput.getUserLong()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.LATITUDE, addOfferInput.getUserLat()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.LATITUDE, addOfferInput.getUserLat()));

        if(addOfferInput.getImage()!=null&&addOfferInput.getImage().size()>0) {
            for(int i=0;i<addOfferInput.getImage().size();i++){
                nameValuePairs.add(new BasicNameValuePair(NetworkConstant.IMAGES, addOfferInput.getImage().get(i)));

            }
        }
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
