package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class EvaluatedBicyclePostScriptView extends FrameLayout {
    public EvaluatedBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    ImageView iv;
    TextView tv1, tv2, tv3, tv4;

    private void init() {
        inflate(getContext(), R.layout.view_evluated_bicycle_post_script_item, this);
        iv = (ImageView)findViewById(R.id.view_evluated_bicycle_post_script_item_renter_image);
        tv1 = (TextView)findViewById(R.id.view_evluated_bicycle_post_script_item_email);
        tv2 = (TextView)findViewById(R.id.view_evluated_bicycle_post_script_item_bicycle_name);
        tv3 = (TextView)findViewById(R.id.view_evluated_bicycle_post_script_item_post_script);
        tv4 = (TextView)findViewById(R.id.view_evluated_bicycle_post_script_item_date_time);
    }

    public void setText(EvaluatedBicyclePostScriptItem item) {
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
        tv4.setText("" + item.tv4);
    }
}
