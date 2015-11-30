package com.example.tacademy.bikee.renter.searchresult.list;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.renter.searchresult.SearchResultListItem;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultView extends FrameLayout {
    public SearchResultView(Context context) {
        super(context);
        init();
    }

    private ImageView bicycle_picture;
    private TextView bicycle_name;
    private TextView height_text;
    private TextView height;
    private TextView type_text;
    private TextView type;
    private TextView payment;
    private TextView perDuration;
    private TextView distance;

    private void init() {
        inflate(getContext(), R.layout.view_search_result_item, this);
        bicycle_picture = (ImageView) findViewById(R.id.view_search_result_item_bicycle_picture_image_view);
        bicycle_name = (TextView) findViewById(R.id.view_search_result_item_bicycle_name_text_view);
        height_text = (TextView) findViewById(R.id.view_search_result_item_height_text_view);
        height = (TextView) findViewById(R.id.view_search_result_item_height_real_text_view);
        type_text = (TextView) findViewById(R.id.view_search_result_item_type_text_view);
        type = (TextView) findViewById(R.id.view_search_result_item_type_real_text_view);
        payment = (TextView) findViewById(R.id.view_search_result_item_payment_real_text_view);
        perDuration = (TextView) findViewById(R.id.view_search_result_item_per_duration_text_view);
        distance = (TextView) findViewById(R.id.view_search_result_item_distance_text_view);
    }

    public void setView(SearchResultListItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 6, bicycle_picture);
        bicycle_name.setText(item.getBicycle_name().toString());
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
        payment.setText(item.getPayment().toString());
        distance.setText(item.getDistance().toString());
    }
}
