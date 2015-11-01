package com.example.tacademy.bikee;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultView extends FrameLayout {
    public SearchResultView(Context context) {
        super(context);
        init();
    }

    ImageView iv;
    TextView tv1, tv2, tv3;

    private void init() {
        inflate(getContext(), R.layout.view_search_result_item, this);
        iv = (ImageView)findViewById(R.id.view_search_result_item_image_view);
        tv1 = (TextView)findViewById(R.id.view_search_result_item_bicycle_name);
        tv2 = (TextView)findViewById(R.id.view_search_result_item_payment_type_height);
        tv3 = (TextView)findViewById(R.id.view_search_result_item_distance);
    }

    public void setText(SearchResultItem item) {
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
    }
}
