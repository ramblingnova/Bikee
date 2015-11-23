package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptItem {
    private String imageURL;
    private String name;
    private String date;
    private String desc;
    private int point;

    public EvaluatingBicyclePostScriptItem(String imageURL, String name, String date, String desc, int point) {
        this.imageURL = imageURL;
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.point = point;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
