package com.example.tacademy.bikee.renter.reservation.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.common.adapters.BicycleImageViewPagerAdapter;
import com.example.tacademy.bikee.common.views.AdditoryComponentView;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.example.tacademy.bikee.renter.reservation.content.popup.CardSelectionActivity;
import com.example.tacademy.bikee.renter.reservation.content.popup.InputBicyclePostScriptActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterReservationContentActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener {
    @Bind(R.id.bicycle_pictures_user_information_view_pager)
    ViewPager viewPager;
    @Bind(R.id.bicycle_pictures_layout)
    RelativeLayout bicyclePicturesLayout;
    @Bind(R.id.lister_information_lister_picture_image_view)
    ImageView listerPicture;
    @Bind(R.id.lister_information_lister_name_text_view)
    TextView listerName;
    @Bind(R.id.bicycle_description_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.bicycle_description_bicycle_introduction_text_view)
    TextView bicycleIntro;
    @Bind(R.id.bicycle_detail_information_bicycle_type_text_view)
    TextView bicycleType;
    @Bind(R.id.bicycle_detail_information_bicycle_height_text_view)
    TextView bicycleHeight;
    @Bind(R.id.bicycle_detail_information_bicycle_location_text_view)
    TextView rentalPlaceText;
    @Bind(R.id.activity_renter_reservation_content_bottom_buttons_left_button)
    Button bottomLeftButton;
    @Bind(R.id.activity_renter_reservation_content_bottom_buttons_right_button)
    Button bottomRightButton;
    BicycleImageViewPagerAdapter bicycleImageViewPagerAdapter;
    @Bind(R.id.additory_component_layout)
    LinearLayout linearLayout;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_additory_component)
    FrameLayout frameLayout;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_bicycle_component_bicycle_rental_place_line)
    View additoryComponentBottomLine;

    private Intent intent;
    private String bicycleId;
    private double bicycleLatitude;
    private double bicycleLongitude;
    private String listerId;
    private String listerPhone;
    private String reservationId;
    private String reservationStatus;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private int pagePosition;
    private int price;
    private int pageScrollState;

    private static final String TAG = "RENTER_R_B_D_I_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_backable_tool_bar);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_renter_reservation_bicycle_detail_information_small_map);
        mapFragment.getMapAsync(this);

        bicycleImageViewPagerAdapter = new BicycleImageViewPagerAdapter();

        intent = getIntent();
        bicycleId = intent.getStringExtra("BICYCLE_ID");
        bicycleLatitude = intent.getDoubleExtra("BICYCLE_LATITUDE", 1.0);
        bicycleLongitude = intent.getDoubleExtra("BICYCLE_LONGITUDE", 1.0);
        reservationId = intent.getStringExtra("RESERVATION_ID");
        reservationStatus = intent.getStringExtra("RESERVATION_STATUS");
        reservationStartDate = (Date) intent.getSerializableExtra("RESERVATION_START_DATE");
        reservationEndDate = (Date) intent.getSerializableExtra("RESERVATION_END_DATE");

        pagePosition = 0;

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

    @OnClick({R.id.renter_backable_tool_bar_back_button_layout,
            R.id.lister_information_chat_with_lister_button,
            R.id.lister_information_call_with_lister_button,
            R.id.activity_renter_reservation_content_bottom_buttons_left_button,
            R.id.activity_renter_reservation_content_bottom_buttons_right_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.renter_backable_tool_bar_back_button_layout:
            case R.id.activity_renter_reservation_content_bottom_buttons_left_button:
                super.onBackPressed();
                break;
            case R.id.lister_information_chat_with_lister_button:
                // TODO : 채팅 시작
                break;
            case R.id.lister_information_call_with_lister_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listerPhone));
                startActivity(intent);
                break;
            case R.id.activity_renter_reservation_content_bottom_buttons_right_button:
                // TODO : 버튼 이벤트, 뒤로가기 제외하고 모두 팝업을 거쳐야 한다.
                switch ((String) bottomRightButton.getTag(R.id.TAG_ONLINE_ID)) {
                    case "결제취소하기": {
                        NetworkManager.getInstance().reserveStatus(
                                bicycleId,
                                reservationId,
                                "PC",
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
                    break;
                    case "예약취소하기":
                        NetworkManager.getInstance().reserveStatus(
                                bicycleId,
                                reservationId,
                                "RC",
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
                    case "결제하기":
                        intent = new Intent(this, CardSelectionActivity.class);
                        intent.putExtra("BICYCLE_ID", bicycleId);
                        intent.putExtra("LISTER_ID", listerId);
                        intent.putExtra("RESERVATION_ID", reservationId);
                        intent.putExtra("BICYCLE_NAME", bicycleName.getText().toString());
                        startActivity(intent);
                        break;
                    case "후기작성":
                        intent = new Intent(this, InputBicyclePostScriptActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if ((pageScrollState != ViewPager.SCROLL_STATE_DRAGGING) && (this.pagePosition != position)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "pageScrollState : " + pageScrollState + ", old pagePosition : " + this.pagePosition + ", new pagePosition : " + position);

            ImageView imageVIew = (ImageView) bicyclePicturesLayout.findViewById(R.id.indicator + this.pagePosition);
            imageVIew.setImageResource(R.drawable.detailpage_image_scroll_icon_w);
            this.pagePosition = position;
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
        // TODO : Network를 굳이 타지 않게끔 해야 한다.
        NetworkManager.getInstance().selectBicycleDetail(
                bicycleId,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse Success");
                        List<Result> results = receiveObject.getResult();
                        for (Result result : results) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "onResponse Bike Image : " + RefinementUtil.getBicycleImageURLStringFromResult(result)
                                                + ", Bike Name : " + result.getTitle()
                                                + ", Bike Intro : " + result.getIntro()
                                                + ", Type : " + result.getType()
                                                + ", Height : " + result.getHeight()
                                                + ", Latitude : " + result.getLoc().getCoordinates().get(1)
                                                + ", Longitude : " + result.getLoc().getCoordinates().get(0)
                                                + ", Price : " + result.getPrice().getMonth()
                                );
                            bicycleImageViewPagerAdapter.addAll(RefinementUtil.getBicycleImageURLListFromResult(result));
                            viewPager.setAdapter(bicycleImageViewPagerAdapter);
                            viewPager.addOnPageChangeListener(RenterReservationContentActivity.this);
                            ImageUtil.initIndicators(
                                    MyApplication.getmContext(),
                                    bicycleImageViewPagerAdapter.getCount(),
                                    bicyclePicturesLayout
                            );
                            ImageUtil.setCircleImageFromURL(
                                    MyApplication.getmContext(),
                                    RefinementUtil.getUserImageURLStringFromResult(result),
                                    R.drawable.noneimage,
                                    0,
                                    listerPicture
                            );
                            listerId = result.getUser().get_id();
                            listerName.setText(result.getUser().getName());
                            listerPhone = result.getUser().getPhone();
                            bicycleName.setText(result.getTitle());
                            bicycleIntro.setText(result.getIntro());
                            bicycleType.setText(
                                    RefinementUtil.getBicycleTypeStringFromBicycleType(
                                            result.getType()
                                    )
                            );
                            bicycleHeight.setText(
                                    RefinementUtil.getBicycleHeightStringFromBicycleHeight(
                                            result.getHeight()
                                    )
                            );
                            if (result.getComponents().size() == 0) {
                                frameLayout.setVisibility(View.GONE);
                                additoryComponentBottomLine.setVisibility(View.GONE);
                            } else {
                                for (String component : result.getComponents()) {
                                    AdditoryComponentView additoryComponentView = new AdditoryComponentView(RenterReservationContentActivity.this);
                                    additoryComponentView.setView(component);
                                    additoryComponentView.setLayoutParams(
                                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                    );
                                    linearLayout.addView(additoryComponentView);
                                }
                            }
                            rentalPlaceText.setText(
                                    RefinementUtil.findAddress(
                                            MyApplication.getmContext(),
                                            result.getLoc().getCoordinates().get(1),
                                            result.getLoc().getCoordinates().get(0)
                                    )
                            );
                            price = result.getPrice().getMonth();
                            break;
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });

        Date currentDate = new Date();
        switch (reservationStatus) {
            case "RR":
                if (currentDate.after(reservationStartDate)) {
                    bottomRightButton.setVisibility(View.INVISIBLE);
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomRightButton.setText("예약취소하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "예약취소하기");
                }
                break;
            case "RS":
                if (currentDate.after(reservationStartDate)) {
                    bottomRightButton.setVisibility(View.INVISIBLE);
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomRightButton.setText("결제하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "결제하기");
                }
                break;
            case "PS":
                if (currentDate.after(reservationStartDate)) {
                    if (currentDate.after(reservationEndDate)) {
                        bottomRightButton.setVisibility(View.VISIBLE);
                        bottomRightButton.setText("후기작성");
                        bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "후기작성");
                    } else {
                        bottomRightButton.setVisibility(View.VISIBLE);
                        bottomRightButton.setText("후기작성");
                        bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "후기작성");
                    }
                } else {
                    bottomRightButton.setVisibility(View.VISIBLE);
                    bottomRightButton.setText("결제취소하기");
                    bottomRightButton.setTag(R.id.TAG_ONLINE_ID, "결제취소하기");
                }
                break;
            case "RC":
            case "PC":
                bottomRightButton.setVisibility(View.INVISIBLE);
                break;
            default:

                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
