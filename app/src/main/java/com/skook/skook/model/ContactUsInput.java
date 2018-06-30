package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/19/2017.
 */

public class ContactUsInput {

    private String name;
    private String email;
    private String message;

    public ContactUsInput() {
    }

    public ContactUsInput(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
