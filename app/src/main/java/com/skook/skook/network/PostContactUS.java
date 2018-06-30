package com.skook.skook.network;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skook.skook.model.ContactUsInput;
import com.skook.skook.model.NormalResponse;
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
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/18/2017.
 */

public class PostContactUS extends AsyncTask<ContactUsInput, Void, NormalResponse> {

    private final static String URL = UrlConstant.BASE_URL+UrlConstant.CONTACT_URL;
    private OnLoadCompleteListener callback;
    private Context context;

    public PostContactUS(Context context, OnLoadCompleteListener cb) {
        callback = cb;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected NormalResponse doInBackground(ContactUsInput... params) {
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

//    public String makeRequest(ContactUsInput contactUsInput) throws Exception {
//
//
////        HttpClient client = new DefaultHttpClient();
////        HttpPost post = new HttpPost("http://www.mymi5.net/API/auth/login");
////        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
////        pairs.add(new BasicNameValuePair("username","abcd"));
////        pairs.add(new BasicNameValuePair("password","1234"));
////        post.setHeader("Content-type", "application/json");
////        post.setHeader("Accept", "application/json");
////        UrlEncodedFormEntity entitys = new UrlEncodedFormEntity(pairs,"UTF-8");
////        post.setEntity(entitys);
////        HttpResponse response = client.execute(post);
//
//
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(URL);
//        StringBuilder total = new StringBuilder();
//        JSONObject json = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
////        ArrayList<ItemJson> arrayList = new ArrayList<>();
////        arrayList.addAll(hashSet);
////        for (int i=0;i<arrayList.size();i++){
////            JSONObject itemJson = new JSONObject();
////            itemJson.put("Product",arrayList.get(i).getIdItem());
////            itemJson.put("Quantity",arrayList.get(i).getQuantityItem());
////            jsonArray.put(itemJson);
////        }
//        json.put(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA);
//        json.put(NetworkConstant.NAME,contactUsInput.getName());
//        json.put(NetworkConstant.EMAIL,contactUsInput.getEmail());
//        json.put(NetworkConstant.MESSAGE,contactUsInput.getMessage());
//
//        InputStreamEntity entity = null;
//        try {
//            InputStream is = new ByteArrayInputStream(json.toString().getBytes(
//                    "UTF-8"));
//
//            entity = new InputStreamEntity(is, is.available());
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//        httpPost.setEntity(entity);
//
//        httpPost.setHeader("Content-type", "application/json");
//        HttpResponse responses = httpclient.execute(httpPost);
//
//        if (responses.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//
//            InputStream instream = responses.getEntity().getContent();
//            BufferedReader r = new BufferedReader(new InputStreamReader(
//                    instream), 8000);
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line);
//            }
//            instream.close();
//        }
//        return total.toString();
//
//    }

    private String makeRequest(ContactUsInput contactUsInput) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("content-type","application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.NAME,contactUsInput.getName()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EMAIL,contactUsInput.getEmail()));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.MESSAGE,contactUsInput.getMessage()));
        StringBuilder total = new StringBuilder();

        httpPost.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK||response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
                ||response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {

            InputStream instream = response.getEntity().getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream), 8000);
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            instream.close();
        }else{
            total.append("error");
        }
        return total.toString();

    }
}
