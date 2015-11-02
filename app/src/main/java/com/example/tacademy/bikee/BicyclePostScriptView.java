package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptView extends FrameLayout {
    public BicyclePostScriptView(Context context) {
        super(context);
        init();
    }

    TextView name, script, date;

    private void init() {
        inflate(getContext(), R.layout.view_bicycle_post_script_item, this);
        name = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_name1);
        script = (TextView) findViewById(R.id.view_bicycle_post_script_item_user_post_script1);
        date = (TextView) findViewById(R.id.view_bicycle_post_script_item_date1);
    }

    public void setText(BicyclePostScriptItem item) {
        name.setText("" + item.name);
        script.setText("" + item.script);
        date.setText("" + item.date);
    }


}
