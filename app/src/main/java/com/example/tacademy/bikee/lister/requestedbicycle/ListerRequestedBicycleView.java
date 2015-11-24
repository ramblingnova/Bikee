package com.example.tacademy.bikee.lister.requestedbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleView extends FrameLayout {
    private ImageView renterImage;
    private TextView renterName;
    private TextView bicycleName;
    private TextView startDate;
    private TextView endDate;
    private TextView price;

    public ListerRequestedBicycleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_lister_requested_bicycle_item, this);
        renterImage = (ImageView) findViewById(R.id.view_lister_requested_bicycle_item_image_view);
        renterName = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_renter_name_text_view);
        bicycleName = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_bicycle_name_text_view);
        startDate = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_start_date_text_view);
        endDate = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_end_date_text_view);
        price = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_price_text_view);
    }

    public void setText(ListerRequestedBicycleItem item) {
        renterName.setText("" + item.getRenterName());
        bicycleName.setText("" + item.getBicycleName());
        startDate.setText("" + item.getStartDate());
        endDate.setText("" + item.getEndDate());
        price.setText("" + item.getPrice());
    }
}
