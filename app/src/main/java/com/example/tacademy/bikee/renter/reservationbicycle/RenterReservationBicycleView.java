package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleView extends FrameLayout {
    private ImageView bicycleImageView;
    private ImageView approvedReservationImageView;
    private ImageView completedReservationImageView;
    private ImageView completedRentalImageView;
    private TextView bicycleNameTextView;
    private TextView startDateTextView;
    private TextView endDateTextView;
    private TextView paymentTextView;

    public RenterReservationBicycleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_renter_reservation_bicycle_item, this);
        bicycleImageView = (ImageView) findViewById(R.id.view_renter_reservation_bicycle_item_bicycle_picture_image_view);
        approvedReservationImageView = (ImageView) findViewById(R.id.view_renter_reservation_bicycle_item_approved_reservation);
        completedReservationImageView = (ImageView) findViewById(R.id.view_renter_reservation_bicycle_item_completed_reservation);
        completedRentalImageView = (ImageView) findViewById(R.id.view_renter_reservation_bicycle_item_completed_rental);
        bicycleNameTextView = (TextView) findViewById(R.id.view_renter_reservation_bicycle_item_bicycle_name_text_view);
        startDateTextView = (TextView) findViewById(R.id.view_renter_reservation_bicycle_item_start_date_text_view);
        endDateTextView = (TextView) findViewById(R.id.view_renter_reservation_bicycle_item_end_date_text_view);
        paymentTextView = (TextView) findViewById(R.id.view_renter_reservation_bicycle_item_payment_text_view);
    }

    public void setText(RenterReservationBicycleItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 6, bicycleImageView);

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

        bicycleNameTextView.setText(item.getBicycleName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
        startDateTextView.setText(simpleDateFormat.format(item.getStartDate()));
        endDateTextView.setText(simpleDateFormat.format(item.getEndDate()));
        paymentTextView.setText("" + item.getPayment());
    }
}
