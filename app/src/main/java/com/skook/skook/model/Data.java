
package com.skook.skook.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("fax")
    private String mFax;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("phone")
    private String mPhone;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFax() {
        return mFax;
    }

    public void setFax(String fax) {
        mFax = fax;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

}
