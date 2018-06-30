package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class Categories {

    private int category_id;
    private String title;
    private String content;
    private String slug;

    public Categories() {
    }
    public Categories(String title) {
        this.title =title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return title ;
    }
}
