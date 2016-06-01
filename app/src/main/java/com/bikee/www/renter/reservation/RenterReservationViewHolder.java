package com.bikee.www.renter.reservation;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikee.www.common.interfaces.OnViewHolderClickListener;
import com.bikee.www.R;
import com.bikee.www.etc.MyApplication;
import com.bikee.www.etc.utils.ImageUtil;
import com.bikee.www.etc.utils.RefinementUtil;

import java.text.DecimalFormat;
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
    @Bind(R.id.view_renter_reservation_bicycle_item_time_text_view)
    TextView totalTime;
    @Bind(R.id.view_renter_reservation_bicycle_item_payment_text_view)
    TextView bicyclePrice;

    private OnViewHolderClickListener onViewHolderClickListener;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

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
                item.getBicycleImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicyclePicture,
                MyApplication.getmContext()
                        .getResources()
                        .getDimension(
                                R.dimen.view_renter_reservation_bicycle_item_bicycle_picture_image_view_round_radius
                        )
        );

        Date currentDate = new Date();
        switch (item.getReservationStatus()) {
            case "RR":
                if (currentDate.after(item.getReservationStartDate())) {
                    stepImageView.setImageResource(R.drawable.reservation_step4);
                    if (Build.VERSION.SDK_INT < 23) {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.reservation_step1);
                    if (Build.VERSION.SDK_INT < 23) {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow));
                    } else {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow, null));
                    }
                }
                break;
            case "RS":
                if (currentDate.after(item.getReservationStartDate())) {
                    stepImageView.setImageResource(R.drawable.reservation_step4);
                    if (Build.VERSION.SDK_INT < 23) {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.reservation_step2);
                    if (Build.VERSION.SDK_INT < 23) {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "PS":
                if (currentDate.after(item.getReservationStartDate())) {
                    if (currentDate.after(item.getReservationEndDate())) {
                        stepImageView.setImageResource(R.drawable.reservation_step4_2);
                        if (Build.VERSION.SDK_INT < 23) {
                            bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                        } else {
                            bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                        }
                    } else {
                        stepImageView.setImageResource(R.drawable.reservation_step3);
                        if (Build.VERSION.SDK_INT < 23) {
                            bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue));
                        } else {
                            bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue, null));
                        }
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.reservation_step2_2);
                    if (Build.VERSION.SDK_INT < 23) {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "RC":
            case "PC":
                stepImageView.setImageResource(R.drawable.reservation_step4);
                if (Build.VERSION.SDK_INT < 23) {
                    bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                } else {
                    bicyclePrice.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                }
                break;
            default:
                break;
        }

        bicycleName.setText(item.getBicycleTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
        startDate.setText(simpleDateFormat.format(item.getReservationStartDate()));
        endDate.setText(simpleDateFormat.format(item.getReservationEndDate()));
        totalTime.setText(
                RefinementUtil.calculateRentPeriod(
                        item.getReservationStartDate(),
                        item.getReservationEndDate()
                )
        );
        bicyclePrice.setText(
                decimalFormat.format(
                        Long.parseLong(
                                RefinementUtil.calculatePrice(
                                        item.getReservationStartDate(),
                                        item.getReservationEndDate(),
                                        item.getPricePerMonth(),
                                        item.getPricePerDay(),
                                        item.getPricePerHour()
                                ) + ""
                        )
                ) + "원"
        );
    }
}
