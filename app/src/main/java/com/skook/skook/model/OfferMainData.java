package com.skook.skook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferMainData {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private OfferData data;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("thumbs")
    @Expose
    private List<ImageThumb> thumbs = null;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OfferData getData() {
        return data;
    }

    public void setData(OfferData data) {
        this.data = data;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ImageThumb> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<ImageThumb> thumbs) {
        this.thumbs = thumbs;
    }
}
