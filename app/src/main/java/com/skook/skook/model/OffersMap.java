package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class OffersMap {

     private String details;
    private String longitude;
    private String latitude;
    private int offer_id;
    private String offer_title;
    private String region_title;
    private String city_title;
    private String edge;
    private String distance;
    private String image;
    private String price;

    public OffersMap() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getRegion_title() {
        return region_title;
    }

    public void setRegion_title(String region_title) {
        this.region_title = region_title;
    }

    public String getCity_title() {
        return city_title;
    }

    public void setCity_title(String city_title) {
        this.city_title = city_title;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
