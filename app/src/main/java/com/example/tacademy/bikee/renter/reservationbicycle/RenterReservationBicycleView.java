package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.Util;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleView extends FrameLayout {
    public RenterReservationBicycleView(Context context) {
        super(context);
        init();
    }

    ImageView iv;
    TextView tv1, tv2, tv3;

    private void init() {
        inflate(getContext(), R.layout.view_renter_reservation_bicycle_item, this);
        iv = (ImageView)findViewById(R.id.view_renter_reservation_bicycle_item_bicycle_picture_image_view);
        tv1 = (TextView)findViewById(R.id.view_renter_reservation_bicycle_item_bicycle_name_text_view);
        tv2 = (TextView)findViewById(R.id.view_renter_reservation_bicycle_item_payment_type_height_text_view);
        tv3 = (TextView)findViewById(R.id.view_renter_reservation_bicycle_item_distance_tex_view);
    }

    public void setText(RenterReservationBicycleItem item) {
        Util.setRectangleImageFromImageURL(getContext(), "http://www.hdwallpapers.in/walls/avengers_age_of_ultron_2015_movie-wide.jpg", iv);
        tv1.setText("" + item.tv1);
        tv2.setText("" + item.tv2);
        tv3.setText("" + item.tv3);
    }
}
