package com.skook.skook.model;

/**
 * Created by ahmed radwan on 12/21/2017.
 */

public class CheckTokenModel {

    private int user_id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String android_token;
    private String image;

    public CheckTokenModel() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAndroid_token() {
        return android_token;
    }

    public void setAndroid_token(String android_token) {
        this.android_token = android_token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
