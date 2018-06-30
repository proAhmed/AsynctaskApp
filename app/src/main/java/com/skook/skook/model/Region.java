package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class Region {

    private int region_id;
    private String title;
    private String region_name;
    private String slug;

    public Region() {
    }


    public Region(String title) {
        this.title =title;
    }

    public Region(String title,int region_id) {
        this.title =title;
        this.region_id =region_id;
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

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return   title;
    }
}
