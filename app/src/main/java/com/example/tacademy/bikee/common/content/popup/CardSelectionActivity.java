package com.example.tacademy.bikee.common.content.popup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.CardReceiveObject;
import com.example.tacademy.bikee.etc.dao.CardResult;
import com.example.tacademy.bikee.etc.dao.CardTokenReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortSendObject;
import com.example.tacademy.bikee.etc.dao.PaymentSendObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.IAmPortNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardSelectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.activity_card_selection_list_view)
    ListView listView;

    private Intent intent;
    private CardAdapter adapter;
    private String bicycleId;
    private String listerId;
    private String reservationId;
    private String bicycleName;
    private String renterEmail;
    private String renterName;


    private static final String TAG = "CARD_SELECTION_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selection);

        ButterKnife.bind(this);

        intent = getIntent();
        bicycleId = intent.getStringExtra("BICYCLE_ID");
        listerId = intent.getStringExtra("LISTER_ID");
        reservationId = intent.getStringExtra("RESERVATION_ID");
        bicycleName = intent.getStringExtra("BICYCLE_NAME");
        renterEmail = PropertyManager.getInstance().getEmail();
        renterName = PropertyManager.getInstance().getName();

        adapter = new CardAdapter();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        init();
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
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

                                            PaymentSendObject paymentSendObject = new PaymentSendObject();
                                            paymentSendObject.setAmount(iAmPortReceiveObject.getResponse().getAmount());
                                            paymentSendObject.setMerchant_uid(iAmPortReceiveObject.getResponse().getMerchant_uid());
                                            paymentSendObject.setLister(listerId);
                                            paymentSendObject.setBike(bicycleId);
                                            paymentSendObject.setName(bicycleName);
                                            paymentSendObject.setBuyer_email(renterEmail);
                                            paymentSendObject.setBuyer_name(renterName);

                                            NetworkManager.getInstance().payment(
                                                    ((CardItem) parent.getItemAtPosition(position)).getId(),
                                                    paymentSendObject,
                                                    null,
                                                    new Callback<CardReceiveObject>() {
                                                        @Override
                                                        public void onResponse(Call<CardReceiveObject> call, Response<CardReceiveObject> response) {
                                                            if (BuildConfig.DEBUG)
                                                                Log.d(TAG, "payment onResponse");

                                                            String reservationId = intent.getStringExtra("RESERVATION_ID");
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
                            adapter.add(
                                    new CardItem(
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
}
