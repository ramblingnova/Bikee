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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {
    static final private String TAG = "GCM_Example";
    private Handler handler;
    private RequestQueue mQueue;
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
                    Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                    + ", Code : " + receiveObject.getCode()
                                    + ", Msg : " + receiveObject.getMsg()
                    );

                    // TODO : get GCM token
                    mQueue = Volley.newRequestQueue(SplashActivity.this);
                    handler = new Handler();
                    checkPlayService();
                    resolveDeviceID();
                    resolveDeviceName();
                    new RequestTokenThread().start();
                    registerToken();
                    showStoredToken();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error", "onFailure Error : " + error.toString());
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
        }, 5000);
    }

    void checkPlayService() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        Log.d(TAG, "isGooglePlayServicesAvailable : " + resultCode);
        if (ConnectionResult.SUCCESS == resultCode) {
            // 구글 플레이 서비스 가능
            Toast.makeText(this, "플레이 서비스 사용 가능", Toast.LENGTH_SHORT).show();
        } else {
            // 구글 플레이 서비스 불가능 - 설치/업데이트 다이얼로그 출력
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 0).show();
        }
    }

    // 디바이스 아이디 가져와 저장하는 함수
    private void resolveDeviceID() {
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        deviceIDLabel.setText(androidID);
        Log.d(TAG, "Android ID : " + androidID);

        deviceID = androidID;
    }

    // 디바이스 기기명을 가져와 저장하는 함수
    private void resolveDeviceName() {
        deviceName = Build.DEVICE;
    }

    class RequestTokenThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "Trying to regist device");
            try {
                InstanceID instanceID = InstanceID.getInstance(SplashActivity.this);
                final String token = instanceID.getToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                if (registrationID != token) {
                    registrationID = token;
                    saveRegistrationID();
                }

                Log.d(TAG, "Token : " + token);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        MainActivity.this.registrationIDLabel.setText(token);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Regist Exception", e);
            }
        }
    }

    void registerToken() {
        // Device ID가 없으면 발급
        if (deviceID == null)
            resolveDeviceID();
        // Device Name이 없으면 발급
        if (deviceName == null)
            resolveDeviceName();

        StringRequest request = new StringRequest(Request.Method.POST, NetworkManager.ServerUrl.baseUrl + "/register", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Token 등록 결과  : " + response);
                Toast.makeText(SplashActivity.this, "Token 등록 성공", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error", error);
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    Log.e(TAG, "Error Response : " + response.statusCode);
                    Toast.makeText(SplashActivity.this, "Token 등록 에러 " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 바디 작성
                Map<String, String> params = new HashMap<>();
                params.put("deviceID", deviceID);
                params.put("token", registrationID);
                params.put("os", deviceOS);

                return params;
            }

            @Override
            public String getBodyContentType() {
                // 컨텐트 타입
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        mQueue.add(request);
    }

    void showStoredToken() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String storedToken = sharedPref.getString("REGISTRATION_ID", null);
        if (storedToken != null) {
            registrationID = storedToken;
//            registrationIDLabel.setText(registrationID);
        }
    }

    void saveRegistrationID() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("REGISTRATION_ID", registrationID);
        editor.commit();
    }
}
