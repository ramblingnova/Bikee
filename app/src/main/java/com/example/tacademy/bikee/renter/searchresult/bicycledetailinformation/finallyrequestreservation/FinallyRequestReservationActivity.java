package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FinallyRequestReservationActivity extends AppCompatActivity implements View.OnClickListener {
    private FinallyRequestReservationConfirmDialogFragment dialog2;
    private FinallyRequestReservationCancelDialogFragment dialog1;
    private Intent intent;
    private String id;
    private String imageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_request_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_finally_request_reservation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        Button btn = (Button) findViewById(R.id.activity_finally_request_reservation_cancel_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_confirm_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_small_map_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_finally_request_reservation_cancel_button:
                dialog1 = new FinallyRequestReservationCancelDialogFragment().newInstance(1);
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_finally_request_reservation_confirm_button:
                Reserve reserve = new Reserve();
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                reserve.setRentStart(date);
                date.setTime(System.currentTimeMillis());
                reserve.setRentEnd(date);
                NetworkManager.getInstance().insertReservation(id, reserve, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                + ", Code : " + receiveObject.getCode()
                                + ", Msg : " + receiveObject.getMsg()
                        );
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error! : " + error.toString());
                    }
                });
                dialog2 = new FinallyRequestReservationConfirmDialogFragment().newInstance(1);
                dialog2.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_finally_request_reservation_small_map_button:
                intent = new Intent(FinallyRequestReservationActivity.this, SmallMapActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initData() {
        intent = getIntent();
        id = intent.getStringExtra("ID");
        imageURL = intent.getStringExtra("IMAGEURL");
        type = intent.getStringExtra("BICYCLETYPE");
        height = intent.getStringExtra("BICYCLEHEIGHT");
        latitude = intent.getDoubleExtra("BICYCLELATITUDE", -1.0);
        longitude = intent.getDoubleExtra("BICYCLELONGITUDE", -1.0);
        price = intent.getIntExtra("BICYCLEPRICE", -1);

        Log.i("result", "onResponse ID : " + id
                        + ", imageURL : " + imageURL
                        + ", BicycleType : " + type
                        + ", BicycleHeight : " + height
                        + ", BicycleLatitude : " + latitude
                        + ", BicycleLongitude : " + longitude
                        + ", BicyclePrice : " + price
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
