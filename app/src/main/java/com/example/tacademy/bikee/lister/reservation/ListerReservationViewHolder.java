package com.example.tacademy.bikee.lister.reservation;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnViewHolderClickListener;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerReservationViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_lister_requested_bicycle_item_step_image_view)
    ImageView requestReservationImage;
    @Bind(R.id.view_lister_requested_bicycle_item_image_view)
    ImageView renterImage;
    @Bind(R.id.view_lister_requested_bicycle_item_renter_name_text_view)
    TextView renterName;
    @Bind(R.id.view_lister_requested_bicycle_item_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.view_lister_requested_bicycle_item_start_date_text_view)
    TextView startDate;
    @Bind(R.id.view_lister_requested_bicycle_item_end_date_text_view)
    TextView endDate;
    @Bind(R.id.view_lister_requested_bicycle_item_time_text_view)
    TextView totalTIme;
    @Bind(R.id.view_lister_requested_bicycle_item_price_text_view)
    TextView price;

    private OnViewHolderClickListener onViewHolderClickListener;

    public ListerReservationViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_lister_reservation)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setView(ListerReservationItem item) {
        ImageUtil.setCircleImageFromURL(
                MyApplication.getmContext(),
                item.getRenterImageURL(),
                R.drawable.noneimage,
                0,
                renterImage
        );

        Date currentDate = new Date();
        switch (item.getReservationStatus()) {
            case "RR":
                if (currentDate.after(item.getReservationStartDate())) {
                    requestReservationImage.setImageResource(R.drawable.lister_reservation_step4_1);
                    if (Build.VERSION.SDK_INT < 23) {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    requestReservationImage.setImageResource(R.drawable.lister_reservation_step1);
                    if (Build.VERSION.SDK_INT < 23) {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow));
                    } else {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow, null));
                    }
                }
                break;
            case "RS":
                if (currentDate.after(item.getReservationStartDate())) {
                    requestReservationImage.setImageResource(R.drawable.lister_reservation_step4_1);
                    if (Build.VERSION.SDK_INT < 23) {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    requestReservationImage.setImageResource(R.drawable.lister_reservation_step2);
                    if (Build.VERSION.SDK_INT < 23) {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "PS":
                if (currentDate.after(item.getReservationStartDate())) {
                    if (currentDate.after(item.getReservationEndDate())) {
                        requestReservationImage.setImageResource(R.drawable.lister_reservation_step4_2);
                        if (Build.VERSION.SDK_INT < 23) {
                            price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                        } else {
                            price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                        }
                    } else {
                        requestReservationImage.setImageResource(R.drawable.lister_reservation_step3);
                        if (Build.VERSION.SDK_INT < 23) {
                            price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue));
                        } else {
                            price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue, null));
                        }
                    }
                } else {
                    requestReservationImage.setImageResource(R.drawable.lister_reservation_step2_2);
                    if (Build.VERSION.SDK_INT < 23) {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "RC":
            case "PC":
                requestReservationImage.setImageResource(R.drawable.lister_reservation_step4_1);
                if (Build.VERSION.SDK_INT < 23) {
                    price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                } else {
                    price.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                }
                break;
            default:
                break;
        }

        renterName.setText(item.getRenterName());
        bicycleName.setText(item.getBicycleTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd. HH:mm", java.util.Locale.getDefault());
        startDate.setText(simpleDateFormat.format(item.getReservationStartDate()));
        endDate.setText(simpleDateFormat.format(item.getReservationEndDate()));
        totalTIme.setText(
                RefinementUtil.calculateRentPeriod(
                        item.getReservationStartDate(),
                        item.getReservationEndDate()
                )
        );
        price.setText(
                RefinementUtil.calculatePrice(
                        item.getReservationStartDate(),
                        item.getReservationEndDate(),
                        item.getPricePerMonth(),
                        item.getPricePerDay(),
                        item.getPricePerHour()
                ) + "원"
        );
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }
}
