package com.example.tacademy.bikee.common;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.RenterMainActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PropertyManager.getInstance().getInit().equals("")) {
            PropertyManager.getInstance().setLatitude("37.565596");
            PropertyManager.getInstance().setLongitude("126.978013");
            PropertyManager.getInstance().setInit("false");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!PropertyManager.getInstance().getEmail().equals("")
                        || !PropertyManager.getInstance().getPassword().equals("")) {
                    NetworkManager.getInstance().login(PropertyManager.getInstance().getEmail(), PropertyManager.getInstance().getPassword(), new Callback<ReceiveObject>() {
                        @Override
                        public void success(ReceiveObject receiveObject, Response response) {
                            Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("error", "onFailure Error : " + error.toString());
                        }
                    });
                }
                Intent intent = new Intent(SplashActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
