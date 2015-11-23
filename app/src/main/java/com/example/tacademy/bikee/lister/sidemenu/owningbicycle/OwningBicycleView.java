package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleView extends FrameLayout {
    public OwningBicycleView(Context context) {
        super(context);
        init();
    }

    TextView nameTextView;
    TextView dateTextView;

    private void init() {
        inflate(getContext(), R.layout.view_owning_bicycle_item, this);
        nameTextView = (TextView) findViewById(R.id.view_owning_bicycle_item_bicycle_name);
        dateTextView = (TextView) findViewById(R.id.view_owning_bicycle_item_register_date);
    }

    public void setText(OwningBicycleItem item) {
        nameTextView.setText("" + item.getName());
        dateTextView.setText("" + item.getDate());
    }
}
