package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RenterReservationBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private ChoiceDialogFragment dialog;
    private Button cancelButton;
    private Button payButton;
    private Button cancelButton2;
    private Button inputPpostScriptionButton;
    private String bicycleId;
    private String status;
    private String endDate;
    private String bicycleImageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    private CheckBox typeCheck1;
    private CheckBox typeCheck2;
    private CheckBox typeCheck3;
    private CheckBox typeCheck4;
    private CheckBox typeCheck5;
    private CheckBox typeCheck6;
    private CheckBox typeCheck7;
    private CheckBox heightCheck1;
    private CheckBox heightCheck2;
    private CheckBox heightCheck3;
    private CheckBox heightCheck4;
    private CheckBox heightCheck5;
    private CheckBox heightCheck6;
    private ImageView image;
    private TextView rentalPlaceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        cancelButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);
        cancelButton.setOnClickListener(this);
        payButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);
        payButton.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);
        cancelButton2.setOnClickListener(this);
        inputPpostScriptionButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);
        inputPpostScriptionButton.setOnClickListener(this);
        typeCheck1 = (CheckBox) findViewById(R.id.bicycle_type_check_box1);
        typeCheck1.setClickable(false);
        typeCheck2 = (CheckBox) findViewById(R.id.bicycle_type_check_box2);
        typeCheck2.setClickable(false);
        typeCheck3 = (CheckBox) findViewById(R.id.bicycle_type_check_box3);
        typeCheck3.setClickable(false);
        typeCheck4 = (CheckBox) findViewById(R.id.bicycle_type_check_box4);
        typeCheck4.setClickable(false);
        typeCheck5 = (CheckBox) findViewById(R.id.bicycle_type_check_box5);
        typeCheck5.setClickable(false);
        typeCheck6 = (CheckBox) findViewById(R.id.bicycle_type_check_box6);
        typeCheck6.setClickable(false);
        typeCheck7 = (CheckBox) findViewById(R.id.bicycle_type_check_box7);
        typeCheck7.setClickable(false);
        heightCheck1 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box1);
        heightCheck1.setClickable(false);
        heightCheck2 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box2);
        heightCheck2.setClickable(false);
        heightCheck3 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box3);
        heightCheck3.setClickable(false);
        heightCheck4 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box4);
        heightCheck4.setClickable(false);
        heightCheck5 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box5);
        heightCheck5.setClickable(false);
        heightCheck6 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box6);
        heightCheck6.setClickable(false);
        image = (ImageView) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_bicycle_picture);
        rentalPlaceText = (TextView) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_bicycle_rental_place_real_position_text_view);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_CANCEL_ALREADY_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_pay_button:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_PAY_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_CANCEL_ALREADY_RESERVATION);
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
        intent = getIntent();
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
                    latitude = result.getLoc().getCoordinates().get(1);
                    longitude = result.getLoc().getCoordinates().get(0);
                    price = result.getPrice().getMonth();
                    Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), imageURL, 6, image);
                    switch (result.getType()) {
                        case "A": typeCheck1.setChecked(true); break;
                        case "B": typeCheck2.setChecked(true); break;
                        case "C": typeCheck3.setChecked(true); break;
                        case "D": typeCheck4.setChecked(true); break;
                        case "E": typeCheck5.setChecked(true); break;
                        case "F": typeCheck6.setChecked(true); break;
                        case "G": typeCheck7.setChecked(true); break;
                        default:  typeCheck1.setChecked(true); break;
                    }
                    switch (result.getHeight()) {
                        case "01": heightCheck1.setChecked(true); break;
                        case "02": heightCheck2.setChecked(true); break;
                        case "03": heightCheck3.setChecked(true); break;
                        case "04": heightCheck4.setChecked(true); break;
                        case "05": heightCheck5.setChecked(true); break;
                        case "06": heightCheck6.setChecked(true); break;
                        default:  heightCheck1.setChecked(true); break;
                    }
                    rentalPlaceText.setText("위도 : " + latitude + ", 경도 : " + longitude);
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
                    inputPpostScriptionButton.setVisibility(View.GONE);
                    break;
                case "RS":
                case "RC":
                    cancelButton.setVisibility(View.GONE);
                    payButton.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.VISIBLE);
                    inputPpostScriptionButton.setVisibility(View.GONE);
                    break;
            }
        } else {
            cancelButton.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            cancelButton2.setVisibility(View.GONE);
            inputPpostScriptionButton.setVisibility(View.VISIBLE);
        }
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
