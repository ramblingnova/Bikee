package com.example.tacademy.bikee.renter.searchresult.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.POI;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.manager.FontManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultMapItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Created by Tacademy on 2015-11-18.
 */
public class BicycleInfoWindowView implements GoogleMap.InfoWindowAdapter {
    private View infoWindow;
    private ImageView bicycleImage;
    private TextView bicycle_name;
    private TextView type_text;
    private TextView type;
    private TextView height_text;
    private TextView height;
    private TextView payment_text;
    private TextView payment;
    private TextView perDuration;
    Map<Marker, POI> mPOIResolver;


    public BicycleInfoWindowView(Context context, Map<Marker, POI> poiResolver) {
        infoWindow = LayoutInflater.from(context).inflate(R.layout.view_bicycle_info_window, null);
        bicycleImage = (ImageView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_picture_image_view);
        bicycle_name = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_name_text_view);
        height_text = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_height_text_view);
        height = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_height_real_text_view);
        type_text = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_type_text_view);
        type = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_type_real_text_view);
        payment_text = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_payment_text_view1);
        payment = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_payment_real_text_view);
        perDuration = (TextView) infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_payment_text_view2);
        FontManager.getInstance().setTextViewFont(FontManager.NOTO, bicycle_name, type_text, type, height_text, height, payment_text, payment, perDuration);
        mPOIResolver = poiResolver;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        POI poi = mPOIResolver.get(marker);
        setView(poi.getItem());
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public void setView(SearchResultMapItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 12, bicycleImage);
        bicycle_name.setText(item.getBicycle_name().toString());
        String typeString = "보급형";
        switch (item.getType().toString()) {
            case "A":
                typeString = "보급형";
                break;
            case "B":
                typeString = "산악용";
                break;
            case "C":
                typeString = "하이브리드";
                break;
            case "D":
                typeString = "픽시";
                break;
            case "E":
                typeString = "폴딩";
                break;
            case "F":
                typeString = "미니벨로";
                break;
            case "G":
                typeString = "전기자전거";
                break;
        }
        type.setText(typeString);
        String heightString = "~145cm";
        switch (item.getHeight().toString()) {
            case "01":
                heightString = "~145cm";
                break;
            case "02":
                heightString = "145cm~155cm";
                break;
            case "03":
                heightString = "155cm~165cm";
                break;
            case "04":
                heightString = "165cm~175cm";
                break;
            case "05":
                heightString = "175cm~185cm";
                break;
            case "06":
                heightString = "185cm~";
                break;
        }
        height.setText(heightString);
        payment.setText(item.getPayment().toString());
    }
}
