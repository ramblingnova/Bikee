package com.example.tacademy.bikee.etc;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tacademy.bikee.R;

/**
 * Created by User on 2015-11-10.
 */
public class Util {
    public static void setRectangleImageFromImageURL(Context context, String imageURL, ImageView targetView) {
        Glide.with(context).load(imageURL).thumbnail(0.0001f).centerCrop().into(targetView);
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

    // TODO : 이름 정규식 체크
    public static String checkClientNameInputValidation(String name) {
        return (null == name) ? "이름을 입력해주세요" : name;
    }

    // TODO : 이메일 정규식 체크
    public static String checkClientEmailInputValidation(String email) {
        return (null == email) ? "이메일 주소를 입력해주세요" : email;
    }

    // TODO : 핸드폰번호 정규식 체크
    public static String checkClientPhoneInputValidation(String phone) {
        return (null == phone) ? "핸드폰 번호를 입력해주세요" : phone;
    }

    // TODO : 인증번호 정규식 체크
    public static String checkClientAuthenticationInputValidation(String authNum) {
        return (null == authNum) ? "잘못된 인증번호 형식입니다" : authNum;
    }

    // TODO : 비밀번호 정규식 체크
    public static String checkClientPasswordInputValidation(String password) {
        return (null == password) ? "비밀번호를 입력해주세요" : password;
    }
}
