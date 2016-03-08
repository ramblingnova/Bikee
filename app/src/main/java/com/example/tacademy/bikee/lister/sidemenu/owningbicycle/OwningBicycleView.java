package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleView extends FrameLayout {
    private ImageView bicycleImage;
    private TextView nameTextView;
    private TextView dateTextView;

    public OwningBicycleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_owning_bicycle_item, this);
        bicycleImage = (ImageView) findViewById(R.id.view_owning_bicycle_item_bicycle_picture_image_view);
        nameTextView = (TextView) findViewById(R.id.view_owning_bicycle_item_bicycle_name_text_view);
        dateTextView = (TextView) findViewById(R.id.view_owning_bicycle_item_register_date_text_view);
    }

    public void setView(OwningBicycleItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicycleImage,
                6
        );
        nameTextView.setText("" + item.getName());
        dateTextView.setText("" + item.getDate());
    }
}
