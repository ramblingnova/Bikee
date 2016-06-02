package com.bikee.www.renter.searchresult.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bikee.www.renter.searchresult.SearchResultItem;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.etc.MyApplication;
import com.bikee.www.etc.utils.ImageUtil;
import com.bikee.www.etc.manager.FontManager;
import com.bikee.www.etc.utils.RefinementUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.DecimalFormat;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-18.
 */
public class BicycleInfoWindowView extends FrameLayout implements GoogleMap.InfoWindowAdapter {
    @Bind(R.id.view_bicycle_info_window_bicycle_picture_image_view)
    ImageView bicycleImage;
    @Bind(R.id.view_bicycle_info_window_bicycle_name_text_view)
    TextView bicycle_name;
    @Bind(R.id.view_bicycle_info_window_bicycle_type_text_view)
    TextView type_text;
    @Bind(R.id.view_bicycle_info_window_bicycle_type_real_text_view)
    TextView type;
    @Bind(R.id.view_bicycle_info_window_bicycle_height_text_view)
    TextView height_text;
    @Bind(R.id.view_bicycle_info_window_bicycle_height_real_text_view)
    TextView height;
    @Bind(R.id.view_bicycle_info_window_bicycle_payment_text_view1)
    TextView payment_text;
    @Bind(R.id.view_bicycle_info_window_bicycle_payment_real_text_view)
    TextView payment;
    @Bind(R.id.view_bicycle_info_window_bicycle_payment_text_view2)
    TextView perDuration;

    private OnImageLoadListener onImageLoadListener;
    private View infoWindow;
    private Map<Marker, POI> mPOIResolver;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

    public BicycleInfoWindowView(Context context, Map<Marker, POI> poiResolver) {
        super(context);
        infoWindow = inflate(getContext(), R.layout.view_bicycle_info_window, this);
        ButterKnife.bind(this);
        FontManager.getInstance().setTextViewFont(
                FontManager.NOTO,
                bicycle_name,
                type_text,
                type,
                height_text,
                height,
                payment_text,
                payment,
                perDuration
        );
        mPOIResolver = poiResolver;
    }

    public static BicycleInfoWindowView getInstance(Context context, Map<Marker, POI> poiResolver) {
        return new BicycleInfoWindowView(context, poiResolver);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        POI poi = mPOIResolver.get(marker);
        setView(poi.getItem());
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public interface OnImageLoadListener {
        void onImageLoad();
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public void setImageView(final Context context, String imageURL) {
        Glide.with(context)
                .load(imageURL)
                .asBitmap()
                .placeholder(R.drawable.detailpage_bike_image_noneimage)
                .fitCenter()
                .thumbnail(0.0001f)
                .into(new BitmapImageViewTarget(bicycleImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(12);
                        bicycleImage.setImageDrawable(circularBitmapDrawable);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        if (onImageLoadListener != null) {
                            onImageLoadListener.onImageLoad();
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (BuildConfig.DEBUG)
                            Log.d("BICYCLE_INFO_WINDOW", "onLoadFailed...");
                        if (onImageLoadListener != null) {
                            onImageLoadListener.onImageLoad();
                        }
                    }
                });
    }

    public void setView(SearchResultItem item) {
        ImageUtil.setRoundRectangleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.detailpage_bike_image_noneimage,
                bicycleImage,
                MyApplication.getmContext()
                        .getResources()
                        .getDimension(
                                R.dimen.view_bicycle_info_window_bicycle_picture_image_view_round_radius
                        )
        );
        bicycle_name.setText(item.getBicycle_name());
        type.setText(RefinementUtil.getBicycleTypeStringFromBicycleType(item.getType()));
        height.setText(RefinementUtil.getBicycleHeightStringFromBicycleHeight(item.getHeight()));
        payment.setText(
                decimalFormat.format(
                        Long.parseLong(item.getPayment())
                ).toString()
        );
    }
}
