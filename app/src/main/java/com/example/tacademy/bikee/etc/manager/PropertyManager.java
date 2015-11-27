package com.example.tacademy.bikee.etc.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tacademy.bikee.etc.MyApplication;

/**
 * Created by User on 2015-11-14.
 */
public class PropertyManager {
    private static PropertyManager instance;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "id";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getmContext());
        mEditor = mPrefs.edit();
    }

    public void setName(String name) {
        mEditor.putString(KEY_NAME, name);
        mEditor.commit();
    }

    public String getName() {
        return mPrefs.getString(KEY_NAME, "");
    }

    public void setEmail(String email) {
        mEditor.putString(KEY_EMAIL, email);
        mEditor.commit();
    }

    public String getEmail() {
        return mPrefs.getString(KEY_EMAIL, "");
    }

    public void setPassword(String password) {
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(KEY_PASSWORD, "");
    }

    public void setLatitude(String latitude) {
        mEditor.putString(KEY_LATITUDE, latitude);
        mEditor.commit();
    }

    public String getLatitude() {
        return mPrefs.getString(KEY_LATITUDE, "37.565596");
    }

    public void setLongitude(String longitude) {
        mEditor.putString(KEY_LONGITUDE, longitude);
        mEditor.commit();
    }

    public String getLongitude() {
        return mPrefs.getString(KEY_LONGITUDE, "126.978013");
    }

    public boolean isBackupSync() {
        return mPrefs.getBoolean("perf_sync", false);
    }
}
