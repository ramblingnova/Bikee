package com.example.tacademy.bikee.lister.requestedbicycle;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleView extends FrameLayout {
    private ImageView requestReservationImage;
    private ImageView completeReservationImage;
    private ImageView completeRentalImage;
    private ImageView renterImage;
    private TextView renterName;
    private TextView bicycleName;
    private TextView startDate;
    private TextView endDate;
    private TextView price;

    public ListerRequestedBicycleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_lister_requested_bicycle_item, this);
        requestReservationImage = (ImageView) findViewById(R.id.view_lister_requested_bicycle_item_request_reservation_image_view);
        completeReservationImage = (ImageView) findViewById(R.id.view_lister_requested_bicycle_item_complete_reservation_image_view);
        completeRentalImage = (ImageView) findViewById(R.id.view_lister_requested_bicycle_item_complete_rantal_image_view);
        renterImage = (ImageView) findViewById(R.id.view_lister_requested_bicycle_item_image_view);
        renterName = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_renter_name_text_view);
        bicycleName = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_bicycle_name_text_view);
        startDate = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_start_date_text_view);
        endDate = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_end_date_text_view);
        price = (TextView) findViewById(R.id.view_lister_requested_bicycle_item_price_text_view);
    }

    public void setView(ListerRequestedBicycleItem item) {
        ImageUtil.setCircleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.noneimage,
                0,
                renterImage
        );

        Date currentDate = new Date(System.currentTimeMillis());
        if (currentDate.after(item.getEndDate()) == false) {
            switch (item.getStatus()) {
                case "RR":
                        requestReservationImage.setVisibility(VISIBLE);
                        completeReservationImage.setVisibility(INVISIBLE);
                        completeRentalImage.setVisibility(INVISIBLE);
                    break;
                case "RS":
                case "RC":
                    requestReservationImage.setVisibility(INVISIBLE);
                    completeReservationImage.setVisibility(VISIBLE);
                    completeRentalImage.setVisibility(INVISIBLE);
                    break;
            }
        } else {
            requestReservationImage.setVisibility(INVISIBLE);
            completeReservationImage.setVisibility(INVISIBLE);
            completeRentalImage.setVisibility(VISIBLE);
        }

        renterName.setText("" + item.getRenterName());
        bicycleName.setText("" + item.getBicycleName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd. HH:mm");
        startDate.setText("" + simpleDateFormat.format(item.getStartDate()));
        endDate.setText("" + simpleDateFormat.format(item.getEndDate()));
        price.setText("" + item.getPrice());
    }
}
