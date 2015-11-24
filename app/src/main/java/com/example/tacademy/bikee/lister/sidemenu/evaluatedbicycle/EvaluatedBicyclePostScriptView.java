package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class EvaluatedBicyclePostScriptView extends FrameLayout {
    private ImageView renterImage;
    private TextView renterName;
    private TextView bicycleName;
    private TextView createDate;
    private TextView description;
    private RatingBar point;

    public EvaluatedBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_evaluated_bicycle_post_script_item, this);
        renterImage = (ImageView)findViewById(R.id.view_evaluated_bicycle_post_script_item_renter_image);
        renterName = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_renter_name_text_view);
        bicycleName = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_bicycle_name_text_view);
        createDate = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_date_time_text_view);
        description = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_post_script_text_view);
        point = (RatingBar)findViewById(R.id.view_evaluated_bicycle_post_script_item_rating_bar);
    }

    public void setText(EvaluatedBicyclePostScriptItem item) {
        renterName.setText(item.getRenterName());
        bicycleName.setText(item.getBicycleName());
        createDate.setText(item.getCreateDate());
        description.setText(item.getDescription());
        point.setRating(item.getPoint());
        point.setClickable(false);
    }
}
