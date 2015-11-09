package com.example.tacademy.bikee.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.renter.RenterMainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(SplashActivity.this, RenterMainActivity.class);
        startActivity(intent);
        finish();

        // TODO : 일정 시간이 흐른 뒤에 종료해야 한다.
    }
}
