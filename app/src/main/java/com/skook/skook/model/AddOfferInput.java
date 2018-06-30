package com.skook.skook.model;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/19/2017.
 */

public class AddOfferInput {

    private String userName;
    private String phone;
    private String email;
    private String showType;
    private String category;
    private String distance;
    private String region;
    private String city;
    private String area;
    private String district;
    private String price;
    private String edge;
    private String details;
    private ArrayList<String> image;
    private String userLong;
    private String userLat;


    public AddOfferInput() {
    }

    public AddOfferInput(String userName, String phone, String email, String showType, String category, String distance, String region, String city, String district, String price, String edge, String details, ArrayList<String> image, String userLong, String userLat) {
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.showType = showType;
        this.category = category;
        this.distance = distance;
        this.region = region;
        this.city = city;
        this.district = district;
        this.price = price;
        this.edge = edge;
        this.details = details;
        this.image = image;
        this.userLong = userLong;
        this.userLat = userLat;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }
}
