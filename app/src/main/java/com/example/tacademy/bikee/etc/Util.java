package com.example.tacademy.bikee.etc;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tacademy.bikee.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 2015-11-10.
 */
public class Util {
    public static void setRectangleImageFromImageURL(Context context, String imageURL, ImageView targetView) {
       Glide.with(context).load(imageURL).thumbnail(0.0001f).centerCrop().into(targetView);
       // Glide.with(context).load(imageURL).sizeMultiplier(0.5f).into(targetView);
    }

    public static void setCircleImageFromURL(final Context context, String imageURL, int imageSize, final ImageView targetVIew) {
        Glide.with(context).load(imageURL).asBitmap().placeholder(R.drawable.temp_icon).fitCenter().thumbnail(0.001f).into(new BitmapImageViewTarget(targetVIew) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                targetVIew.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setRoundRectangleImageFromURL(final Context context, String imageURL, final float radius, final ImageView targetView) {
        Glide.with(context).load(imageURL).asBitmap().placeholder(R.drawable.temp_icon).fitCenter().thumbnail(0.001f).into(new BitmapImageViewTarget(targetView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(radius);
                targetView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    // imageVIew Scaltype
    // matrix, center, centerCrop, centerInside,
    // fitStart, fitCenter, fitEnd, fitXY
//        ImageLoader loader;
//        loader = ImageLoader.getInstance();
//        loader.displayImage("http://www.hdwallpapers.in/walls/avengers_age_of_ultron_2015_movie-wide.jpg", iv);
//        Glide.with(getContext()).load("http://www.hdwallpapers.in/walls/avengers_age_of_ultron_2015_movie-wide.jpg").thumbnail(0.0001f).centerCrop().into(iv);
}
