package com.bikee.www.renter.searchresult.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikee.www.common.interfaces.OnViewHolderClickListener;
import com.bikee.www.R;
import com.bikee.www.etc.MyApplication;
import com.bikee.www.etc.utils.ImageUtil;
import com.bikee.www.renter.searchresult.SearchResultItem;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_search_result_item_bicycle_picture_image_view)
    ImageView bicycle_picture;
    @Bind(R.id.view_search_result_item_bicycle_name_text_view)
    TextView bicycle_name;
    @Bind(R.id.view_search_result_item_height_text_view)
    TextView height_text;
    @Bind(R.id.view_search_result_item_height_real_text_view)
    TextView height;
    @Bind(R.id.view_search_result_item_type_text_view)
    TextView type_text;
    @Bind(R.id.view_search_result_item_type_real_text_view)
    TextView type;
    @Bind(R.id.view_search_result_item_payment_real_text_view)
    TextView payment;
    @Bind(R.id.view_search_result_item_per_duration_text_view)
    TextView perDuration;
    @Bind(R.id.view_search_result_item_distance_text_view)
    TextView distance;

    private OnViewHolderClickListener onViewHolderClickListener;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

    public SearchResultViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_search_result)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    public void setView(SearchResultItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicycle_picture,
                MyApplication.getmContext()
                        .getResources()
                        .getDimension(
                                R.dimen.view_search_result_item_bicycle_picture_image_view_round_radius
                        )
        );
        bicycle_name.setText(item.getBicycle_name().toString());
        String heightString = "~145cm";
        switch (item.getHeight().toString()) {
            case "01":
                heightString = "~145cm";
                break;
            case "02":
                heightString = "145cm~155cm";
                break;
            case "03":
                heightString = "155cm~165cm";
                break;
            case "04":
                heightString = "165cm~175cm";
                break;
            case "05":
                heightString = "175cm~185cm";
                break;
            case "06":
                heightString = "185cm~";
                break;
        }
        height.setText(heightString);
        String typeString = "보급형";
        switch (item.getType().toString()) {
            case "A":
                typeString = "보급형";
                break;
            case "B":
                typeString = "산악용";
                break;
            case "C":
                typeString = "하이브리드";
                break;
            case "D":
                typeString = "픽시";
                break;
            case "E":
                typeString = "폴딩";
                break;
            case "F":
                typeString = "미니벨로";
                break;
            case "G":
                typeString = "전기자전거";
                break;
        }
        type.setText(typeString);
        payment.setText(
                decimalFormat.format(
                        Long.parseLong(item.getPayment())
                ) + "원"
        );
        distance.setText((Math.round(item.getDistance() * 10.0) / 10.0) + "km");
    }
}
