package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tacademy.bikee.etc.dao.Price;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleItem {
    private String id;
    private String imageURL;
    private String name;
    private String date;

    public OwningBicycleItem(String id, String imageURL, String name, String date) {
        this.imageURL = imageURL;
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
