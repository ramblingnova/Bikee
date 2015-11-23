package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptView extends FrameLayout {
    public EvaluatingBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private ImageView image;
    private TextView name;
    private TextView date;
    private TextView desc;
    private RatingBar point;

    private void init() {
        inflate(getContext(), R.layout.view_evaluating_bicycle_post_script_item, this);
        image = (ImageView) findViewById(R.id.view_evaluating_bicycle_post_script_item_bicycle_picture_image_view);
        name = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_bicycle_name_text_view);
        date = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_date_time_text_view);
        desc = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_post_script_text_view);
        point = (RatingBar) findViewById(R.id.view_evaluating_bicycle_post_script_item_rating_bar);
    }

    public void EvaluatingBicyclePostScriptView(EvaluatingBicyclePostScriptItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 7, image);
        name.setText(item.getName());
        date.setText(item.getDate());
        desc.setText(item.getDesc());
        point.setRating(item.getPoint());
        point.setClickable(false);
    }
}
