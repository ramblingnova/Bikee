package com.example.tacademy.bikee.common.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.common.adapters.BicycleImageViewPagerAdapter;
import com.example.tacademy.bikee.common.chatting.room.ConversationActivity;
import com.example.tacademy.bikee.common.views.AdditoryComponentView;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.example.tacademy.bikee.lister.reservation.ListerReservationsFragment;
import com.example.tacademy.bikee.lister.sidemenu.bicycle.BicyclesActivity;
import com.example.tacademy.bikee.renter.reservation.RenterReservationsFragment;
import com.example.tacademy.bikee.common.content.popup.CardSelectionActivity;
import com.example.tacademy.bikee.common.content.popup.InputBicyclePostScriptActivity;
import com.example.tacademy.bikee.renter.searchresult.content.postscription.BicyclePostScriptListActivity;
import com.example.tacademy.bikee.renter.searchresult.list.SearchResultListFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sendbird.android.MessagingChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.MessagingChannel;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener {
    @Bind(R.id.activity_content_bicycle_pictures_layout)
    RelativeLayout bicyclePicturesLayout;
    @Bind(R.id.activity_content_bicycle_pictures_view_pager)
    ViewPager bicyclePicturesViewPager;
    @Bind(R.id.activity_content_user_information_user_picture_image_view)
    ImageView userPictureImageView;
    @Bind(R.id.activity_content_user_information_user_name_text_view)
    TextView userNameTextView;
    @Bind(R.id.activity_content_user_information_chatting_button)
    Button chattingButton;
    @Bind(R.id.activity_content_user_information_call_button)
    Button callButton;
    @Bind(R.id.activity_content_bicycle_title_text_view)
    TextView bicycleTitleTextView;
    @Bind(R.id.activity_content_bicycle_introduction_text_view)
    TextView bicycleIntroductionTextView;
    @Bind(R.id.activity_content_bicycle_type_text_view)
    TextView bicycleTypeTextView;
    @Bind(R.id.activity_content_bicycle_height_text_view)
    TextView bicycleHeightTextView;
    @Bind(R.id.activity_content_bicycle_location_text_view)
    TextView bicycleLocationTextView;
    BicycleImageViewPagerAdapter bicycleImageViewPagerAdapter;
    @Bind(R.id.activity_content_bicycle_components_layout)
    RelativeLayout bicycleComponentsLayout;
    @Bind(R.id.activity_content_bicycle_components_real_components_layout)
    LinearLayout bicycleComponentsRealComponentsLayout;
    @Bind(R.id.activity_content_bicycle_rental_period_layout)
    RelativeLayout bicycleRentalPeriodLayout;
    @Bind(R.id.activity_content_content_bicycle_rental_period_start_date_text_view)
    TextView bicycleRentalPeriodStartDateTextView;
    @Bind(R.id.activity_content_bicycle_rental_period_end_date_text_view)
    TextView bicycleRentalPeriodEndDateTextView;
    @Bind(R.id.activity_content_bicycle_rental_period_total_time_layout)
    RelativeLayout bicycleRentalPeriodTotalTimeLayout;
    @Bind(R.id.activity_content_bicycle_rental_period_total_time_text_view)
    TextView bicycleRentalPeriodTotalTimeTextView;
    @Bind(R.id.activity_content_bicycle_price_decision_layout)
    RelativeLayout bicyclePriceDecisionLayout;
    @Bind(R.id.activity_content_bicycle_price_decision_price_per_hour_text_view)
    TextView pricePerHourTextView;
    @Bind(R.id.activity_content_bicycle_price_decision_price_per_day_text_view)
    TextView pricePerDayTextView;
    @Bind(R.id.activity_content_bicycle_price_decision_price_per_month_text_view)
    TextView pricePerMonthTextView;
    @Bind(R.id.activity_content_bicycle_rental_price_layout)
    RelativeLayout bicycleRentalPriceLayout;
    @Bind(R.id.activity_content_bicycle_rental_price_text_view)
    TextView bicycleRentalPriceTextView;
    @Bind(R.id.activity_content_bicycle_comment_layout)
    RelativeLayout bicycleCommentLayout;
    @Bind(R.id.activity_content_bicycle_comment_renter_picture_image_view)
    ImageView bicycleCommentRenterPictureImageView;
    @Bind(R.id.activity_content_bicycle_comment_renter_name_text_view)
    TextView bicycleCommentRenterNameTextView;
    @Bind(R.id.activity_content_bicycle_comment_create_date_text_view)
    TextView bicycleCommentCreateDateTextView;
    @Bind(R.id.activity_content_bicycle_comment_body_text_view)
    TextView bicycleCommentBodyTextView;
    @Bind(R.id.activity_content_bicycle_comment_rating_bar)
    RatingBar bicycleCommentRatingBar;
    @Bind(R.id.activity_content_bottom_buttons_layout)
    LinearLayout bottomButtonsLayout;
    @Bind(R.id.activity_content_bottom_buttons_left_button)
    Button bottomButtonsLeftButton;
    @Bind(R.id.activity_content_bottom_buttons_right_button)
    Button bottomButtonsRightButton;

    private Intent intent;
    private int from;
    private String bicycleId;
    private double bicycleLatitude;
    private double bicycleLongitude;
    private String userId;
    private String userImageURL;
    private String userName;
    private String userPhone;
    private String reservationId;
    private String reservationStatus;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private int pagePosition;
    private int rentalPrice;
    private int pageScrollState;

    private MessagingChannelListQuery mMessagingChannelListQuery;
    private boolean hasMyId;
    private boolean hasTargetId;
    private boolean hasMyIdTargetId;
    private String messageChannelURL;

    private static final String TAG = "CONTENT_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_content_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        intent = getIntent();
        from = intent.getIntExtra("FROM", -1);

        View cView = null;
        if ((from == RenterReservationsFragment.from)
                || (from == SearchResultListFragment.from)) {
            cView = getLayoutInflater().inflate(R.layout.renter_backable_tool_bar, null);
            cView.findViewById(R.id.renter_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else if ((from == ListerReservationsFragment.from)
                || (from == BicyclesActivity.from)) {
            cView = getLayoutInflater().inflate(R.layout.lister_backable_tool_bar, null);
            cView.findViewById(R.id.lister_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        getSupportActionBar().setCustomView(cView);

        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_content_bicycle_location_map_container);
        mapFragment.getMapAsync(this);

        bicycleImageViewPagerAdapter = new BicycleImageViewPagerAdapter();
        pagePosition = 0;

        bicycleId = intent.getStringExtra("BICYCLE_ID");
        bicycleLatitude = intent.getDoubleExtra("BICYCLE_LATITUDE", 1.0);
        bicycleLongitude = intent.getDoubleExtra("BICYCLE_LONGITUDE", 1.0);
        if ((from == ListerReservationsFragment.from)
                || (from == RenterReservationsFragment.from)) {
            reservationId = intent.getStringExtra("RESERVATION_ID");
            reservationStatus = intent.getStringExtra("RESERVATION_STATUS");
            reservationStartDate = (Date) intent.getSerializableExtra("RESERVATION_START_DATE");
            reservationEndDate = (Date) intent.getSerializableExtra("RESERVATION_END_DATE");
        }
        if (from == ListerReservationsFragment.from) {
            userImageURL = intent.getStringExtra("RENTER_IMAGE");
            userName = intent.getStringExtra("RENTER_NAME");
            userPhone = intent.getStringExtra("RENTER_PHONE");
        }

        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap gm = googleMap;
        gm.getUiSettings().setScrollGesturesEnabled(false);
        gm.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(bicycleLatitude, bicycleLongitude));
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        gm.moveCamera(update);

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(bicycleLatitude, bicycleLongitude));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        options.anchor(0.5f, 0.5f);
        gm.addMarker(options);

        gm.getUiSettings().setZoomGesturesEnabled(false);
    }

    @OnClick({R.id.activity_content_user_information_chatting_button,
            R.id.activity_content_user_information_call_button,
            R.id.activity_content_bicycle_comment_button,
            R.id.activity_content_bottom_buttons_left_button,
            R.id.activity_content_bottom_buttons_right_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_content_user_information_chatting_button:
                // TODO : 채팅 시작
                // TODO : chatting start
                if (mMessagingChannelListQuery == null) {
                    mMessagingChannelListQuery = SendBird.queryMessagingChannelList();
                    mMessagingChannelListQuery.setLimit(30);
                }
                mMessagingChannelListQuery.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                    @Override
                    public void onResult(List<MessagingChannel> list) {
                        for (MessagingChannel messagingChannel : list) {
                            hasMyId = false;
                            hasTargetId = false;
                            hasMyIdTargetId = false;
                            for (MessagingChannel.Member member : messagingChannel.getMembers()) {
                                if (member.getId().equals(PropertyManager.getInstance().get_id()))
                                    hasMyId = true;
                                else if (member.getId().equals(userId))
                                    hasTargetId = true;
                                if (hasMyId && hasTargetId) {
                                    hasMyIdTargetId = true;
                                    messageChannelURL = messagingChannel.getUrl();
                                    break;
                                }
                            }
                        }

                        SendBird.join("");
                        SendBird.connect();

                        intent = new Intent(ContentActivity.this, ConversationActivity.class);
                        intent.putExtra("TARGET_USER_NAME", userName);
                        if ((from == RenterReservationsFragment.from)
                                || (from == SearchResultListFragment.from))
                            intent.putExtra("AM_I_LISTER", false);
                        else if (from == ListerReservationsFragment.from)
                            intent.putExtra("AM_I_LISTER", true);
                        intent.putExtra("TARGET_USER_ID", userId);
                        intent.putExtra("APP_ID", "2E377FE1-E1AD-4484-A66F-696AF1306F58");
                        intent.putExtra("USER_ID", PropertyManager.getInstance().get_id());
                        intent.putExtra("USER_NAME", PropertyManager.getInstance().getName());
                        intent.putExtra("BICYCLE_ID", bicycleId);
                        if (hasMyIdTargetId) {
                            // join chat using channelUrl
                            intent.putExtra("CHANNEL_URL", messageChannelURL);
                            intent.putExtra("JOIN", true);
                        } else {
                            // start chat using targetId
                            intent.putExtra("START", true);
                        }
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i) {

                    }
                });
                break;
            case R.id.activity_content_user_information_call_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userPhone));
                startActivity(intent);
                break;
            case R.id.activity_content_bicycle_comment_button:
                intent = new Intent(ContentActivity.this, BicyclePostScriptListActivity.class);
                intent.putExtra("BICYCLE_ID", bicycleId);
                startActivity(intent);
                break;
            case R.id.activity_content_bottom_buttons_left_button:
                if (bottomButtonsLeftButton.getTag(R.id.TAG_ONLINE_ID) != null) {
                    switch ((String) bottomButtonsLeftButton.getTag(R.id.TAG_ONLINE_ID)) {
                        case "예약취소하기":
                            NetworkManager.getInstance().reserveStatus(
                                    reservationId,
                                    bicycleId,
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
            case R.id.activity_content_bottom_buttons_right_button:
                // TODO : 버튼 이벤트, 뒤로가기 제외하고 모두 팝업을 거쳐야 한다.
                switch ((String) bottomButtonsRightButton.getTag(R.id.TAG_ONLINE_ID)) {
                    case "예약승인하기":
                        NetworkManager.getInstance().reserveStatus(
                                reservationId,
                                bicycleId,
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
                    case "결제취소하기":
                        NetworkManager.getInstance().reserveStatus(
                                reservationId,
                                bicycleId,
                                "PC",
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();

                                        if (receiveObject.isSuccess()) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse success : " + receiveObject.isSuccess());
                                        } else {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse success : " + receiveObject.isSuccess());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });
                        break;
                    case "예약취소하기":
                        String requestStatus = "";
                        if ((from == RenterReservationsFragment.from))
                            requestStatus = "RC";
                        else if ((from == ListerReservationsFragment.from))
                            requestStatus = "LC";
                        NetworkManager.getInstance().reserveStatus(
                                reservationId,
                                bicycleId,
                                requestStatus,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();

                                        if (receiveObject.isSuccess()) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse success : " + receiveObject.isSuccess());
                                        } else {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse success : " + receiveObject.isSuccess());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });
                        break;
                    case "결제하기":
                        intent = new Intent(this, CardSelectionActivity.class);
                        intent.putExtra("BICYCLE_ID", bicycleId);
                        intent.putExtra("LISTER_ID", userId);
                        intent.putExtra("RESERVATION_ID", reservationId);
                        intent.putExtra("BICYCLE_NAME", bicycleTitleTextView.getText().toString());
                        startActivity(intent);
                        break;
                    case "후기작성":
                        intent = new Intent(this, InputBicyclePostScriptActivity.class);
                        intent.putExtra("BICYCLE_ID", bicycleId);
                        startActivity(intent);
                        break;
                    case "예약날짜선택":
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if ((pageScrollState != ViewPager.SCROLL_STATE_DRAGGING) && (pagePosition != position)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "pageScrollState : " + pageScrollState
                                + "\nold pagePosition : " + pagePosition
                                + "\nnew pagePosition : " + position
                );

            ImageView imageVIew = (ImageView) bicyclePicturesLayout.findViewById(R.id.indicator + pagePosition);
            imageVIew.setImageResource(R.drawable.detailpage_image_scroll_icon_w);

            pagePosition = position;

            imageVIew = (ImageView) bicyclePicturesLayout.findViewById(R.id.indicator + position);
            imageVIew.setImageResource(R.drawable.detailpage_image_scroll_icon_b);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.pageScrollState = state;
    }

    private void init() {
        NetworkManager.getInstance().selectBicycleDetail(
                bicycleId,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();

                        if (receiveObject.isSuccess()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectBicycleDetail onResponse success : " + receiveObject.isSuccess());

                            if ((receiveObject.getResult() != null)
                                    && (receiveObject.getResult().size() > 0)) {
                                Result result = receiveObject.getResult().get(0);

//                                printLog(result);

                                /* 자전거 이미지 */
                                bicycleImageViewPagerAdapter.addAll(RefinementUtil.getBicycleImageURLListFromResult(result));
                                bicyclePicturesViewPager.setAdapter(bicycleImageViewPagerAdapter);
                                bicyclePicturesViewPager.addOnPageChangeListener(ContentActivity.this);
                                ImageUtil.initIndicators(
                                        MyApplication.getmContext(),
                                        bicycleImageViewPagerAdapter.getCount(),
                                        bicyclePicturesLayout
                                );

                                /* 유저 아이디 */ // TODO
                                if ((from == RenterReservationsFragment.from)
                                        || (from == SearchResultListFragment.from))
                                    userId = result.getUser().get_id();

                                /* 유저 이미지 */
                                if ((from == RenterReservationsFragment.from)
                                        || (from == SearchResultListFragment.from)
                                        || (from == BicyclesActivity.from))
                                    ImageUtil.setCircleImageFromURL(
                                            MyApplication.getmContext(),
                                            RefinementUtil.getUserImageURLStringFromResult(result),
                                            R.drawable.noneimage,
                                            0,
                                            userPictureImageView
                                    );
                                else if (from == ListerReservationsFragment.from)
                                    ImageUtil.setCircleImageFromURL(
                                            MyApplication.getmContext(),
                                            userImageURL,
                                            R.drawable.noneimage,
                                            0,
                                            userPictureImageView
                                    );

                                /* 유저 이름 */
                                if ((from == RenterReservationsFragment.from)
                                        || (from == SearchResultListFragment.from)
                                        || (from == BicyclesActivity.from)) {
                                    userNameTextView.setText(result.getUser().getName());
                                    userName = result.getUser().getName();
                                } else if (from == ListerReservationsFragment.from)
                                    userNameTextView.setText(userName);

                                /* 유저 폰 */
                                if ((from == RenterReservationsFragment.from)
                                        || (from == SearchResultListFragment.from))
                                    userPhone = result.getUser().getPhone();
                                else if (from == BicyclesActivity.from)
                                    callButton.setVisibility(View.GONE);

                                /* 유저 채팅 */
                                if (from == BicyclesActivity.from)
                                    chattingButton.setVisibility(View.GONE);

                                /* 자전거 제목 */
                                bicycleTitleTextView.setText(result.getTitle());

                                /* 자전거 설명 */
                                bicycleIntroductionTextView.setText(result.getIntro());

                                /* 자전거 타입 */
                                bicycleTypeTextView.setText(
                                        RefinementUtil.getBicycleTypeStringFromBicycleType(
                                                result.getType()
                                        )
                                );

                                /* 자전거 권장 신장 */
                                bicycleHeightTextView.setText(
                                        RefinementUtil.getBicycleHeightStringFromBicycleHeight(
                                                result.getHeight()
                                        )
                                );

                                /* 자전거 추가 구성품 */
                                if (result.getComponents().size() == 0) {
                                    bicycleComponentsLayout.setVisibility(View.GONE);
                                } else {
                                    for (String component : result.getComponents()) {
                                        AdditoryComponentView additoryComponentView = new AdditoryComponentView(ContentActivity.this);
                                        additoryComponentView.setView(component);
                                        additoryComponentView.setLayoutParams(
                                                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                        );
                                        bicycleComponentsRealComponentsLayout.addView(additoryComponentView);
                                    }
                                }

                                /* 자전거 위치 */
                                bicycleLocationTextView.setText(
                                        RefinementUtil.findAddress(
                                                MyApplication.getmContext(),
                                                result.getLoc().getCoordinates().get(1),
                                                result.getLoc().getCoordinates().get(0)
                                        )
                                );

                                if ((from == ListerReservationsFragment.from)
                                        || (from == RenterReservationsFragment.from)) {
                                /* 대여 날짜 */
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd aahh:mm", java.util.Locale.getDefault());
                                    bicycleRentalPeriodStartDateTextView.setText(dateFormat.format(reservationStartDate));
                                    bicycleRentalPeriodEndDateTextView.setText(dateFormat.format(reservationEndDate));

                                /* 총 시간 */
                                    bicycleRentalPeriodTotalTimeTextView.setText(
                                            RefinementUtil.calculateRentPeriod(
                                                    reservationStartDate,
                                                    reservationEndDate
                                            )
                                    );

                                /* 자전거 가격 */
                                    rentalPrice = RefinementUtil.calculatePrice(
                                            reservationStartDate,
                                            reservationEndDate,
                                            result.getPrice().getMonth(),
                                            result.getPrice().getDay(),
                                            result.getPrice().getHour()
                                    );
                                    bicycleRentalPriceTextView.setText(rentalPrice + "원");
                                } else if ((from == BicyclesActivity.from)
                                        || (from == SearchResultListFragment.from)) {
                                    bicycleRentalPeriodLayout.setVisibility(View.GONE);
                                    bicycleRentalPeriodTotalTimeLayout.setVisibility(View.GONE);
                                    bicycleRentalPriceLayout.setVisibility(View.GONE);
                                    bicyclePriceDecisionLayout.setVisibility(View.VISIBLE);
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bicycleCommentLayout.getLayoutParams();
                                    params.addRule(RelativeLayout.BELOW, R.id.activity_content_bicycle_location_map_layout);
                                    bicycleCommentLayout.setLayoutParams(params);
                                    pricePerHourTextView.setText(result.getPrice().getHour() + "원");
                                    pricePerDayTextView.setText(result.getPrice().getDay() + "원");
                                    pricePerMonthTextView.setText(result.getPrice().getMonth() + "원");
                                }

                                /* 자전거 후기 */
                                NetworkManager.getInstance().selectBicycleComment(
                                        bicycleId,
                                        null,
                                        new Callback<ReceiveObject>() {
                                            @Override
                                            public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                                ReceiveObject receiveObject = response.body();
                                                if (receiveObject.isSuccess()) {
                                                    if (BuildConfig.DEBUG)
                                                        Log.d(TAG, "selectBicycleComment onResponse success : " + receiveObject.isSuccess());

                                                    if ((receiveObject.getResult() != null)
                                                            && (receiveObject.getResult().size() > 0)
                                                            && (receiveObject.getResult().get(0) != null)
                                                            && (receiveObject.getResult().get(0).getComments() != null)
                                                            && (receiveObject.getResult().get(0).getComments().size() > 0)) {
                                                        bicycleCommentLayout.setVisibility(View.VISIBLE);
                                                        for (Comment comment : receiveObject.getResult().get(0).getComments()) {
                                                            if (comment.getWriter().getImage() != null) {
                                                                ImageUtil.setCircleImageFromURL(
                                                                        ContentActivity.this,
                                                                        RefinementUtil.getUserImageURLStringFromComment(comment),
                                                                        R.drawable.noneimage,
                                                                        6,
                                                                        bicycleCommentRenterPictureImageView
                                                                );
                                                            }
                                                            bicycleCommentRenterNameTextView.setText("" + comment.getWriter().getName());
                                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd. HH:mm");
                                                            bicycleCommentCreateDateTextView.setText("" + simpleDateFormat.format(comment.getCreatedAt()));
                                                            bicycleCommentBodyTextView.setText("" + comment.getBody());
                                                            bicycleCommentRatingBar.setRating((null != comment.getPoint()) ? comment.getPoint() : 0);
                                                        }

                                                        if ((from == BicyclesActivity.from)
                                                                || (from == SearchResultListFragment.from)) {
                                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bicycleCommentLayout.getLayoutParams();
                                                            params.addRule(RelativeLayout.BELOW, R.id.activity_content_bicycle_price_decision_layout);
                                                            bicycleCommentLayout.setLayoutParams(params);
                                                        }
                                                    } else {
                                                        if ((from == BicyclesActivity.from)
                                                                || (from == SearchResultListFragment.from)) {
                                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bottomButtonsLayout.getLayoutParams();
                                                            params.addRule(RelativeLayout.BELOW, R.id.activity_content_bicycle_price_decision_layout);
                                                            bottomButtonsLayout.setLayoutParams(params);
                                                            bicyclePriceDecisionLayout.setBackgroundResource(R.drawable.detailpage_back_img2);
                                                        }
                                                        bicycleCommentLayout.setVisibility(View.GONE);
                                                    }
                                                } else {
                                                    if (BuildConfig.DEBUG)
                                                        Log.d(TAG, "selectBicycleComment onResponse success : " + receiveObject.isSuccess());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "selectBicycleComment onFailure", t);
                                            }
                                        }
                                );
                            }
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectBicycleDetail onResponse success : " + receiveObject.isSuccess());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "selectBicycleDetail onFailure", t);
                    }
                });

        /* 하단 버튼 */
        if ((from == ListerReservationsFragment.from)
                || (from == RenterReservationsFragment.from)) {
            Date currentDate = new Date();
            switch (reservationStatus) {
                case "RR":
                    if (currentDate.after(reservationStartDate)) {
                        bottomButtonsRightButton.setVisibility(View.GONE);
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomButtonsLeftButton.getLayoutParams();
                        if (Build.VERSION.SDK_INT >= 17)
                            params.setMarginEnd(0);
                        params.setMargins(0, 0, 0, 0);
                        bottomButtonsLeftButton.setLayoutParams(params);
                    } else {
                        bottomButtonsRightButton.setVisibility(View.VISIBLE);
                        if (from == RenterReservationsFragment.from) {
                            bottomButtonsRightButton.setText("예약취소하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                        } else if (from == ListerReservationsFragment.from) {
                            bottomButtonsLeftButton.setText("예약취소하기");
                            bottomButtonsLeftButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                            bottomButtonsRightButton.setText("예약승인하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "예약승인하기");
                        }
                    }
                    break;
                case "RS":
                    if (currentDate.after(reservationStartDate)) {
                        bottomButtonsRightButton.setVisibility(View.GONE);
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomButtonsLeftButton.getLayoutParams();
                        if (Build.VERSION.SDK_INT >= 17)
                            params.setMarginEnd(0);
                        params.setMargins(0, 0, 0, 0);
                        bottomButtonsLeftButton.setLayoutParams(params);
                    } else {
                        bottomButtonsRightButton.setVisibility(View.VISIBLE);
                        if (from == RenterReservationsFragment.from) {
                            bottomButtonsRightButton.setText("결제하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "결제하기");
                        } else if (from == ListerReservationsFragment.from) {
                            bottomButtonsRightButton.setText("예약취소하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                        }
                    }
                    break;
                case "PS":
                    if (currentDate.after(reservationStartDate)) {
                        if (currentDate.after(reservationEndDate)) {
                            bottomButtonsRightButton.setVisibility(View.VISIBLE);
                            if (from == RenterReservationsFragment.from) {
                                bottomButtonsRightButton.setText("후기작성");
                                bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "후기작성");
                            }
                        } else {
                            bottomButtonsRightButton.setVisibility(View.VISIBLE);
                            if (from == RenterReservationsFragment.from) {
                                bottomButtonsRightButton.setText("후기작성");
                                bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "후기작성");
                            }
                        }
                    } else {
                        bottomButtonsRightButton.setVisibility(View.VISIBLE);
                        if (from == RenterReservationsFragment.from) {
                            bottomButtonsRightButton.setText("결제취소하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "결제취소하기");
                        } else if (from == ListerReservationsFragment.from) {
                            bottomButtonsRightButton.setText("예약취소하기");
                            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                        }
                    }
                    break;
                case "RC":
                case "LC":
                case "PC": {
                    bottomButtonsRightButton.setVisibility(View.GONE);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomButtonsLeftButton.getLayoutParams();
                    if (Build.VERSION.SDK_INT >= 17)
                        params.setMarginEnd(0);
                    params.setMargins(0, 0, 0, 0);
                    bottomButtonsLeftButton.setLayoutParams(params);
                    break;
                }
                default:
                    break;
            }
        } else if (from == BicyclesActivity.from) {
            bottomButtonsRightButton.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottomButtonsLeftButton.getLayoutParams();
            if (Build.VERSION.SDK_INT >= 17)
                params.setMarginEnd(0);
            params.setMargins(0, 0, 0, 0);
            bottomButtonsLeftButton.setLayoutParams(params);
        } else if (from == SearchResultListFragment.from) {
            bottomButtonsRightButton.setVisibility(View.VISIBLE);
            bottomButtonsRightButton.setText("예약날짜선택");
            bottomButtonsRightButton.setTag(R.id.TAG_ONLINE_ID, "예약날짜선택");
        }
    }

    private void printLog(Result result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Bicycle Id : ").append(bicycleId).append("\n");
        builder.append("Bicycle Image Url : ");
        for (String url : RefinementUtil.getBicycleImageURLListFromResult(result))
            builder.append(url).append("\n");
        builder.append("User Image Url : ").append(RefinementUtil.getUserImageURLStringFromResult(result)).append("\n");
        builder.append("User Name : ").append(result.getUser().getName()).append("\n");
        builder.append("User Phone : ").append(result.getUser().getPhone()).append("\n");
        builder.append("Bicycle Name : ").append(result.getTitle()).append("\n");
        builder.append("Bicycle Intro : ").append(result.getIntro()).append("\n");
        builder.append("Bicycle Type : ").append(result.getType()).append("\n");
        builder.append("Bicycle Height : ").append(result.getHeight()).append("\n");
        builder.append("Bicycle Component : ");
        if (result.getComments() != null && result.getComponents().size() > 0)
            for (String component : result.getComponents())
                builder.append(component).append("\n");
        else
            builder.append("\n");
        builder.append("Bicycle Latitude : ").append(result.getLoc().getCoordinates().get(1)).append("\n");
        builder.append("Bicycle Longitude : ").append(result.getLoc().getCoordinates().get(0)).append("\n");
        builder.append("Bicycle Price Month : ").append(result.getPrice().getMonth()).append("\n");
        builder.append("Bicycle Price Day : ").append(result.getPrice().getDay()).append("\n");
        builder.append("Bicycle Price Hour : ").append(result.getPrice().getHour()).append("\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", java.util.Locale.getDefault());
        builder.append("Reservation Start Date : ").append(dateFormat.format(reservationStartDate)).append("\n");
        builder.append("Reservation End Date : ").append(dateFormat.format(reservationEndDate));

        if (BuildConfig.DEBUG)
            Log.d(TAG, builder.toString());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
