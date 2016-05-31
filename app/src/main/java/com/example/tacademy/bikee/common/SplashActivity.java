package com.example.tacademy.bikee.common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.Facebook;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
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
import com.sendbird.android.SendBird;

import java.io.IOException;
import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private String deviceID;
    private String registrationID;
    private String deviceName;
    private String deviceOS = "Android";
    private CallbackManager callbackManager;
    private LoginManager mLoginManager;
    private AccessTokenTracker mTokenTracker;
    private final String appId = "2E377FE1-E1AD-4484-A66F-696AF1306F58";
    private String userId;
    private String userName;
    private String gcmRegToken;

    private static final String TAG = "SPLASH_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT < 15) {
            // TODO : android api 버전 15이하는 걸러야 함, 메시지와 함께 앱 종료?
        } else if ((Build.VERSION.SDK_INT >= 15)
                && (Build.VERSION.SDK_INT < 23)) {
            afterPermissionCheck();
        } else if (Build.VERSION.SDK_INT >= 23) {
            // TODO : android api 버전 23이상은 (필요한 권한 xor 모든 권한)을 체크해야 함
            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(SplashActivity.this, "AA", Toast.LENGTH_SHORT).show();

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                        || shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    // 거절한 경우
                    Toast.makeText(SplashActivity.this, "BB", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + "com.example.tacademy.bikee"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivityForResult(intent, 10);
                } else {
                    // 최초
                    Toast.makeText(SplashActivity.this, "CC", Toast.LENGTH_SHORT).show();

                    requestPermissions(
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            },
                            10
                    );
                }
            } else {
                // 승인된 경우
                afterPermissionCheck();
            }
        }
    }

    int a = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(SplashActivity.this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();
        if (requestCode == 10)
            afterPermissionCheck();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(SplashActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
        if (requestCode == 10)
            afterPermissionCheck();
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTokenTracker != null)
            mTokenTracker.stopTracking();
    }

    private void afterPermissionCheck() {
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

    private void signInFacebook() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                final AccessToken token = AccessToken.getCurrentAccessToken();
                if (token != null) {
                    String facebookId = PropertyManager.getInstance().getFacebookId();
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
                                            Facebook facebook = new Facebook();
                                            facebook.setAccess_token(token.getToken());
                                            NetworkManager.getInstance().signInFacebook(
                                                    facebook,
                                                    null,
                                                    new Callback<ReceiveObject>() {
                                                        @Override
                                                        public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                                            if (BuildConfig.DEBUG)
                                                                Log.d(TAG, "Success send token to server...");
                                                            afterSignIn();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                            if (BuildConfig.DEBUG)
                                                                Log.d(TAG, "onFailure Error : " + t.toString());
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
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "login onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                        afterSignIn();
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    public void afterSignIn() {
        // TODO : SignInActivity에서 로그인 후에 _id를 저장했다면, 여기서 receiveProfile을 호출할 필요가 없는 것으로 판단됨
        NetworkManager.getInstance().receiveProfile(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();

                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg());

                        /* send gcm token to server */
                        checkPlayService();
                        getDeviceID();
                        getDeviceName();
                        getRegistrationID();
                        new RequestTokenThread().start();
                        sendGCMToken();

                        /* property initialization */
                        for (Result result : receiveObject.getResult())
                            if (result.get_id() != null) {
                                PropertyManager.getInstance().set_id(result.get_id());
                                PropertyManager.getInstance().setName(result.getName());
                                userId = result.get_id();
                                userName = result.getName();
                                break;
                            }
                        gcmRegToken = PropertyManager.getInstance().getGCMToken();

                        /* SendBird initialization and login */
                        SendBird.init(SplashActivity.this, appId);
                        SendBird.login(SendBird.LoginOption.build(userId).setUserName(userName).setGCMRegToken(gcmRegToken));

                        /* close splash */
                        closeSplash();
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
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
                if (!registrationID.equals(token)) {
                    registrationID = token;
                    saveRegistrationID();
                    sendGCMToken();
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
                    + "\ndeviceOS : " + deviceID);
        NetworkManager.getInstance().sendGCMToken(
                deviceID,
                registrationID,
                deviceOS,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "sendGCMToken onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
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
        }, 1000);
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
