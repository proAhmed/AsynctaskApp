
package com.skook.skook.model;

 import com.google.gson.annotations.SerializedName;

   public class ContactUsModel {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

}
