package com.example.tacademy.bikee.renter.searchresult.content.finallyrequestreservation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinallyRequestReservationActivity extends AppCompatActivity {
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

    private static final String TAG = "FINALLY_R_R_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_request_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_finally_request_reservation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.renter_backable_tool_bar, null);
        cView.findViewById(R.id.renter_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.renter_backable_tool_bar_back_button_layout:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);

        ButterKnife.bind(this);

        initData();
    }

    @OnClick(R.id.activity_finally_request_reservation_cancel_button)
    void cancel() {
        dialog1 = new FinallyRequestReservationCancelDialogFragment().newInstance(1);
        dialog1.show(getSupportFragmentManager(), "custom");
    }

    @OnClick(R.id.activity_finally_request_reservation_confirm_button)
    void confirm() {
        Reserve reserve = new Reserve();
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 1000*60*60);
        reserve.setRentStart(date);
        Date date1 = new Date();
        date1.setTime(System.currentTimeMillis() + 1000*60*60*3);
        reserve.setRentEnd(date1);
        NetworkManager.getInstance().insertReservation(
                id,
                reserve,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (receiveObject.isSuccess()) {
                            Log.i("result", "onResponse Success : " + receiveObject.isSuccess()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
        dialog2 = new FinallyRequestReservationConfirmDialogFragment().newInstance(1);
        dialog2.show(getSupportFragmentManager(), "custom");
    }

    @OnClick(R.id.activity_finally_request_reservation_small_map_button)
    void openSmallMap() {
        intent = new Intent(FinallyRequestReservationActivity.this, SmallMapActivity.class);
        startActivity(intent);
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
