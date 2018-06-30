package com.skook.skook.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed radwan on 11/26/2017.
 */

public class SearchInput implements Parcelable {

    private int city;
    private int region;
    private int category;
    private String type;
    private String hay;

    public SearchInput() {
    }

    protected SearchInput(Parcel in) {
        city = in.readInt();
        region = in.readInt();
        category = in.readInt();
        type = in.readString();
        hay = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(city);
        dest.writeInt(region);
        dest.writeInt(category);
        dest.writeString(type);
        dest.writeString(hay);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchInput> CREATOR = new Creator<SearchInput>() {
        @Override
        public SearchInput createFromParcel(Parcel in) {
            return new SearchInput(in);
        }

        @Override
        public SearchInput[] newArray(int size) {
            return new SearchInput[size];
        }
    };

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHay() {
        return hay;
    }

    public void setHay(String hay) {
        this.hay = hay;
    }
}
