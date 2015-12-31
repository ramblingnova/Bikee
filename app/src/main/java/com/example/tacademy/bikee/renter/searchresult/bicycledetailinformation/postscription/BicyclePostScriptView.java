package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

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
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptView extends FrameLayout {
    @Bind(R.id.view_bicycle_post_script_item_user_image1)
    ImageView renterImage;
    @Bind(R.id.view_bicycle_post_script_item_user_name1)
    TextView renterName;
    @Bind(R.id.view_bicycle_post_script_item_rating_bar)
    RatingBar point;
    @Bind(R.id.view_bicycle_post_script_item_user_post_script1)
    TextView postscript;
    @Bind(R.id.view_bicycle_post_script_item_date1)
    TextView createAt;

    public BicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_bicycle_post_script_item, this);
        ButterKnife.bind(this);
        point.setClickable(false);
    }

    public void setView(BicyclePostScriptItem item) {
        Util.setCircleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 0, renterImage);
        renterName.setText("" + item.getRenterName());
        point.setRating(item.getPoint());
        postscript.setText("" + item.getPostScript());
        createAt.setText("" + item.getCreateAt());
    }
}
