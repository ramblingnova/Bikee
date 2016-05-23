package com.example.tacademy.bikee.renter.sidemenu.creditcard;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnAdapterClickListener;
import com.example.tacademy.bikee.etc.dao.CardReceiveObject;
import com.example.tacademy.bikee.etc.dao.CardResult;
import com.example.tacademy.bikee.etc.dao.CardSendObject;
import com.example.tacademy.bikee.etc.dao.CardTokenReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortReceiveObject;
import com.example.tacademy.bikee.etc.dao.IAmPortSendObject;
import com.example.tacademy.bikee.etc.manager.IAmPortNetworkManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.renter.sidemenu.creditcard.register.RegisterCreditCardActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CreditCardsActivity extends AppCompatActivity implements OnAdapterClickListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_icon_back_image_view)
    ImageView toolbarLeftIconBackImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.toolbar_right_icon_image_view)
    ImageView toolbarRightIconImageView;
    @Bind(R.id.activity_credit_cards_cards_layout)
    RelativeLayout cardsLayout;
    @Bind(R.id.activity_credit_cards_cards_view_pager)
    ViewPager viewPager;

    private Intent intent;
    private CreditCardViewPagerAdapter viewPagerAdapter;
    private int pageScrollState;
    private int pagePosition;

    private static final String TAG = "CARDS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_cards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_credit_cards_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));

        /* 툴바 왼쪽 */
        toolbarLeftIconBackImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterTextView.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
        toolbarCenterTextView.setText("결제관리");

        /* 툴바 오른쪽 */
        toolbarRightIconImageView.setImageResource(R.drawable.rider_main_icon);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(
                getResources().getDimensionPixelSize(
                        R.dimen.activity_card_management_view_pager_left_padding
                ),
                getResources().getDimensionPixelSize(
                        R.dimen.activity_card_management_view_pager_top_padding
                ),
                getResources().getDimensionPixelSize(
                        R.dimen.activity_card_management_view_pager_left_padding
                ),
                getResources().getDimensionPixelSize(
                        R.dimen.activity_card_management_view_pager_bottom_padding
                )
        );
        viewPager.setPageMargin(
                getResources().getDimensionPixelSize(
                        R.dimen.activity_card_management_page_margin
                )
        );
        viewPager.addOnPageChangeListener(CreditCardsActivity.this);

        viewPagerAdapter = new CreditCardViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        pagePosition = 0;

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            if (cardReceiveObject.getCode() == 200) {
                                if (BuildConfig.DEBUG)
                                    Log.d(TAG, "registerCard onResponse getCode : " + cardReceiveObject.getCode()
                                            + "\ngetMsg : " + cardReceiveObject.getMsg());
                                viewPagerAdapter.clear();
                                init();
                            } else {
                                Log.d(TAG, "registerCard onResponse getCode : " + cardReceiveObject.getCode()
                                        + "\ngetMsg : " + cardReceiveObject.getMsg());
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

    @OnClick({R.id.toolbar_left_icon_layout,
            R.id.activity_credit_cards_add_card_layout,
            R.id.activity_credit_cards_add_card_prepayment_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_icon_layout:
                onBackPressed();
                break;
            case R.id.activity_credit_cards_add_card_layout:
                intent = new Intent(this, RegisterCreditCardActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.activity_credit_cards_add_card_prepayment_button:
                // TODO : DELME 사전결제는 여기에 있으면 안됨
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
    public void onAdapterClick(View view, Object item) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, ((CreditCardItem) item).getCardName() + "/" + ((CreditCardItem) item).getNickname());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if ((pageScrollState != ViewPager.SCROLL_STATE_DRAGGING) && (pagePosition != position)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "pageScrollState : " + pageScrollState
                                + "\nold pagePosition : " + pagePosition
                                + "\nnew pagePosition : " + position
                );

            ImageView imageVIew = (ImageView) cardsLayout.findViewById(R.id.indicator + pagePosition);
            imageVIew.setImageResource(R.drawable.scroll_black);

            pagePosition = position;

            imageVIew = (ImageView) cardsLayout.findViewById(R.id.indicator + position);
            imageVIew.setImageResource(R.drawable.scroll_blue);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        pageScrollState = state;
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

                            viewPagerAdapter.add(
                                    new CreditCardItem(
                                            result.get_id(),
                                            result.getCard_name(),
                                            result.getCard_nick()
                                    )
                            );
                        }
                        ImageUtil.initCardViewPagerIndicators(
                                CreditCardsActivity.this,
                                viewPagerAdapter.getCount(),
                                cardsLayout
                        );
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
