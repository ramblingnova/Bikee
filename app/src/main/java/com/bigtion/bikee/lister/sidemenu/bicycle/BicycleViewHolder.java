package com.bigtion.bikee.lister.sidemenu.bicycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigtion.bikee.common.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.etc.utils.ImageUtil;
import com.bigtion.bikee.R;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class BicycleViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_owning_bicycle_item_bicycle_picture_image_view)
    ImageView bicycleImage;
    @Bind(R.id.view_owning_bicycle_item_bicycle_name_text_view)
    TextView nameTextView;
    @Bind(R.id.view_owning_bicycle_item_register_date_text_view)
    TextView dateTextView;

    private OnViewHolderClickListener onViewHolderClickListener;

    public BicycleViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.view_holder_owning_bicycle)
    void onClick(View view) {
        if (onViewHolderClickListener != null)
            onViewHolderClickListener.onViewHolderClick(view);
    }

    public void setView(BicycleItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicycleImage,
                MyApplication.getmContext()
                        .getResources()
                        .getDimension(
                                R.dimen.view_owning_bicycle_item_bicycle_picture_image_view_round_radius
                        )
        );
        nameTextView.setText("" + item.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
        dateTextView.setText(simpleDateFormat.format(item.getDate()));
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }
}
