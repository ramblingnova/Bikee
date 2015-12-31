package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RenterReservationBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private Intent intent;
    private GoogleMap googleMap;
    private ChoiceDialogFragment dialog;
    private Button cancelButton;
    private Button payButton;
    private Button cancelButton2;
    private Button inputPostScriptionButton;
    private String bicycleId;
    private String status;
    private String endDate;
    private String bicycleImageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    private String reserveId;

    @Bind(R.id.bicycle_description_bicycle_name_text_view) TextView bicycleName;
    @Bind(R.id.bicycle_description_bicycle_introduction_text_view) TextView bicycleIntro;
    @Bind(R.id.bicycle_detail_information_bicycle_type_text_view) TextView bicycleType;
    @Bind(R.id.bicycle_detail_information_bicycle_height_text_view) TextView bicycleHeight;
    @Bind(R.id.bicycle_detail_information_bicycle_location_text_view) TextView rentalPlaceText;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.renter_backable_tool_bar1, null);
        cView.findViewById(R.id.renter_backable_tool_bar1_back_button_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.renter_backable_tool_bar1_back_button_image_view:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);
        ButterKnife.bind(this);

        cancelButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);
        cancelButton.setOnClickListener(this);
        payButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);
        payButton.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);
        cancelButton2.setOnClickListener(this);
        inputPostScriptionButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);
        inputPostScriptionButton.setOnClickListener(this);
        image = (ImageView) findViewById(R.id.bicycle_picture_lister_information_bicycle_picture_image_view);

        intent = getIntent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_renter_reservation_bicycle_detail_information_small_map);
        mapFragment.getMapAsync(this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setScrollGesturesEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(intent.getDoubleExtra("LATITUDE", 1.0), intent.getDoubleExtra("LONGITUDE", 1.0)));
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        this.googleMap.moveCamera(update);

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(intent.getDoubleExtra("LATITUDE", 1.0), intent.getDoubleExtra("LONGITUDE", 1.0)));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        options.anchor(0.5f, 0.5f);
        this.googleMap.addMarker(options);

        this.googleMap.getUiSettings().setZoomGesturesEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void init() {
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                List<Result> results = receiveObject.getResult();
                for (Result result : results) {
                    String imageURL;
                    if ((null == result.getImage())
                            || (null == result.getImage().getCdnUri())
                            || (null == result.getImage().getFiles())
                            || (null == result.getImage().getFiles().get(0))) {
                        imageURL = "";
                    } else {
                        imageURL = result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0);
                    }
                    Log.i("result", "onResponse Bike Image : " + imageURL
                                    + ", Bike Name : " + result.getTitle()
                                    + ", Bike Intro : " + result.getIntro()
                                    + ", Type : " + result.getType()
                                    + ", Height : " + result.getHeight()
                                    + ", Latitude : " + result.getLoc().getCoordinates().get(1)
                                    + ", Longitude : " + result.getLoc().getCoordinates().get(0)
                                    + ", Price : " + result.getPrice().getMonth()
                    );
                    bicycleName.setText(result.getTitle());
                    bicycleIntro.setText(result.getIntro());
                    latitude = result.getLoc().getCoordinates().get(1);
                    longitude = result.getLoc().getCoordinates().get(0);
                    price = result.getPrice().getMonth();
                    Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), imageURL, 6, image);
                    switch (result.getType()) {
                        case "A": bicycleType.setText("보급형"); break;
                        case "B": bicycleType.setText("산악용"); break;
                        case "C": bicycleType.setText("하이브리드"); break;
                        case "D": bicycleType.setText("픽시"); break;
                        case "E": bicycleType.setText("폴딩"); break;
                        case "F": bicycleType.setText("미니벨로"); break;
                        case "G": bicycleType.setText("전기자전거"); break;
                        default: bicycleType.setText(""); break;
                    }
                    switch (result.getHeight()) {
                        case "01": bicycleHeight.setText("~ 145cm"); break;
                        case "02": bicycleHeight.setText("145cm ~ 155cm"); break;
                        case "03": bicycleHeight.setText("155cm ~ 165cm"); break;
                        case "04": bicycleHeight.setText("165cm ~ 175cm"); break;
                        case "05": bicycleHeight.setText("175cm ~ 185cm"); break;
                        case "06": bicycleHeight.setText("185cm ~"); break;
                        default: bicycleHeight.setText(""); break;
                    }
                    rentalPlaceText.setText(findAddress(latitude, longitude));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });

        status = intent.getStringExtra("STATUS");
        endDate = intent.getStringExtra("ENDDATE");
        Log.i("DETAIL", "DETAIL ID : " + bicycleId
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
        if (currentDate.after(endTime) == false) {
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

    private String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // address.get(0).getAddressLine(0);

                    bf.append(address.get(0).getAdminArea())
                            .append(" " + address.get(0).getLocality())
                            .append(" " + address.get(0).getLocality())
                            .append(" " + address.get(0).getThoroughfare());
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "주소취득 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return bf.toString();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
