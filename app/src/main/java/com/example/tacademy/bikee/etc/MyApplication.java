package com.example.tacademy.bikee.etc;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tsengvn.typekit.Typekit;
//import com.tsengvn.typekit.Typekit;

/**
 * Created by User on 2015-11-09.
 */
public class MyApplication extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;
        Glide.get(mContext).setMemoryCategory(MemoryCategory.HIGH);
        initImageLoader(this);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansKR-Regular.otf"));
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.temp_icon)
                .showImageForEmptyUri(R.drawable.temp_icon)
                .showImageOnFail(R.drawable.temp_icon)
                .cacheInMemory(true)
                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
