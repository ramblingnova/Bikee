package com.bikee.www.common.content.popup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bikee.www.etc.dao.CardReceiveObject;
import com.bikee.www.etc.dao.CardResult;
import com.bikee.www.etc.manager.NetworkManager;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardSelectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnCardAdapterCheckedChangeListener {
    @Bind(R.id.activity_card_selection_list_view)
    ListView listView;

    private Intent intent;
    private CardAdapter adapter;
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
    private String cardId;

    private static final String TAG = "CARD_SELECTION_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selection);

        ButterKnife.bind(this);

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

        adapter = new CardAdapter();
        adapter.setOnCardAdapterCheckedChangeListener(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        init();
    }

    @Override
    public void onCardAdapterCheckedChanged(CardItem item) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onCardAdapterCheckedChanged");
        cardId = item.getId();
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

    }

    @OnClick({R.id.activity_card_selection_cancel_button,
            R.id.activity_card_selection_confirm_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_card_selection_cancel_button:
                onBackPressed();
                break;
            case R.id.activity_card_selection_confirm_button:
                intent = new Intent(CardSelectionActivity.this, FinallyPaymentActivity.class);
                intent.putExtra("BICYCLE_ID", bicycleId);
                intent.putExtra("BICYCLE_IMAGE", bicycleImage);
                intent.putExtra("BICYCLE_NAME", bicycleName);
                intent.putExtra("LISTER_ID", listerId);
                intent.putExtra("LISTER_NAME", listerName);
                intent.putExtra("RESERVATION_ID", reservationId);
                intent.putExtra("RENTAL_START_DATE", rentalStartDate);
                intent.putExtra("RENTAL_END_DATE", rentalEndDate);
                intent.putExtra("RENTAL_PERIOD", rentalPeriod);
                intent.putExtra("RENTAL_PRICE", rentalPrice);
                intent.putExtra("CARD_ID", cardId);
                startActivity(intent);
                break;
        }
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
