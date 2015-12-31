package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptView extends FrameLayout {
    @Bind(R.id.view_evaluating_bicycle_post_script_item_bicycle_picture_image_view)
    ImageView image;
    @Bind(R.id.view_evaluating_bicycle_post_script_item_bicycle_name_text_view)
    TextView name;
    @Bind(R.id.view_evaluating_bicycle_post_script_item_date_time_text_view)
    TextView date;
    @Bind(R.id.view_evaluating_bicycle_post_script_item_post_script_text_view)
    TextView desc;
    @Bind(R.id.view_evaluating_bicycle_post_script_item_rating_bar)
    RatingBar point;

    public EvaluatingBicyclePostScriptView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_evaluating_bicycle_post_script_item, this);
        ButterKnife.bind(this);
    }

    public void setView(EvaluatingBicyclePostScriptItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 7, image);
        name.setText(item.getName());
        date.setText(item.getDate());
        desc.setText(item.getDesc());
        point.setRating(item.getPoint());
        point.setClickable(false);
    }
}
