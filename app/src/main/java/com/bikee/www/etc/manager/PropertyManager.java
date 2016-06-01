package com.bikee.www.etc.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bikee.www.etc.MyApplication;

/**
 * Created by User on 2015-11-14.
 */
public class PropertyManager {
    private static PropertyManager instance;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private static final String KEY_SIGN_IN_STATE = "SIGN_IN_STATE";
    public static final int SIGN_OUT_STATE = 0;
    public static final int SIGN_IN_FACEBOOK_STATE = 1;
    public static final int SIGN_IN_LOCAL_STATE = 2;

    private static final String KEY_IMAGE = "IMAGE";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_PHONE = "PHONE";

    private static final String KEY_INIT_COORDINATES = "INIT_COORDINATES";
    private static final String KEY_LATITUDE = "LATITUDE";
    private static final String KEY_LONGITUDE = "LONGITUDE";

    private static final String KEY_PUSH = "PUSH";

    private static final String KEY_FACEBOOK_ID = "FACEBOOK_ID";

    private static final String KEY_GCM_TOKEN = "GCM_TOKEN";

    private static final String KEY_ID = "KEY_ID";

    public static PropertyManager getInstance() {
        if (instance == null)
            instance = new PropertyManager();

        return instance;
    }

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getmContext());
        mEditor = mPrefs.edit();
    }

    public void setSignInState(int state) {
        mEditor.putInt(KEY_SIGN_IN_STATE, state);
        mEditor.commit();
    }

    public int getSignInState() {
        return mPrefs.getInt(KEY_SIGN_IN_STATE, SIGN_OUT_STATE);
    }

    public void setImage(String image) {
        mEditor.putString(KEY_IMAGE, image);
        mEditor.commit();
    }

    public String getImage() {
        return mPrefs.getString(KEY_IMAGE, "");
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

    public void setPhone(String phone) {
        mEditor.putString(KEY_PHONE, phone);
        mEditor.commit();
    }

    public String getPhone() {
        return mPrefs.getString(KEY_PHONE, "");
    }

    public void setLatitude(String latitude) {
        mEditor.putString(KEY_LATITUDE, latitude);
        mEditor.commit();
    }

    public String getLatitude() {
        return mPrefs.getString(KEY_LATITUDE, "");
    }

    public void setLongitude(String longitude) {
        mEditor.putString(KEY_LONGITUDE, longitude);
        mEditor.commit();
    }

    public String getLongitude() {
        return mPrefs.getString(KEY_LONGITUDE, "");
    }

    public void setInitCoordinates(boolean initCoordinates) {
        mEditor.putBoolean(KEY_INIT_COORDINATES, initCoordinates);
        mEditor.commit();
    }

    public boolean isInitCoordinates() {
        return mPrefs.getBoolean(KEY_INIT_COORDINATES, true);
    }

    public void setPushEnable(boolean push) {
        mEditor.putBoolean(KEY_PUSH, push);
        mEditor.commit();
    }

    public boolean isPushEnable() {
        return mPrefs.getBoolean(KEY_PUSH, true);
    }

    public void setFacebookId(String facebookId) {
        mEditor.putString(KEY_FACEBOOK_ID, facebookId);
        mEditor.commit();
    }

    public String getFacebookId() {
        return mPrefs.getString(KEY_FACEBOOK_ID, "");
    }

    public void setGCMToken(String token) {
        mEditor.putString(KEY_GCM_TOKEN, token);
        mEditor.commit();
    }

    public String getGCMToken() {
        return mPrefs.getString(KEY_GCM_TOKEN, "");
    }

    public void set_id(String _id) {
        mEditor.putString(KEY_ID, _id);
        mEditor.commit();
    }

    public String get_id() {
        return mPrefs.getString(KEY_ID, "");
    }
}
