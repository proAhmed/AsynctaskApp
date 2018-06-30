package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/27/2017.
 */

public class SignInModel {

    private int code;
    private String message;
    private String token;
    private int user_id;
    private String email;
    private String phone;
    private String username;
    private String image;

    public SignInModel() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserId(int user) {
        this.user_id = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
