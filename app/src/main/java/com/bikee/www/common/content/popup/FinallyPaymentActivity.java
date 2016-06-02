package com.bikee.www.common.content.popup;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bikee.www.etc.dao.IAmPortSendObject;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.etc.dao.CardReceiveObject;
import com.bikee.www.etc.dao.CardTokenReceiveObject;
import com.bikee.www.etc.dao.IAmPortReceiveObject;
import com.bikee.www.etc.dao.PaymentSendObject;
import com.bikee.www.etc.dao.ReceiveObject;
import com.bikee.www.etc.manager.IAmPortNetworkManager;
import com.bikee.www.etc.manager.NetworkManager;
import com.bikee.www.etc.manager.PropertyManager;
import com.bikee.www.etc.utils.ImageUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinallyPaymentActivity extends AppCompatActivity {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_icon_back_image_view)
    ImageView toolbarLeftIconBackImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.activity_finally_payment_bicycle_information_bicycle_picture_image_view)
    ImageView bicyclePictureImageView;
    @Bind(R.id.activity_finally_payment_bicycle_information_bicycle_title_text_view)
    TextView bicycleTitleTextView;
    @Bind(R.id.activity_finally_payment_bicycle_information_lister_name_text_view)
    TextView listerNameTextView;
    @Bind(R.id.activity_finally_payment_rental_start_date_text_view)
    TextView rentalStartDateTextView;
    @Bind(R.id.activity_finally_payment_rental_end_date_text_view)
    TextView rentalEndDateTextView;
    @Bind(R.id.activity_finally_payment_rental_period_text_view)
    TextView rentalPeriodTextView;
    @Bind(R.id.activity_finally_payment_payment_text_view)
    TextView paymentTextView;

    private Intent intent;
    private String bicycleId;
    private String bicycleImage;
    private String bicycleName;
    private String listerId;
    private String listerName;
    private String reservationId;
    private Date rentalStartDate;
    private Date rentalEndDate;
    private String rentalPeriod;
    private int rentalPrice;
    private String renterEmail;
    private String renterName;
    private String cardId;
    private PaymentSendObject paymentSendObject;

    private static final String TAG = "FINALLY_PAYMENT_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_finally_payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);
        toolbarLeftIconBackImageView.setImageResource(R.drawable.icon_before);

        /* 툴바 가운데 */
        toolbarCenterTextView.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeBlack, getTheme()));
        toolbarCenterTextView.setText("결제하기");

        intent = getIntent();
        bicycleId = intent.getStringExtra("BICYCLE_ID");
        bicycleImage = intent.getStringExtra("BICYCLE_IMAGE");
        bicycleName = intent.getStringExtra("BICYCLE_NAME");
        listerId = intent.getStringExtra("LISTER_ID");
        listerName = intent.getStringExtra("LISTER_NAME");
        reservationId = intent.getStringExtra("RESERVATION_ID");
        rentalStartDate = (Date) intent.getSerializableExtra("RENTAL_START_DATE");
        rentalEndDate = (Date) intent.getSerializableExtra("RENTAL_END_DATE");
        rentalPeriod = intent.getStringExtra("RENTAL_PERIOD");
        rentalPrice = intent.getIntExtra("RENTAL_PRICE", -1);
        renterEmail = PropertyManager.getInstance().getEmail();
        renterName = PropertyManager.getInstance().getName();
        cardId = intent.getStringExtra("CARD_ID");

        ImageUtil.setRoundRectangleImageFromURL(
                this,
                bicycleImage,
                R.drawable.detailpage_bike_image_noneimage,
                bicyclePictureImageView,
                getResources().getDimension(
                        R.dimen.activity_finally_payment_bicycle_information_bicycle_picture_image_view_round_radius
                )
        );
        bicycleTitleTextView.setText(bicycleName);
        listerNameTextView.setText(listerName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd ah:mm", java.util.Locale.getDefault());
        rentalStartDateTextView.setText(simpleDateFormat.format(rentalStartDate));
        rentalEndDateTextView.setText(simpleDateFormat.format(rentalEndDate));
        rentalPeriodTextView.setText(rentalPeriod);
        paymentTextView.setText("" + rentalPrice);

        NetworkManager.getInstance().receiveCardToken(
                null,
                new Callback<CardTokenReceiveObject>() {
                    @Override
                    public void onResponse(Call<CardTokenReceiveObject> call, Response<CardTokenReceiveObject> response) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "receiveCardToken onResponse");

                        CardTokenReceiveObject cardTokenReceiveObject = response.body();
                        cardTokenReceiveObject.getToken();

                        IAmPortSendObject iAmPortSendObject = new IAmPortSendObject();
                        iAmPortSendObject.setMerchant_uid("bikee_" + new Date().getTime());
                        // TODO : 가격 입력, 현재 하드코딩됨
                        iAmPortSendObject.setAmount(1000);

                        IAmPortNetworkManager.getInstance().prepayment(
                                cardTokenReceiveObject.getToken(),
                                iAmPortSendObject,
                                null,
                                new Callback<IAmPortReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<IAmPortReceiveObject> call, Response<IAmPortReceiveObject> response) {
                                        IAmPortReceiveObject iAmPortReceiveObject = response.body();

                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "prepayment onResponse");

                                        if (iAmPortReceiveObject.getCode().equals("0")) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "prepayment 사전 결제 성공");

                                            paymentSendObject = new PaymentSendObject();
                                            paymentSendObject.setAmount(iAmPortReceiveObject.getResponse().getAmount());
                                            paymentSendObject.setMerchant_uid(iAmPortReceiveObject.getResponse().getMerchant_uid());
                                            paymentSendObject.setLister(listerId);
                                            paymentSendObject.setBike(bicycleId);
                                            paymentSendObject.setName(bicycleName);
                                            paymentSendObject.setBuyer_email(renterEmail);
                                            paymentSendObject.setBuyer_name(renterName);
                                        } else {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "prepayment 사전 결제 실패");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<IAmPortReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "prepayment onFailure", t);
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<CardTokenReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "receiveCardToken onFailure", t);
                    }
                });
    }

    @OnClick({R.id.toolbar_left_icon_layout,
            R.id.activity_finally_payment_cancel_button,
            R.id.activity_finally_payment_payment_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_icon_layout:
            case R.id.activity_finally_payment_cancel_button:
                onBackPressed();
                break;
            case R.id.activity_finally_payment_payment_button:
                NetworkManager.getInstance().payment(
                        cardId,
                        paymentSendObject,
                        null,
                        new Callback<CardReceiveObject>() {
                            @Override
                            public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "payment onResponse");

                                NetworkManager.getInstance().reserveStatus(
                                        reservationId,
                                        bicycleId,
                                        "PS",
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
                            }

                            @Override
                            public void onFailure(Call<CardReceiveObject> call, Throwable t) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "payment onFailure", t);
                            }
                        }
                );
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
