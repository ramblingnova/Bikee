package com.example.tacademy.bikee.etc;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.Result;

import java.io.File;
import java.util.List;

/**
 * Created by User on 2015-11-10.
 */
public class Util {
    public static void setRectangleImageFromImageURL(Context context, String imageURL, ImageView targetView) {
        Glide.with(context).load(imageURL).thumbnail(0.0001f).centerCrop().into(targetView);
    }

    public static void setRectangleImageFromURI(Context context, String uri, ImageView targetView) {
        Glide.with(context).load(uri).thumbnail(0.0001f).centerCrop().into(targetView);
    }

    public static void setCircleImageFromURL(final Context context, String imageURL, int imageSize, final ImageView targetVIew) {
        Glide.with(context).load(imageURL).asBitmap().placeholder(R.drawable.noneimage).fitCenter().thumbnail(0.0001f).into(new BitmapImageViewTarget(targetVIew) {
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
        Glide.with(context).load(imageURL).asBitmap().placeholder(R.drawable.detailpage_bike_image_noneimage).fitCenter().thumbnail(0.0001f).into(new BitmapImageViewTarget(targetView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(radius);
                targetView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setRoundRectangleImageFromFile(final Context context, int placeHolderResourceId, File file, final float radius, final ImageView targetView) {
        Glide.with(context).load(file).asBitmap().placeholder(placeHolderResourceId).fitCenter().thumbnail(0.0001f).into(new BitmapImageViewTarget(targetView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(radius);
                targetView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public final static String REGEX_HANGUL = "^[가-힣]{2,30}$";
    public final static String REGEX_ADDRESS = "[[가-힣]*[0-9]*[가-힣]*[0-9]*\\s?]$";

    public static String getBicycleImageURL(Result result) {
        if ((null == result.getImage())
                || (null == result.getImage().getCdnUri())
                || (null == result.getImage().getFiles())
                || (0 >= result.getImage().getFiles().size())) {
            return "";
        } else {
            return result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0);
        }
    }

    public static String getUserImageURL(Result result) {
        if ((null == result.getUser().getImage())
                || (null == result.getUser().getImage().getCdnUri())
                || (null == result.getUser().getImage().getFiles())
                || (0 >= result.getUser().getImage().getFiles().size())) {
            return "";
        } else {
            return result.getUser().getImage().getCdnUri() + "/detail_" + result.getUser().getImage().getFiles().get(0);
        }
    }

    public static String getUserImageURL(Comment comment) {
        if ((null == comment.getWriter().getImage())
                || (null == comment.getWriter().getImage().getCdnUri())
                || (null == comment.getWriter().getImage().getFiles())
                || (0 >= comment.getWriter().getImage().getFiles().size())) {
            return "";
        } else {
            return comment.getWriter().getImage().getCdnUri() + "/detail_" + comment.getWriter().getImage().getFiles().get(0);
        }
    }

    public static List<String> getComponents(Result result) {
        if ((null == result.getComponents())
                || (0 >= result.getComponents().size())) {
            return null;
        } else {
            return result.getComponents();
        }
    }
}
