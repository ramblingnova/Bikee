package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptView extends FrameLayout {
    public EvaluatingBicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    TextView name;
    TextView email;
    TextView desc;
    TextView time;

    private void init() {
        inflate(getContext(), R.layout.view_evaluation_bicycle_post_script_item, this);
        name = (TextView) findViewById(R.id.view_evaluation_bicycle_post_script_item_bicycle_name);
        email = (TextView) findViewById(R.id.view_evaluation_bicycle_post_script_item_mail_address);
        desc = (TextView) findViewById(R.id.view_evaluation_bicycle_post_script_item_post_script);
        time = (TextView) findViewById(R.id.view_evaluation_bicycle_post_script_item_date_time);
    }

    public void setText(EvaluatingBicyclePostScriptItem item) {
        name.setText("" + item.name);
        email.setText("" + item.email);
        desc.setText("" + item.desc);
        time.setText("" + item.time);
    }
}
