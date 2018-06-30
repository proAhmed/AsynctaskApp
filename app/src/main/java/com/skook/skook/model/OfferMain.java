package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class OfferMain {

    private int code;
    private String message;
    private  Offer data;

    public OfferMain() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public  Offer getData() {
        return data;
    }

    public void setData( Offer data) {
        this.data = data;
    }
}
