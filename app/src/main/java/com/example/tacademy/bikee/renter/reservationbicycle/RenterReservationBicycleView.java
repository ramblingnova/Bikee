package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleView extends FrameLayout {
    @Bind(R.id.view_renter_reservation_bicycle_item_bicycle_picture_image_view)
    ImageView bicyclePicture;
    @Bind(R.id.view_renter_reservation_bicycle_item_approved_reservation)
    ImageView approvedReservationImageView;
    @Bind(R.id.view_renter_reservation_bicycle_item_completed_reservation)
    ImageView completedReservationImageView;
    @Bind(R.id.view_renter_reservation_bicycle_item_completed_rental)
    ImageView completedRentalImageView;
    @Bind(R.id.view_renter_reservation_bicycle_item_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.view_renter_reservation_bicycle_item_start_date_text_view)
    TextView startDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_end_date_text_view)
    TextView endDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_payment_text_view)
    TextView bicyclePrice;

    public RenterReservationBicycleView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_renter_reservation_bicycle_item, this);
        ButterKnife.bind(this);
    }

    public void setView(RenterReservationBicycleItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 6, bicyclePicture);

        Date currentDate = new Date(System.currentTimeMillis());
        if (currentDate.after(item.getEndDate()) == false) {
            switch (item.getStatus()) {
                case "RR":
                    approvedReservationImageView.setVisibility(VISIBLE);
                    completedReservationImageView.setVisibility(INVISIBLE);
                    completedRentalImageView.setVisibility(INVISIBLE);
                    break;
                case "RS":
                case "RC":
                    approvedReservationImageView.setVisibility(INVISIBLE);
                    completedReservationImageView.setVisibility(VISIBLE);
                    completedRentalImageView.setVisibility(INVISIBLE);
                    break;
            }
        } else {
            approvedReservationImageView.setVisibility(INVISIBLE);
            completedReservationImageView.setVisibility(INVISIBLE);
            completedRentalImageView.setVisibility(VISIBLE);
        }

        bicycleName.setText(item.getBicycleName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd. HH:mm");
        startDate.setText(simpleDateFormat.format(item.getStartDate()));
        endDate.setText(simpleDateFormat.format(item.getEndDate()));
        bicyclePrice.setText("" + item.getPayment());
    }
}
