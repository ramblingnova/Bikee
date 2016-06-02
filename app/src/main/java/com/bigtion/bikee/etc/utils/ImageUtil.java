package com.bigtion.bikee.etc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.R;

/**
 * Created by User on 2015-11-10.
 */
public class ImageUtil {
    public static void setCircleImageFromURL(
            final Context context,
            String imageURL,
            final int placeHolderResourceId,
            final ImageView targetView) {
        Glide.with(context)
                .load(imageURL)
                .asBitmap()
                .placeholder(placeHolderResourceId)
                .fitCenter()
                .thumbnail(0.0001f)
                .into(new BitmapImageViewTarget(targetView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        targetView.setImageDrawable(circularBitmapDrawable);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        BitmapDrawable bitmapDrawable;
                        if (Build.VERSION.SDK_INT < 23)
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId);
                        else
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId, MyApplication.getmContext().getTheme());
                        if ((bitmapDrawable != null)
                                && (bitmapDrawable.getBitmap() != null)) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), bitmapDrawable.getBitmap());
                            circularBitmapDrawable.setCircular(true);
                            targetView.setImageDrawable(circularBitmapDrawable);
                        }
                    }
                });
    }

    public static void setRectangleImageFromURL(
            final Context context,
            String imageURL,
            final int placeHolderResourceId,
            final ImageView targetView) {
        Glide.with(context)
                .load(imageURL)
                .asBitmap()
                .placeholder(placeHolderResourceId)
//                .centerCrop() vs .fitCenter()
                .fitCenter()
                .thumbnail(0.0001f)
                .into(new BitmapImageViewTarget(targetView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        targetView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        BitmapDrawable bitmapDrawable;
                        if (Build.VERSION.SDK_INT < 23)
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId);
                        else
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId, MyApplication.getmContext().getTheme());
                        if ((bitmapDrawable != null)
                                && (bitmapDrawable.getBitmap() != null)) {
                            targetView.setImageDrawable(bitmapDrawable);
                        }
                    }
                });
    }

    public static void setRoundRectangleImageFromURL(
            final Context context,
            String imageURL,
            final int placeHolderResourceId,
            final ImageView targetView,
            final float radius) {
        Glide.with(context)
                .load(imageURL)
                .asBitmap()
                .placeholder(placeHolderResourceId)
                .fitCenter()
                .thumbnail(0.0001f)
                .into(new BitmapImageViewTarget(targetView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        roundedBitmapDrawable.setCornerRadius(radius);
                        targetView.setImageDrawable(roundedBitmapDrawable);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        BitmapDrawable bitmapDrawable;
                        if (Build.VERSION.SDK_INT < 23) {
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId);
                        } else {
                            bitmapDrawable = (BitmapDrawable) context.getResources()
                                    .getDrawable(placeHolderResourceId, MyApplication.getmContext().getTheme());
                        }
                        RoundedBitmapDrawable roundedBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), bitmapDrawable.getBitmap());
                        roundedBitmapDrawable.setCornerRadius(radius);
                        targetView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    public static void initBicycleImageViewPagerIndicators(Context context, int size, RelativeLayout targetLayout) {
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(R.id.indicator + (size - 1 - i));
            if (i == (size - 1))
                imageView.setImageResource(R.drawable.detailpage_image_scroll_icon_b);
            else
                imageView.setImageResource(R.drawable.detailpage_image_scroll_icon_w);

            int width = (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_indicator_width);
            int height = (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_indicator_height);
            int top_margin = (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_indicator_top_margin);
            int right_margin;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            if (i == 0)
                right_margin = (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_first_indicator_right_margin);
            else
                right_margin = (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_first_indicator_right_margin)
                        + i * (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_indicator_width)
                        + i * (int) context.getResources().getDimension(R.dimen.bicycle_image_view_pager_other_indicator_right_margin);
            layoutParams.setMargins(0, top_margin, right_margin, 0);

            imageView.setLayoutParams(layoutParams);

            targetLayout.addView(imageView);
        }
    }

    public static void initCardViewPagerIndicators(Context context, int size, RelativeLayout targetLayout) {
        RelativeLayout indicatorsLayout = new RelativeLayout(context);

        RelativeLayout.LayoutParams indicatorsLayoutParams
                = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        indicatorsLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, targetLayout.getId());
        indicatorsLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        int bottomMargin = (int) context.getResources().getDimensionPixelSize(R.dimen.card_view_pager_indicator_bottom_margin);
        indicatorsLayoutParams.setMargins(0, 0, 0, bottomMargin);

        indicatorsLayout.setLayoutParams(indicatorsLayoutParams);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(R.id.indicator + i);
            if (i == 0)
                imageView.setImageResource(R.drawable.scroll_blue);
            else
                imageView.setImageResource(R.drawable.scroll_black);

            int width = (int) context.getResources().getDimensionPixelSize(R.dimen.card_view_pager_indicator_width);
            int height = (int) context.getResources().getDimensionPixelSize(R.dimen.card_view_pager_indicator_height);

            RelativeLayout.LayoutParams indicatorParams = new RelativeLayout.LayoutParams(width, height);
            int leftMargin;
            if (i != 0)
                leftMargin = i * (int) context.getResources().getDimensionPixelSize(R.dimen.card_view_pager_indicator_left_margin)
                        + i * (int) context.getResources().getDimensionPixelSize(R.dimen.card_view_pager_indicator_width);
            else
                leftMargin = 0;
            indicatorParams.setMargins(leftMargin, 0, 0, 0);

            imageView.setLayoutParams(indicatorParams);

            indicatorsLayout.addView(imageView);
        }

        targetLayout.addView(indicatorsLayout);
    }
}
