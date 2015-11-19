package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

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
        name.setText("" + item.getName());
        date.setText("" + item.getDate());
    }
}
