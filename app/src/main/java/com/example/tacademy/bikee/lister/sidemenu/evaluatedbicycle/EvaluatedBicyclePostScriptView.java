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
    public EvaluatedBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    private ImageView iv;
    private TextView mail;
    private TextView name;
    private RatingBar rb;
    private TextView body;
    private TextView date;

    private void init() {
        inflate(getContext(), R.layout.view_evaluated_bicycle_post_script_item, this);
        iv = (ImageView)findViewById(R.id.view_evaluated_bicycle_post_script_item_renter_image);
        mail = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_email_text_view);
        name = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_bicycle_name_text_view);
        rb = (RatingBar)findViewById(R.id.view_evaluated_bicycle_post_script_item_rating_bar);
        body = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_post_script_text_view);
        date = (TextView)findViewById(R.id.view_evaluated_bicycle_post_script_item_date_time_text_view);
    }

    public void setText(EvaluatedBicyclePostScriptItem item) {
        mail.setText(item.getMail());
        name.setText(item.getName());
        rb.setRating(item.getPoint());
        rb.setClickable(false);
        body.setText(item.getBody());
        date.setText(item.getDate());
    }
}
