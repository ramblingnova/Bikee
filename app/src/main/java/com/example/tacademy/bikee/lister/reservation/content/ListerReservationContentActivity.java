package com.example.tacademy.bikee.lister.reservation.content;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListerReservationContentActivity extends AppCompatActivity {
    @Bind(R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_left_button)
    Button bottomLeftButton;
    @Bind(R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_right_button)
    Button bottomRightButton;

    private Intent intent;
    private String reservationId;
    private String bicycleId;
    private String status;
    private Date startDate;
    private Date endDate;

    private static final String TAG = "LISTER_R_C_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_requested_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lister_requested_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.lister_backable_tool_bar, null);
        cView.findViewById(R.id.lister_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.lister_backable_tool_bar_back_button_layout:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);

        ButterKnife.bind(this);

        intent = getIntent();
        reservationId = intent.getStringExtra("RESERVATION_ID");
        bicycleId = intent.getStringExtra("ID");
        status = intent.getStringExtra("STATUS");
        startDate = (Date) intent.getSerializableExtra("START_DATE");
        endDate = (Date) intent.getSerializableExtra("END_DATE");

        init();
    }

    @OnClick({R.id.lister_backable_tool_bar_back_button_layout,
            R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_left_button,
            R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_right_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.lister_backable_tool_bar_back_button_layout:
                super.onBackPressed();
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_left_button: {
                if (bottomLeftButton.getTag(R.id.TAG_ONLINE_ID) != null) {
                    switch ((String) bottomLeftButton.getTag(R.id.TAG_ONLINE_ID)) {
                        case "예약취소하기":
                            NetworkManager.getInstance().reserveStatus(
                                    bicycleId,
                                    reservationId,
                                    "LC",
                                    null,
                                    new Callback<ReceiveObject>() {
                                        @Override
                                        public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse");
                                        }

                                        @Override
                                        public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onFailure", t);
                                        }
                                    });
                            break;
                    }
                } else {
                    super.onBackPressed();
                }
                break;
            }
            case R.id.activity_lister_requested_bicycle_detail_information_bottom_buttons_right_button: {
                // TODO : 버튼 이벤트, 뒤로가기 제외하고 모두 팝업을 거쳐야 한다.
                switch ((String) bottomRightButton.getTag(R.id.TAG_ONLINE_ID)) {
                    case "예약승인하기": {
                        NetworkManager.getInstance().reserveStatus(
                                bicycleId,
                                reservationId,
                                "RS",
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onResponse");
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });
                        break;
                    }
                    case "예약취소하기": {
                        NetworkManager.getInstance().reserveStatus(
                                bicycleId,
                                reservationId,
                                "LC",
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onResponse");
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });
                        break;
                    }
                }
                break;
            }
        }
    }

    private void init() {
        NetworkManager.getInstance().selectBicycleDetail(
                bicycleId,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        Log.i("result", "onResponse Success");
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });

        Date currentDate = new Date();

        switch (status) {
            case "RR":
                if (currentDate.after(startDate)) {
                    bottomRightButton.setVisibility(View.INVISIBLE);
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomLeftButton.setText("예약취소하기");
                    bottomLeftButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                    bottomRightButton.setText("예약승인하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "예약승인하기");
                }
                break;
            case "RS":
                if (currentDate.after(startDate)) {
                    bottomRightButton.setVisibility(View.INVISIBLE);
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomRightButton.setText("예약취소하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                }
                break;
            case "PS":
                if (currentDate.after(startDate)) {
                    if (currentDate.after(endDate)) {
                        bottomRightButton.setVisibility(View.INVISIBLE);
                    } else {
                        bottomRightButton.setVisibility(View.INVISIBLE);
                    }
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomRightButton.setText("예약취소하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                }
                break;
            case "RC":case "PC":
                bottomRightButton.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
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
