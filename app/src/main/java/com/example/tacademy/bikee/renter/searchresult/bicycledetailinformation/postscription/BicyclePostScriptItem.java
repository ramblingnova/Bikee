package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptItem {
    BicyclePostScriptItem(String image_url, String name, float star, String desc, String date) {
        this.image_url = image_url;
        this.name = name;
        this.star = star;
        this.desc = desc;
        this.date = date;
    }

    // TODO 이미지
    String image_url;
    String name, desc, date;
    float star;
}
