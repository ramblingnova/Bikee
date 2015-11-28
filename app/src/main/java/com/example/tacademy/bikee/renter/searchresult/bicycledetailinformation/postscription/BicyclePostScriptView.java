package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptView extends FrameLayout {
    private ImageView renterImage;
    private TextView renterName;
    private RatingBar point;
    private TextView postscript;
    private TextView createAt;

    public BicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_bicycle_post_script_item, this);
        renterImage = (ImageView) findViewById(R.id.view_bicycle_post_script_item_user_image1);
        renterName = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_name1);
        point = (RatingBar) findViewById(R.id.view_bicycle_post_script_item_rating_bar);
        point.setClickable(false);
        postscript = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_post_script1);
        createAt = (TextView) findViewById(R.id.view_bicycle_post_script_item_date1);
    }

    public void setView(BicyclePostScriptItem item) {
        Util.setCircleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 0, renterImage);
        renterName.setText("" + item.getRenterName());
        point.setRating(item.getPoint());
        postscript.setText("" + item.getPostScript());
        createAt.setText("" + item.getCreateAt());
    }
}
