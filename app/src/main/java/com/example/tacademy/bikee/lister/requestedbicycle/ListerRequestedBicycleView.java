package com.example.tacademy.bikee.lister.requestedbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;

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
        renter_name = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_renter_name_text_view);
        start_date = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_start_date_text_view);
        end_date = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_end_date_text_view);
        bicycle_name = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_bicycle_name_text_view);
        price = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_price_text_view);
        FontManager.getInstance().setTextViewFont(FontManager.NOTO,
                renter_name,
                start_date,
                end_date,
                bicycle_name,
                price
        );
    }

    public void setText(ListerRequestedBicycleItem item) {
        renter_name.setText("" + item.tv1);
        start_date.setText("" + item.tv2);
        end_date.setText("" + item.tv3);
        bicycle_name.setText("" + item.tv4);
        price.setText("" + item.tv5);
    }
}
