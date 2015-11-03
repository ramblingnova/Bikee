package com.example.tacademy.bikee;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleView extends FrameLayout {
    public OwningBicycleView(Context context) {
        super(context);
        init();
    }

    TextView name;
    TextView date;

    private void init() {
        inflate(getContext(), R.layout.view_owning_bicycle_item, this);
        name = (TextView) findViewById(R.id.view_owning_bicycle_item_bicycle_name);
        date = (TextView) findViewById(R.id.view_owning_bicycle_item_register_date);
    }

    public void setText(OwningBicycleItem item) {
        name.setText("" + item.tv1);
        date.setText("" + item.tv2);
    }
}
