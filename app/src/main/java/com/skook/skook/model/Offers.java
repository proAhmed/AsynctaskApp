package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class Offers {

    private int offer_id;
    private String offer_title;
    private String region_title;
    private String city_title;
    private double edge;
    private double distance;
    private String image;

    public Offers() {
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

    public double getEdge() {
        return edge;
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
