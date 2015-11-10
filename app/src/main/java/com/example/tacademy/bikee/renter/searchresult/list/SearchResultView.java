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

    ImageView iv;
    TextView tv1, tv2, tv3;

    private void init() {
        inflate(getContext(), R.layout.view_search_result_item, this);
        iv = (ImageView)findViewById(R.id.view_search_result_item_image_view);
        tv1 = (TextView)findViewById(R.id.view_search_result_item_bicycle_name_text_view);
        tv2 = (TextView)findViewById(R.id.view_search_result_item_payment_type_height_text_view);
        tv3 = (TextView)findViewById(R.id.view_search_result_item_distance_text_view);
    }

    public void setText(SearchResultItem item) {
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
    }
}
