package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptView extends FrameLayout {
    public BicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    ImageView image;
    TextView name, desc, date;
    RatingBar star;

    private void init() {
        inflate(getContext(), R.layout.view_bicycle_post_script_item, this);
        image = (ImageView) findViewById(R.id.view_bicycle_post_script_item_user_image1);
        name = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_name1);
        star = (RatingBar) findViewById(R.id.view_bicycle_post_script_item_rating_bar);
        star.setClickable(false);
        desc = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_post_script1);
        date = (TextView) findViewById(R.id.view_bicycle_post_script_item_date1);
    }

    public void setText(BicyclePostScriptItem item) {
//        image.setImageDrawable(item.image);
        name.setText("" + item.name);
        star.setRating(item.star);
        desc.setText("" + item.date);
        date.setText("" + item.date);
    }
}
