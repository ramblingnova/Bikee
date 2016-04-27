package com.example.tacademy.bikee.renter.sidemenu.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.CardReceiveObject;
import com.example.tacademy.bikee.etc.dao.CardResult;
import com.example.tacademy.bikee.etc.dao.CardSendObject;
import com.example.tacademy.bikee.etc.dao.CardTokenReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortSendObject;
import com.example.tacademy.bikee.etc.manager.IAmPortNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.sidemenu.card.register.RegisterCardActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardsActivity extends AppCompatActivity implements OnCardAdapterClickListener {
    private Intent intent;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_card_management_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_backable_tool_bar);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_card_management_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        cardAdapter = new CardAdapter();
        cardAdapter.setOnCardAdapterClickListener(this);

        recyclerView.setAdapter(cardAdapter);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            CardSendObject cardSendObject = new CardSendObject();
            cardSendObject.setCard_number(data.getStringExtra("CARD_NUMBER"));
            cardSendObject.setExpiry(data.getStringExtra("EXPIRATION_DATE"));
            cardSendObject.setBirth(data.getStringExtra("BIRTH_DATE"));
            cardSendObject.setPwd_2digit(data.getStringExtra("PASSWORD_2_DIGIT"));
            cardSendObject.setCard_nick(data.getStringExtra("NICKNAME"));
            NetworkManager.getInstance().registerCard(
                    cardSendObject,
                    null,
                    new Callback<CardReceiveObject>() {
                        @Override
                        public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
                            CardReceiveObject cardReceiveObject = response.body();
                            Log.d(TAG, cardReceiveObject.getMsg());
                            if (cardReceiveObject.getCode() == 200) {
                                cardAdapter.clear();
                                init();
                            } else {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, cardReceiveObject.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Call<CardReceiveObject> call, Throwable t) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "registerCard onFailure", t);
                        }
                    }
            );
        }
    }

    @OnClick(R.id.renter_backable_tool_bar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    @OnClick({R.id.activity_card_management_add_button,
            R.id.activity_card_management_prepayment_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_card_management_add_button:
                intent = new Intent(this, RegisterCardActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.activity_card_management_prepayment_button:
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

//                                                    PaymentSendObject paymentSendObject = new PaymentSendObject();
//                                                    paymentSendObject.setAmount(iAmPortReceiveObject.getResponse().getAmount());
//                                                    paymentSendObject.setMerchant_uid(iAmPortReceiveObject.getResponse().getMerchant_uid());
//                                                    paymentSendObject.setLister("");
//                                                    paymentSendObject.setBike("");
//                                                    paymentSendObject.setName("");
//                                                    paymentSendObject.setBuyer_email("");
//                                                    paymentSendObject.setBuyer_name("");
//
//                                                    NetworkManager.getInstance().payment(
//                                                            "571ded7f6ccbb1b02a114570",
//                                                            paymentSendObject,
//                                                            null,
//                                                            new Callback<CardReceiveObject>() {
//                                                                @Override
//                                                                public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
//                                                                    if (BuildConfig.DEBUG)
//                                                                        Log.d(TAG, "payment onResponse");
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<CardReceiveObject> call, Throwable t) {
//                                                                    if (BuildConfig.DEBUG)
//                                                                        Log.d(TAG, "payment onFailure", t);
//                                                                }
//                                                            }
//                                                    );
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
                break;
        }
    }

    @Override
    public void onCardAdapterClick(View view, CardItem item) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, item.getCardName() + "/" + item.getNickname());
    }

    private void init() {
        NetworkManager.getInstance().receiveCard(
                null,
                new Callback<CardReceiveObject>() {
                    @Override
                    public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
                        CardReceiveObject cardReceiveObject = response.body();

                        if (BuildConfig.DEBUG)
                            Log.d(TAG, cardReceiveObject.getMsg());

                        for (CardResult result : cardReceiveObject.getResult()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "_id : " + result.get_id());
                            cardAdapter.add(
                                    new CardItem(
                                            R.mipmap.ic_launcher,
                                            result.get_id(),
                                            result.getCard_name(),
                                            result.getCard_nick()
                                    )
                            );
                        }

                    }

                    @Override
                    public void onFailure(Call<CardReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "receiveCard onFailure", t);
                    }
                }
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
