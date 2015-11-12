package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.content.Context;
import android.media.Rating;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptView extends FrameLayout {
    public EvaluatingBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private TextView name;
    private TextView email;
    private RatingBar bar;
    private TextView desc;
    private TextView time;

    private void init() {
        inflate(getContext(), R.layout.view_evaluating_bicycle_post_script_item, this);
        name = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_bicycle_name_text_view);
        email = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_mail_address_text_view);
        bar = (RatingBar) findViewById(R.id.view_evaluating_bicycle_post_script_item_rating_bar);
        desc = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_post_script_text_view);
        time = (TextView) findViewById(R.id.view_evaluating_bicycle_post_script_item_date_time_text_view);
    }

    public void EvaluatingBicyclePostScriptView(EvaluatingBicyclePostScriptItem item) {
        name.setText("" + item.name);
        email.setText("" + item.email);
        bar.setRating(item.point);
        bar.setClickable(false);
        desc.setText("" + item.desc);
        time.setText("" + item.time);
    }
}
