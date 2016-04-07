package com.example.tacademy.bikee.etc;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.facebook.FacebookSdk;
import com.tsengvn.typekit.Typekit;

/**
 * Created by User on 2015-11-09.
 */
public class MyApplication extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;
        Glide.get(mContext).setMemoryCategory(MemoryCategory.HIGH);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansKR-Regular.otf"));
        FacebookSdk.sdkInitialize(this);
    }

    public static Context getmContext() {
        return mContext;
    }
}
