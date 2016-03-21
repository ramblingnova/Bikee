package com.example.tacademy.bikee.common.chatting.room;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.conversation_toolbar_user_name_text_view)
    TextView userName;
    @Bind(R.id.activity_conversation_bicycle_image_image_view)
    ImageView bicycleImage;
    @Bind(R.id.activity_conversation_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.activity_conversation_reservation_period_text_view)
    TextView reservationPeriod;
    @Bind(R.id.activity_conversation_reservation_state_image_view)
    ImageView reservationStateImage;
    @Bind(R.id.activity_conversation_reservation_state_text_view)
    TextView reservationState;
    @Bind(R.id.activity_conversation_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.activity_conversation_recycler_view)
    RecyclerView recyclerView;
    private ConversationAdapter conversationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean lastVisibleItem;
    private SoftKeyboardDetectorView softKeyboardDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_conversation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.conversation_toolbar);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        userName.setText(intent.getStringExtra("userName"));

        swipeRefreshLayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        conversationAdapter = new ConversationAdapter();
        init();
        recyclerView.setAdapter(conversationAdapter);

        recyclerView.addItemDecoration(
                new ConversationDecoration(
                        getResources().getDimensionPixelSize(R.dimen.view_holder_conversation_item_bottom_space)
                )
        );

        recyclerView.smoothScrollToPosition(conversationAdapter.getItemCount() - 1);
        lastVisibleItem = true;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = conversationAdapter.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                lastVisibleItem = ((totalItemCount - 1) == lastVisibleItemPosition);
            }
        });

        softKeyboardDetector = new SoftKeyboardDetectorView(this);
        addContentView(softKeyboardDetector, new FrameLayout.LayoutParams(-1, -1));
        softKeyboardDetector.setOnShownKeyboard(new SoftKeyboardDetectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {
                if (lastVisibleItem)
                    recyclerView.scrollToPosition(conversationAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @OnClick(R.id.conversation_toolbar_back_button_layout)
    void back(View view) {
        super.onBackPressed();
    }

    private void init() {
        ImageUtil.setRoundRectangleImageFromURL(
                this,
                "",
                R.drawable.detailpage_bike_image_noneimage,
                bicycleImage,
                getResources().getDimensionPixelOffset(R.dimen.activity_conversation_bicycle_image_image_view_radius)
        );
        bicycleName.setText("2015 아메리칸이글 CY 픽시 블랙");
        reservationPeriod.setText("10.25 17:00 ~ 10.26 19:00");
        reservationStateImage.setImageResource(R.drawable.icon_step3);
        reservationState.setText("예약승인");
        if (Build.VERSION.SDK_INT < 23)
            reservationState.setTextColor(getResources().getColor(R.color.bikeeBlue));
        else
            reservationState.setTextColor(getResources().getColor(R.color.bikeeBlue, null));

        for (int i = 0; i < 30; i++) {
            Date conversationTime = new Date();
            conversationTime.setTime(System.currentTimeMillis());
            conversationAdapter.add(
                    new ConversationItem(
                            "",
                            "conversation" + i,
                            conversationTime,
                            ((i % 12) / 4) + 1
                    )
            );
        }
        for (int i = 0; i < 10; i++) {
            Date conversationTime = new Date();
            conversationTime.setTime(System.currentTimeMillis());
            conversationAdapter.add(
                    new ConversationItem(
                            "",
                            "conversation",
                            conversationTime,
                            (i % 2) + 1
                    )
            );
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
