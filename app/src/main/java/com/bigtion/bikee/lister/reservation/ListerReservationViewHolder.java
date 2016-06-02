package com.bigtion.bikee.lister.reservation;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigtion.bikee.common.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.etc.utils.ImageUtil;
import com.bigtion.bikee.etc.utils.RefinementUtil;
import com.bigtion.bikee.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerReservationViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_lister_reservation_step_image_view)
    ImageView stepImageView;
    @Bind(R.id.view_holder_lister_reservation_renter_picture_image_view)
    ImageView renterPictureImageView;
    @Bind(R.id.view_holder_lister_reservation_renter_name_text_view)
    TextView renterNameTextView;
    @Bind(R.id.view_holder_lister_reservation_bicycle_title_text_view)
    TextView bicycleTitleTextView;
    @Bind(R.id.view_holder_lister_reservation_start_date_text_view)
    TextView startDateTextView;
    @Bind(R.id.view_holder_lister_reservation_end_date_text_view)
    TextView endDateTextView;
    @Bind(R.id.view_holder_lister_reservation_total_period_text_view)
    TextView totalPeriodTextView;
    @Bind(R.id.view_holder_lister_reservation_price_text_view)
    TextView priceTextView;

    private OnViewHolderClickListener onViewHolderClickListener;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

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
                renterPictureImageView
        );

        Date currentDate = new Date();
        switch (item.getReservationStatus()) {
            case "RR":
                if (currentDate.after(item.getReservationStartDate())) {
                    stepImageView.setImageResource(R.drawable.lister_reservation_step4_1);
                    if (Build.VERSION.SDK_INT < 23) {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.lister_reservation_step1);
                    if (Build.VERSION.SDK_INT < 23) {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow));
                    } else {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeYellow, null));
                    }
                }
                break;
            case "RS":
                if (currentDate.after(item.getReservationStartDate())) {
                    stepImageView.setImageResource(R.drawable.lister_reservation_step4_1);
                    if (Build.VERSION.SDK_INT < 23) {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                    } else {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.lister_reservation_step2);
                    if (Build.VERSION.SDK_INT < 23) {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "PS":
                if (currentDate.after(item.getReservationStartDate())) {
                    if (currentDate.after(item.getReservationEndDate())) {
                        stepImageView.setImageResource(R.drawable.lister_reservation_step4_2);
                        if (Build.VERSION.SDK_INT < 23) {
                            priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                        } else {
                            priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                        }
                    } else {
                        stepImageView.setImageResource(R.drawable.lister_reservation_step3);
                        if (Build.VERSION.SDK_INT < 23) {
                            priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue));
                        } else {
                            priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeBlue, null));
                        }
                    }
                } else {
                    stepImageView.setImageResource(R.drawable.lister_reservation_step2_2);
                    if (Build.VERSION.SDK_INT < 23) {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed));
                    } else {
                        priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeRed, null));
                    }
                }
                break;
            case "RC":
            case "PC":
                stepImageView.setImageResource(R.drawable.lister_reservation_step4_1);
                if (Build.VERSION.SDK_INT < 23) {
                    priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray));
                } else {
                    priceTextView.setTextColor(MyApplication.getmContext().getResources().getColor(R.color.bikeeLightGray, null));
                }
                break;
            default:
                break;
        }

        renterNameTextView.setText(item.getRenterName());
        bicycleTitleTextView.setText(item.getBicycleTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd. HH:mm", java.util.Locale.getDefault());
        startDateTextView.setText(simpleDateFormat.format(item.getReservationStartDate()));
        endDateTextView.setText(simpleDateFormat.format(item.getReservationEndDate()));
        totalPeriodTextView.setText(
                RefinementUtil.calculateRentPeriod(
                        item.getReservationStartDate(),
                        item.getReservationEndDate()
                )
        );
        int unDecimalPointedPrice =
                RefinementUtil.calculatePrice(
                        item.getReservationStartDate(),
                        item.getReservationEndDate(),
                        item.getPricePerMonth(),
                        item.getPricePerDay(),
                        item.getPricePerHour()
                );
        String decimalPointedPrice = unDecimalPointedPrice == 0 ? "0" : decimalFormat.format(
                Long.parseLong(unDecimalPointedPrice + "")
        );
        priceTextView.setText(
                decimalPointedPrice + "원"
        );
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }
}
