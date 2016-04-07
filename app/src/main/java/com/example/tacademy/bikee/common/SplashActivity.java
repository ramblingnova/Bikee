package com.example.tacademy.bikee.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.FacebookNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.security.MessageDigest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {
    private String deviceID;
    private String registrationID;
    private String deviceName;
    private String deviceOS = "Android";
    private CallbackManager callbackManager;
    private LoginManager mLoginManager;
    private AccessTokenTracker mTokenTracker;

    private static final String TAG = "SPLASH_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PropertyManager.getInstance().isInitCoordinates()) {
            PropertyManager.getInstance().setLatitude("37.565596");
            PropertyManager.getInstance().setLongitude("126.978013");
            if (BuildConfig.DEBUG)
                Log.d(TAG, "is it init coordinates? " + PropertyManager.getInstance().isInitCoordinates()
                                + "\nlatitude : " + PropertyManager.getInstance().getLatitude()
                                + "\nlongitude : " + PropertyManager.getInstance().getLongitude()
                );
            PropertyManager.getInstance().setInitCoordinates(false);
        }

        switch (PropertyManager.getInstance().getSignInState()) {
            case PropertyManager.SIGN_IN_FACEBOOK_STATE:
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "SIGN_IN_FACEBOOK_STATE");
                callbackManager = CallbackManager.Factory.create();
                mLoginManager = LoginManager.getInstance();
                signInFacebook();
                break;
            case PropertyManager.SIGN_IN_LOCAL_STATE:
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "SIGN_IN_LOCAL_STATE");
                signInLocal();
                break;
            case PropertyManager.SIGN_OUT_STATE:
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "SIGN_OUT_STATE");
                closeSplash();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTokenTracker != null)
            mTokenTracker.stopTracking();
    }

    private void signInFacebook() {

        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                final AccessToken token = AccessToken.getCurrentAccessToken();
                String facebookId = PropertyManager.getInstance().getFacebookId();
                if (token != null) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "token.getToken : " + token.getToken()
                                + "\ntoken.getUserId : " + token.getUserId()
                                + "\nPropertyManager.getFacebookId : " + facebookId);
                    if (token.getUserId().equals(facebookId)) {
                        FacebookNetworkManager.getInstance().loginFacebookToken(
                                SplashActivity.this,
                                token.getToken(),
                                "OK",
                                new FacebookNetworkManager.OnResultListener<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if (result.equals("OK")) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "loginFacebookToken result : " + result);
                                            NetworkManager.getInstance().signInFacebook(token.getToken(), new Callback<ReceiveObject>() {
                                                @Override
                                                public void success(ReceiveObject receiveObject, Response response) {
                                                    if (BuildConfig.DEBUG)
                                                        Log.d(TAG, "Success send token to server...");
                                                    checkPlayService();
                                                    getDeviceID();
                                                    getDeviceName();
                                                    getRegistrationID();
                                                    new RequestTokenThread().start();
                                                    sendGCMToken();

                                                    closeSplash();
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    if (BuildConfig.DEBUG)
                                                        Log.d(TAG, "Failure send token to server...");
                                                    mLoginManager.logOut();

                                                    closeSplash();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFail(int code) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onFail");
                                    }
                                });
                    } else {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "Facebook id changed...");
                        mLoginManager.logOut();
                        closeSplash();
                    }
                }
            }
        };

        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onSuccess");
            }

            @Override
            public void onCancel() {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onError");
                closeSplash();
            }
        });

        mLoginManager.logInWithReadPermissions(this, null);
    }

    private void signInLocal() {
        NetworkManager.getInstance().login(
                PropertyManager.getInstance().getEmail(),
                PropertyManager.getInstance().getPassword(),
                new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "login onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                        checkPlayService();
                        getDeviceID();
                        getDeviceName();
                        getRegistrationID();
                        new RequestTokenThread().start();
                        sendGCMToken();

                        closeSplash();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "login onFailure Error...", error);
                        closeSplash();
                    }
                });
    }

    private void checkPlayService() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "isGooglePlayServicesAvailable : " + resultCode);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Google Play Service is available to bikee...");
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }
    }

    private void getDeviceID() {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Android ID : " + androidID);

        deviceID = androidID;
    }

    private void getDeviceName() {
        deviceName = Build.DEVICE;
    }

    private void getRegistrationID() {
        String storedToken = PropertyManager.getInstance().getGCMToken();
        if (!storedToken.equals(""))
            registrationID = storedToken;
    }

    private void saveRegistrationID() {
        PropertyManager.getInstance().setGCMToken(registrationID);
    }

    class RequestTokenThread extends Thread {
        @Override
        public void run() {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Trying to register device...");
            try {
                InstanceID instanceID = InstanceID.getInstance(SplashActivity.this);
                final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                if (registrationID != token) {
                    registrationID = token;
                    saveRegistrationID();
                }
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "GCM Token : " + token);
            } catch (IOException e) {
                e.printStackTrace();
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Get GCM Token Exception...", e);
            }
        }
    }

    private void sendGCMToken() {
        if (deviceID == null)
            getDeviceID();
        if (deviceName == null)
            getDeviceName();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "deviceID : " + deviceID
                            + "\nregistrationID(GCM Token) : " + registrationID
                            + "\ndeviceOS : " + deviceID
            );
        NetworkManager.getInstance().sendGCMToken(deviceID, registrationID, deviceOS, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "sendGCMToken onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );
            }

            @Override
            public void failure(RetrofitError error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "sendGCMToken onFailure Error...", error);
            }
        });
    }

    private void closeSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, "Hash Key : " + something);
            }
        } catch (Exception e) {
            Log.i(TAG, "name not found...", e);
        }
    }
}
