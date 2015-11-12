package com.example.tacademy.bikee.renter.searchresult.list;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

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
    private TextView payment;
    private TextView type;
    private TextView height;
    private TextView distance;

    private void init() {
        inflate(getContext(), R.layout.view_search_result_item, this);
        bicycle_picture = (ImageView)findViewById(R.id.view_search_result_item_bicycle_picture_image_view);
        bicycle_name = (TextView)findViewById(R.id.view_search_result_item_bicycle_name_text_view);
        payment = (TextView)findViewById(R.id.view_search_result_item_payment_text_view);
        type = (TextView)findViewById(R.id.view_search_result_item_type_text_view);
        height = (TextView)findViewById(R.id.view_search_result_item_height_text_view);
        distance = (TextView)findViewById(R.id.view_search_result_item_distance_text_view);
    }

    public void setSearchResultView(SearchResultItem item) {
        bicycle_name.setText("" + item.getBicycle_name());
        payment.setText("" + item.getPayment());
        type.setText("" + item.getType());
        height.setText("" + item.getHeight());
        distance.setText("" + item.getDistance());
    }
}
