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

import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.dialog.NoChoiceDialogFragment;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private String bicycleId;
    private String reserveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_requested_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lister_requested_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.lister_backable_tool_bar, null);
        cView.findViewById(R.id.lister_backable_tool_bar_back_button_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.lister_backable_tool_bar_back_button_layout:
                        finish();
                        break;
                }
            }
        });
        getSupportActionBar().setCustomView(cView);

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

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button:
                dialog1 = new ChoiceDialogFragment().newInstance(
                        bicycleId,
                        reserveId,
                        "RC",
                        ChoiceDialogFragment.LISTER_CANCEL_RESERVATION
                );
                dialog1.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_approval_button:
                dialog2 = new NoChoiceDialogFragment().newInstance(
                        bicycleId,
                        reserveId,
                        "RS",
                        NoChoiceDialogFragment.LISTER_APPROVE_RESERVATION,
                        NoChoiceDialogFragment.LISTER_MOVE_TO_LISTER_REQUESTED
                );
                dialog2.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button2:
                dialog1 = new ChoiceDialogFragment().newInstance(
                        bicycleId,
                        reserveId,
                        "RC",
                        ChoiceDialogFragment.LISTER_CANCEL_RESERVATION
                );
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

    private void init() {
        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });

        status = intent.getStringExtra("STATUS");
        endDate = intent.getStringExtra("ENDDATE");
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
                    approvalButton.setVisibility(View.VISIBLE);
                    cancelButton2.setVisibility(View.GONE);
                    inputPostBackButton.setVisibility(View.GONE);
                    break;
                case "RS":
                case "RC":
                    cancelButton.setVisibility(View.GONE);
                    approvalButton.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.VISIBLE);
                    inputPostBackButton.setVisibility(View.GONE);
                    break;
            }
        } else {
            cancelButton.setVisibility(View.GONE);
            approvalButton.setVisibility(View.GONE);
            cancelButton2.setVisibility(View.GONE);
            inputPostBackButton.setVisibility(View.VISIBLE);
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
