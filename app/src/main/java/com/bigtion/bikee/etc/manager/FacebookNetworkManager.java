package com.bigtion.bikee.etc.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by User on 2016-03-30.
 */
public class FacebookNetworkManager {
    private static FacebookNetworkManager instance;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static FacebookNetworkManager getInstance () {
        if (instance == null) {
            instance = new FacebookNetworkManager();
        }
        return instance;
    }

    private FacebookNetworkManager() {

    }

    public interface OnResultListener<T> {
        void onSuccess(T result);
        void onFail(int code);
    }

    public void loginFacebookToken(Context context, String accessToken, final String result , final OnResultListener<String> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(result);
            }
        }, 1000);
    }

    public void signupFacebook(Context context, String message, final OnResultListener<String> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess("OK");
            }
        }, 1000);
    }
}
