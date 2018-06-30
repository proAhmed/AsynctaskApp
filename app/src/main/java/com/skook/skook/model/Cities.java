package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class Cities {

    private int region_id;
    private String title;
    private int city_id;
    private String slug;
    private String city_name;


    public Cities() {
    }
    public Cities(int city_id) {
        this.city_id =city_id;
    }
    public Cities(String title) {
        this.title =title;
    }
    public Cities(String title,int cityId) {
        this.title =title;
        this.city_id =cityId;
    }
    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return   title;
    }
}
