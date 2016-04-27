package com.example.tacademy.bikee.renter.reservation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_renter_reservation_bicycle_item_bicycle_picture_image_view)
    ImageView bicyclePicture;
    @Bind(R.id.view_holder_renter_reservation_step1_image_view)
    ImageView step1ImageView;
    @Bind(R.id.view_holder_renter_reservation_step2_image_view)
    ImageView step2ImageView;
    @Bind(R.id.view_holder_renter_reservation_step3_image_view)
    ImageView step3ImageView;
    @Bind(R.id.view_holder_renter_reservation_step4_image_view)
    ImageView step4ImageView;
    @Bind(R.id.view_renter_reservation_bicycle_item_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.view_renter_reservation_bicycle_item_start_date_text_view)
    TextView startDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_end_date_text_view)
    TextView endDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_payment_text_view)
    TextView bicyclePrice;

    private OnRenterReservationViewClickListener listener;

    public RenterReservationViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_renter_reservation)
    void onClick(View view) {
        listener.onRenterReservationClick(view);
    }

    public void setOnRenterReservationViewClickListener(OnRenterReservationViewClickListener listener) {
        this.listener = listener;
    }

    public void setView(RenterReservationItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicyclePicture,
                6
        );

        step1ImageView.setVisibility(View.INVISIBLE);
        step2ImageView.setVisibility(View.INVISIBLE);
        step3ImageView.setVisibility(View.INVISIBLE);
        step4ImageView.setVisibility(View.INVISIBLE);
        Date currentDate = new Date();
        switch (item.getStatus()) {
            case "RR":
                if (currentDate.after(item.getStartDate())) {
                    step4ImageView.setVisibility(View.VISIBLE);
                    step4ImageView.setImageResource(R.drawable.reservation_step4_1);
                } else {
                    step1ImageView.setVisibility(View.VISIBLE);
                    step1ImageView.setImageResource(R.drawable.reservation_step1);
                }
                break;
            case "RS":
                if (currentDate.after(item.getStartDate())) {
                    step4ImageView.setVisibility(View.VISIBLE);
                    step4ImageView.setImageResource(R.drawable.reservation_step4_1);
                } else {
                    step2ImageView.setVisibility(View.VISIBLE);
                    step2ImageView.setImageResource(R.drawable.reservation_step2);
                }
                break;
            case "PS":
                if (currentDate.after(item.getStartDate())) {
                    if (currentDate.after(item.getEndDate())) {
                        step4ImageView.setVisibility(View.VISIBLE);
                        step4ImageView.setImageResource(R.drawable.reservation_step4_2);
                    } else {
                        step3ImageView.setVisibility(View.VISIBLE);
                        step3ImageView.setImageResource(R.drawable.reservation_step3);
                    }
                } else {
                    step3ImageView.setVisibility(View.VISIBLE);
                    step3ImageView.setImageResource(R.drawable.reservation_step3);
                }
                break;
            case "RC":
                step4ImageView.setVisibility(View.VISIBLE);
                step4ImageView.setImageResource(R.drawable.reservation_step4_1);
                break;
            default:
                break;
        }

        bicycleName.setText(item.getBicycleName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd HH:mm", java.util.Locale.getDefault());
        startDate.setText(simpleDateFormat.format(item.getStartDate()));
        endDate.setText(simpleDateFormat.format(item.getEndDate()));
        bicyclePrice.setText("" + item.getPayment());
    }
}
