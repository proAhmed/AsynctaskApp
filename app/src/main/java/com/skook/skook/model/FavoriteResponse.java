package com.skook.skook.model;

/**
 * Created by ahmed radwan on 11/28/2017.
 */

public class FavoriteResponse {
   private int fav;

    public FavoriteResponse(int fav) {
        this.fav = fav;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
}
