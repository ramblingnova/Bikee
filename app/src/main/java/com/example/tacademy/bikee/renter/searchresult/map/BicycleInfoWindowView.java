package com.example.tacademy.bikee.renter.searchresult.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.POI;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.manager.FontManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultMapItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-18.
 */
public class BicycleInfoWindowView extends FrameLayout implements GoogleMap.InfoWindowAdapter {
    OnImageLoadListener onImageLoadListener;
    private View infoWindow;
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
    Map<Marker, POI> mPOIResolver;

    public BicycleInfoWindowView(Context context, Map<Marker, POI> poiResolver) {
        super(context);
        infoWindow = inflate(getContext(), R.layout.view_bicycle_info_window, this);
        ButterKnife.bind(this);
        FontManager.getInstance().setTextViewFont(FontManager.NOTO, bicycle_name, type_text, type, height_text, height, payment_text, payment, perDuration);
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
        Glide.with(context).load(imageURL).asBitmap().placeholder(R.drawable.detailpage_bike_image_noneimage).fitCenter().thumbnail(0.0001f).into(new BitmapImageViewTarget(bicycleImage) {
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
        });
    }

    public void setView(SearchResultMapItem item) {
        Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), item.getImageURL(), 12, bicycleImage);
        bicycle_name.setText(item.getBicycle_name().toString());
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
        payment.setText(item.getPayment().toString());
    }
}
