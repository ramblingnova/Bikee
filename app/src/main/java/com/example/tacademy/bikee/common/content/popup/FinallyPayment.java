package com.example.tacademy.bikee.common.content.popup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tacademy.bikee.R;

import butterknife.ButterKnife;

public class FinallyPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_payment);

        ButterKnife.bind(this);


    }
}
