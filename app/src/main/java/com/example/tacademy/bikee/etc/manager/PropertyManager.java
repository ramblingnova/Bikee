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

    public static final String KEY_EMAIL = "id";
    public static final String KEY_PASSWORD = "password";

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

    public boolean isBackupSync() {
        return mPrefs.getBoolean("perf_sync", false);
    }
}
