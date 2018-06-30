package com.skook.skook.network;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skook.skook.model.SignInModel;
import com.skook.skook.utilities.constant.NetworkConstant;
import com.skook.skook.utilities.constant.UrlConstant;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/18/2017.
 */

public class PostSignIn extends AsyncTask<String, Void, SignInModel> {

    private final static String URL = UrlConstant.BASE_URL + UrlConstant.LOGIN_URL;
    private OnLoadCompleteListener callback;
    private Context context;

    public PostSignIn(Context context, OnLoadCompleteListener cb) {
        callback = cb;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SignInModel doInBackground(String... params) {
        String responseJSON = null;
        SignInModel obj = null;

        try {
            responseJSON = makeRequest(params[0], params[1]);

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
                obj = gson.fromJson(responseJSON, SignInModel.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return obj;
    }

    @Override
    protected void onPostExecute(SignInModel result) {

        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onFailure();
        }
    }


    private String makeRequest(String email, String pass) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TYPE, NetworkConstant.ANDROID));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EMAIL, email));
        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.PASSWORD, pass));
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


    public String makeRequesst(String email, String pass) throws Exception {


//        HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost("http://www.mymi5.net/API/auth/login");
//        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        pairs.add(new BasicNameValuePair("username","abcd"));
//        pairs.add(new BasicNameValuePair("password","1234"));
//        post.setHeader("Content-type", "application/json");
//        post.setHeader("Accept", "application/json");
//        UrlEncodedFormEntity entitys = new UrlEncodedFormEntity(pairs,"UTF-8");
//        post.setEntity(entitys);
//        HttpResponse response = client.execute(post);


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        StringBuilder total = new StringBuilder();
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
//        ArrayList<ItemJson> arrayList = new ArrayList<>();
//        arrayList.addAll(hashSet);
//        for (int i=0;i<arrayList.size();i++){
//            JSONObject itemJson = new JSONObject();
//            itemJson.put("Product",arrayList.get(i).getIdItem());
//            itemJson.put("Quantity",arrayList.get(i).getQuantityItem());
//            jsonArray.put(itemJson);
//        }
        json.put(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA);
        json.put(NetworkConstant.TYPE, NetworkConstant.ANDROID);
        json.put(NetworkConstant.EMAIL, email);
        json.put(NetworkConstant.PASSWORD, pass);

        InputStreamEntity entity = null;
        try {
            InputStream is = new ByteArrayInputStream(json.toString().getBytes(
                    "UTF-8"));

            entity = new InputStreamEntity(is, is.available());

        } catch (IOException e) {

            e.printStackTrace();
        }

        httpPost.setEntity(entity);

        httpPost.setHeader("Content-type", "application/json");
        HttpResponse responses = httpclient.execute(httpPost);

        if (responses.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            InputStream instream = responses.getEntity().getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream), 8000);
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            instream.close();
        }
        return total.toString();

    }

//    private String makeRequest(String email,String pass) throws Exception {
//
//
////        OkHttpClient client = new OkHttpClient();
////
////        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
////        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"securecode\"\r\n\r\ntx2^%sAu]$9\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"region_id\"\r\n\r\n5\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
////        Request request = new Request.Builder()
////                .url("http://alskok.com/api_search.html")
////                .post(body)
////                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
////                .addHeader("cache-control", "no-cache")
////                .build();
////
////        Response response = client.newCall(request).execute();
//
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(URL);
//        httpPost.setHeader("content-type","application/x-www-form-urlencoded");
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//
//        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
//        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TYPE,NetworkConstant.ANDROID));
//        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EMAIL,email));
//        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.PASSWORD,pass));
//        StringBuilder total = new StringBuilder();
// ;
//
//        httpPost.setHeader(HTTP.CONTENT_TYPE,"multipart/form-data;charset=UTF-8");
//
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        HttpResponse response = httpclient.execute(httpPost);
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK||response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
//                ||response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
//
//            InputStream instream = response.getEntity().getContent();
//            BufferedReader r = new BufferedReader(new InputStreamReader(
//                    instream), 8000);
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line);
//            }
//            instream.close();
//        }else{
//            total.append("error");
//        }
//        return total.toString();
//
//    }

//    public static String makeRequest(String email, String password)
//            throws Exception {
//
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpost = new HttpPost(URL);
//        StringBuilder total = new StringBuilder();
//        JSONObject json = new JSONObject();
//
//        json.put(NetworkConstant.EMAIL, email);
//        json.put(NetworkConstant.PASSWORD, password);
//        json.put(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA);
//
////        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.SECURE_DATA_KEY, NetworkConstant.SECURE_DATA));
////        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.TYPE,NetworkConstant.ANDROID));
////        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.EMAIL,email));
////        nameValuePairs.add(new BasicNameValuePair(NetworkConstant.PASSWORD,pass));
//        InputStream is = new ByteArrayInputStream(json.toString().getBytes(
//                "UTF-8"));
//
//        InputStreamEntity entity = new InputStreamEntity(is, is.available());
//
//        httpost.setEntity(entity);
//        httpost.setHeader("Content-type", "application/json");
//        HttpResponse response = (HttpResponse) httpclient.execute(httpost);
//
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//
//            InputStream instream = response.getEntity().getContent();
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

}
