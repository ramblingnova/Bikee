package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleView extends FrameLayout {
    public ListerRequestedBicycleView(Context context) {
        super(context);
        init();
    }

    TextView renter_name;
    TextView start_date;
    TextView end_date;
    TextView bicycle_name;
    TextView price;

    private void init() {
        inflate(getContext(), R.layout.view_lister_requested_bicycle_item, this);
        renter_name = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_renter_name);
        start_date = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_start_date);
        end_date = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_end_date);
        bicycle_name = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_bicycle_name);
        price = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_price);
    }

    public void setText(ListerRequestedBicycleItem item) {
        renter_name.setText("" + item.tv1);
        start_date.setText("" + item.tv2);
        end_date.setText("" + item.tv3);
        bicycle_name.setText("" + item.tv4);
        price.setText("" + item.tv5);
    }
}
