package com.example.tacademy.bikee.renter.searchresult.list;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.manager.FontManager;

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
    private TextView height;
    private TextView type;
    private TextView payment;
    private TextView distance;

    private void init() {
        inflate(getContext(), R.layout.view_search_result_item, this);
        bicycle_picture = (ImageView)findViewById(R.id.view_search_result_item_bicycle_picture_image_view);
        bicycle_name = (TextView)findViewById(R.id.view_search_result_item_bicycle_name_text_view);
        bicycle_name.setTypeface(FontManager.getInstance().getTypeface(MyApplication.getmContext(), FontManager.NOTO));
        height = (TextView)findViewById(R.id.view_search_result_item_height_text_view);
        height.setTypeface(FontManager.getInstance().getTypeface(MyApplication.getmContext(), FontManager.NOTO));
        type = (TextView)findViewById(R.id.view_search_result_item_type_text_view);
        type.setTypeface(FontManager.getInstance().getTypeface(MyApplication.getmContext(), FontManager.NOTO));
        payment = (TextView)findViewById(R.id.view_search_result_item_payment_text_view);
        payment.setTypeface(FontManager.getInstance().getTypeface(MyApplication.getmContext(), FontManager.NOTO));
        distance = (TextView)findViewById(R.id.view_search_result_item_distance_text_view);
        distance.setTypeface(FontManager.getInstance().getTypeface(MyApplication.getmContext(), FontManager.NOTO));
    }

    public void setSearchResultView(SearchResultItem item) {
        bicycle_name.setText(item.getBicycle_name().toString());
        height.setText(item.getHeight().toString());
        type.setText(item.getType().toString());
        payment.setText(item.getPayment().toString());
        distance.setText(item.getDistance().toString());
    }
}
