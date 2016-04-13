package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.common.bicycleImages.BicycleImageViewPagerAdapter;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RenterReservationBicycleDetailInformationActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener {
    private Intent intent;
    private String bicycleId;
    private String reserveId;
    @Bind(R.id.bicycle_pictures_user_information_view_pager)
    ViewPager viewPager;
    private int state;
    private int position = 0;
    @Bind(R.id.bicycle_pictures_layout)
    RelativeLayout bicyclePicturesLayout;
    @Bind(R.id.lister_information_lister_picture_image_view)
    ImageView listerPicture;
    @Bind(R.id.lister_information_lister_name_text_view)
    TextView listerName;
    private String listerPhone;
    @Bind(R.id.bicycle_description_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.bicycle_description_bicycle_introduction_text_view)
    TextView bicycleIntro;
    @Bind(R.id.bicycle_detail_information_bicycle_type_text_view)
    TextView bicycleType;
    @Bind(R.id.bicycle_detail_information_bicycle_height_text_view)
    TextView bicycleHeight;
    // TODO : show Component
    @Bind(R.id.bicycle_detail_information_bicycle_location_text_view)
    TextView rentalPlaceText;
    // TODO : shwo Period
    private String status;
    private String endDate;
    // TODO : show Price
    private int price;
    private ChoiceDialogFragment dialog;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button)
    Button cancelButton;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_pay_button)
    Button payButton;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2)
    Button cancelButton2;
    @Bind(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button)
    Button inputPostScriptionButton;
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

        intent = getIntent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_renter_reservation_bicycle_detail_information_small_map);
        mapFragment.getMapAsync(this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap gm = googleMap;
        gm.getUiSettings().setScrollGesturesEnabled(false);
        gm.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(
                new LatLng(
                        intent.getDoubleExtra("LATITUDE", 1.0),
                        intent.getDoubleExtra("LONGITUDE", 1.0)
                )
        );
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        gm.moveCamera(update);

        MarkerOptions options = new MarkerOptions();
        options.position(
                new LatLng(
                        intent.getDoubleExtra("LATITUDE", 1.0),
                        intent.getDoubleExtra("LONGITUDE", 1.0)
                )
        );
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        options.anchor(0.5f, 0.5f);
        gm.addMarker(options);

        gm.getUiSettings().setZoomGesturesEnabled(false);
    }

    @OnClick({R.id.renter_backable_tool_bar_back_button_layout,
            R.id.lister_information_chat_with_lister_button,
            R.id.lister_information_call_with_lister_button,
            R.id.activity_renter_reservation_bicycle_detail_information_cancel_button,
            R.id.activity_renter_reservation_bicycle_detail_information_pay_button,
            R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2,
            R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.renter_backable_tool_bar_back_button_layout:
                super.onBackPressed();
                break;
            case R.id.lister_information_chat_with_lister_button:
                // TODO : 유저를 하나 선택해서 채팅을 시작하는 부분 이미 채팅을 했던
//              intent.putExtra("START", true);
//              intent.putExtra("APP_ID", appId);
//              intent.putExtra("USER_ID", userId);
//              intent.putExtra("USER_NAME", userName);
//              for (MessagingChannel.Member member : item.getMessagingChannel().getMembers())
//                  if (!member.getId().equals(SendBird.getUserId())) {
//                      intent.putExtra("TARGET_USER_NAME", member.getName());
//                      intent.putExtra("TARGET_USER_ID", member.getId());
//                      break;
//                  }
//              intent.putExtra("bicycleName", item.getBicycleName());
//              startActivity(intent);
                break;
            case R.id.lister_information_call_with_lister_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listerPhone));
                startActivity(intent);
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button:
                dialog = new ChoiceDialogFragment().newInstance(bicycleId, reserveId, "RC", ChoiceDialogFragment.RENTER_CANCEL_ALREADY_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_pay_button:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_PAY_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2:
                dialog = new ChoiceDialogFragment().newInstance(bicycleId, reserveId, "RC", ChoiceDialogFragment.RENTER_CANCEL_ALREADY_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button:
                intent = new Intent(RenterReservationBicycleDetailInformationActivity.this, InputBicyclePostScriptActivity.class);
                intent.putExtra("ID", bicycleId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if ((state != ViewPager.SCROLL_STATE_DRAGGING) && (this.position != position)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "state : " + state + ", old position : " + this.position + ", new position : " + position);

            ImageView imageVIew = (ImageView) bicyclePicturesLayout.findViewById(R.id.indicator + this.position);
            imageVIew.setImageResource(R.drawable.detailpage_image_scroll_icon_w);
            this.position = position;
            imageVIew = (ImageView) bicyclePicturesLayout.findViewById(R.id.indicator + position);
            imageVIew.setImageResource(R.drawable.detailpage_image_scroll_icon_b);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.state = state;
    }

    private void init() {
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
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
                    // TODO : change none-rounded rectangle image
                    BicycleImageViewPagerAdapter bicycleImageViewPagerAdapter = new BicycleImageViewPagerAdapter(
                            RefinementUtil.getBicycleImageURLListFromResult(result)
                    );
                    viewPager.setAdapter(bicycleImageViewPagerAdapter);
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
                    rentalPlaceText.setText(
                            RefinementUtil.findAddress(
                                    MyApplication.getmContext(),
                                    result.getLoc().getCoordinates().get(1),
                                    result.getLoc().getCoordinates().get(0)
                            )
                    );
                    price = result.getPrice().getMonth();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onFailure Error : " + error.toString());
            }
        });

        status = intent.getStringExtra("STATUS");
        endDate = intent.getStringExtra("ENDDATE");
        if (BuildConfig.DEBUG)
            Log.d(TAG, "DETAIL ID : " + bicycleId
                            + ", Status : " + status
                            + ", endDate : " + endDate
            );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
        Date currentDate = new Date(System.currentTimeMillis());
        Date endTime = null;
        try {
            endTime = dateFormat.parse(endDate);
        } catch (ParseException pe) {
            // pe.printStackTrace();
        }
        if (!currentDate.after(endTime)) {
            switch (status) {
                case "RR":
                    cancelButton.setVisibility(View.VISIBLE);
                    payButton.setVisibility(View.VISIBLE);
                    cancelButton2.setVisibility(View.GONE);
                    inputPostScriptionButton.setVisibility(View.GONE);
                    break;
                case "RS":
                case "RC":
                    cancelButton.setVisibility(View.GONE);
                    payButton.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.VISIBLE);
                    inputPostScriptionButton.setVisibility(View.GONE);
                    break;
            }
        } else {
            cancelButton.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            cancelButton2.setVisibility(View.GONE);
            inputPostScriptionButton.setVisibility(View.VISIBLE);
        }

        reserveId = intent.getStringExtra("RESERVE");
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
