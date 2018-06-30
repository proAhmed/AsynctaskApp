package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/19/2017.
 */

public class SignUpInput {

    private String email;
    private String password;
    private String phone;
    private String userName;


    public SignUpInput() {
    }

    public SignUpInput(String email, String password, String userName, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userName = userName;
    }

    public SignUpInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
