package com.example.tacademy.bikee.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {
    static final private String TAG = "SPLASH_ACTIVITY";
    private String deviceID;
    private String registrationID;
    private String deviceName;
    private String deviceOS = "Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PropertyManager.getInstance().getInit().equals("")) {
            PropertyManager.getInstance().setLatitude("37.565596");
            PropertyManager.getInstance().setLongitude("126.978013");
            PropertyManager.getInstance().setInit("false");
        }

        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getPassword().equals("")) {
            NetworkManager.getInstance().login(
                    PropertyManager.getInstance().getEmail(),
                    PropertyManager.getInstance().getPassword(),
                    new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                                + ", Code : " + receiveObject.getCode()
                                                + ", Msg : " + receiveObject.getMsg()
                                );

                            // TODO : need GCM test
                            checkPlayService();
                            resolveDeviceID();
                            resolveDeviceName();
                            new RequestTokenThread().start();
                            registerToken();
                            showStoredToken();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onFailure Error : " + error.toString());
                        }
                    });
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "End of code!");
    }

    void checkPlayService() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "isGooglePlayServicesAvailable : " + resultCode);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Google Paly Service is available to bikee!");
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }
    }

    private void resolveDeviceID() {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Android ID : " + androidID);

        deviceID = androidID;
    }

    private void resolveDeviceName() {
        deviceName = Build.DEVICE;
    }

    class RequestTokenThread extends Thread {
        @Override
        public void run() {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Trying to register device");
            try {
                InstanceID instanceID = InstanceID.getInstance(SplashActivity.this);
                final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                if (registrationID != token) {
                    registrationID = token;
                    saveRegistrationID();
                }
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Token : " + token);
            } catch (IOException e) {
                e.printStackTrace();
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Register Exception", e);
            }
        }
    }

    void registerToken() {
        if (deviceID == null)
            resolveDeviceID();
        if (deviceName == null)
            resolveDeviceName();

        NetworkManager.getInstance().registerToken(deviceID, registrationID, deviceOS, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );

            }

            @Override
            public void failure(RetrofitError error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onFailure Error : " + error.toString());
            }
        });
    }

    void showStoredToken() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String storedToken = sharedPref.getString("REGISTRATION_ID", null);
        if (storedToken != null) {
            registrationID = storedToken;
        }
    }

    void saveRegistrationID() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("REGISTRATION_ID", registrationID);
        editor.commit();
    }
}
