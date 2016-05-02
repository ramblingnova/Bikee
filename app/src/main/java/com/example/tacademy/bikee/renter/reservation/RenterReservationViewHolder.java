package com.example.tacademy.bikee.renter.reservation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnViewHolderClickListener;
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
    @Bind(R.id.view_holder_renter_reservation_step_image_view)
    ImageView stepImageView;
    @Bind(R.id.view_renter_reservation_bicycle_item_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.view_renter_reservation_bicycle_item_start_date_text_view)
    TextView startDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_end_date_text_view)
    TextView endDate;
    @Bind(R.id.view_renter_reservation_bicycle_item_payment_text_view)
    TextView bicyclePrice;

    private OnViewHolderClickListener onViewHolderClickListener;

    public RenterReservationViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_renter_reservation)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    public void setView(RenterReservationItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicyclePicture,
                6
        );

        Date currentDate = new Date();
        switch (item.getStatus()) {
            case "RR":
                if (currentDate.after(item.getStartDate()))
                    stepImageView.setImageResource(R.drawable.reservation_step4);
                else
                    stepImageView.setImageResource(R.drawable.reservation_step1);
                break;
            case "RS":
                if (currentDate.after(item.getStartDate()))
                    stepImageView.setImageResource(R.drawable.reservation_step4);
                else
                    stepImageView.setImageResource(R.drawable.reservation_step2);
                break;
            case "PS":
                if (currentDate.after(item.getStartDate())) {
                    if (currentDate.after(item.getEndDate()))
                        stepImageView.setImageResource(R.drawable.reservation_step4_2);
                    else
                        stepImageView.setImageResource(R.drawable.reservation_step3);
                } else {
                    stepImageView.setImageResource(R.drawable.reservation_step2_2);
                }
                break;
            case "RC":case "PC":
                stepImageView.setImageResource(R.drawable.reservation_step4);
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
