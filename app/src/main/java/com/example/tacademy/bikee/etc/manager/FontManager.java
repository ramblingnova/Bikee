package com.example.tacademy.bikee.etc.manager;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by User on 2015-11-16.
 */
public class FontManager {
    private static FontManager instance;

    private static Object obj = new Object();

    public static FontManager getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new FontManager();
            }
        }
        return instance;
    }

    Typeface nanum, noto, roboto;

    public static final String NOTO = "noto";

    private FontManager() {

    }

    public Typeface getTypeface(Context context, String fontName) {
        if (NOTO.equals(fontName)) {
            if (noto == null) {
                noto = Typeface.createFromAsset(context.getAssets(), "NotoSansKR-Regular.otf");
            }
            return noto;
        }
        return null;
    }
}
