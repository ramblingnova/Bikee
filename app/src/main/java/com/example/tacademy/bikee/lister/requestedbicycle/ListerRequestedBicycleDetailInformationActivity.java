package com.example.tacademy.bikee.lister.requestedbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dialog.NoChoiceDialogFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListerRequestedBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ChoiceDialogFragment dialog1;
    private NoChoiceDialogFragment dialog2;
    private Intent intent;
    private Button cancelButton;
    private Button approvalButton;
    private Button cancelButton2;
    private Button inputPostBackButton;
    private Button smallMapButton;
    private String imageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private String startDate;
    private String endDate;
    private int price;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_requested_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lister_requested_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        intent = getIntent();
        imageURL = intent.getStringExtra("BICYCLEURL");
        type = intent.getStringExtra("TYPE");
        height = intent.getStringExtra("HEIGHT");
        latitude = intent.getDoubleExtra("LATITUDE", -1.0);
        longitude = intent.getDoubleExtra("LONGITUDE", -1.0);
        startDate = intent.getStringExtra("STARTDATE");
        endDate = intent.getStringExtra("ENDDATE");
        price = intent.getIntExtra("PRICE", -1);
        status = intent.getStringExtra("STATUS");
        Log.i("result", "BICYCLEURL : " + imageURL
                        + ", TYPE : " + type
                        + ", HEIGHT : " + height
                        + ", LATITUDE : " + latitude
                        + ", LONGITUDE : " + longitude
                        + ", STARTDATE : " + startDate
                        + ", ENDDATE : " + endDate
                        + ", PRICE : " + price
                        + ", STATUS : " + status
        );
        cancelButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
        cancelButton.setOnClickListener(this);
        approvalButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
        approvalButton.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
        cancelButton2.setOnClickListener(this);
        inputPostBackButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
        inputPostBackButton.setOnClickListener(this);
        smallMapButton = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_small_map_button);
        smallMapButton.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
        Date currentDate = new Date(System.currentTimeMillis());
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = dateFormat.parse(startDate);
            endTime = dateFormat.parse(endDate);
        } catch (ParseException pe) {
            // pe.printStackTrace();
        }
//
//        if (currentDate.after(endTime) == false) {


        switch (status) {
            case "RR":
                if (currentDate.after(startTime)) {
                    // 요청 취소
                } else {
                    // 예약 요청
                    cancelButton.setVisibility(View.VISIBLE);
                    approvalButton.setVisibility(View.VISIBLE);
                    cancelButton2.setVisibility(View.GONE);
                    inputPostBackButton.setVisibility(View.GONE);
                }
                break;
            case "RS":
                if (currentDate.after(startTime)) {
                    cancelButton.setVisibility(View.GONE);
                    approvalButton.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.GONE);
                    inputPostBackButton.setVisibility(View.VISIBLE);
                } else {
                    // 예약 승인
                }
            case "RC":
                cancelButton.setVisibility(View.GONE);
                approvalButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.VISIBLE);
                inputPostBackButton.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button:
                dialog1 = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.LISTER_CANCEL_RESERVATION);
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_approval_button:
                dialog2 = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.LISTER_APPROVE_RESERVATION, NoChoiceDialogFragment.LISTER_MOVE_TO_LISTER_REQUESTED);
                dialog2.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button2:
                dialog1 = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.LISTER_CANCEL_RESERVATION);
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button:
                finish();
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_small_map_button:
                Intent intent = new Intent(ListerRequestedBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
                break;
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
