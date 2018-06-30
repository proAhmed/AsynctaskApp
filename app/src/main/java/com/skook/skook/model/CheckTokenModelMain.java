package com.skook.skook.model;

/**
 * Created by ahmed radwan on 12/21/2017.
 */

public class CheckTokenModelMain {

    private int error;
    private String token;
    private String msg;
    private CheckTokenModel data;

    public CheckTokenModelMain() {
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CheckTokenModel getData() {
        return data;
    }

    public void setData(CheckTokenModel data) {
        this.data = data;
    }
}
