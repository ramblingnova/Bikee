package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
//import com.tsengvn.typekit.TypekitContextWrapper;

public class RenterReservationBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private ChoiceDialogFragment dialog;
    private Button smallMapButton;
    private Button cancelButton;
    private Button payButton;
    private Button cancelButton2;
    private Button inputPpostScriptionButton;

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

        intent = getIntent();
        String status = intent.getStringExtra("STATE");
        Toast.makeText(RenterReservationBicycleDetailInformationActivity.this, "STATE : " + status, Toast.LENGTH_SHORT).show();

        cancelButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);
        cancelButton.setOnClickListener(this);
        payButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);
        payButton.setOnClickListener(this);
        cancelButton2 = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);
        cancelButton2.setOnClickListener(this);
        inputPpostScriptionButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);
        inputPpostScriptionButton.setOnClickListener(this);
        smallMapButton = (Button) findViewById(R.id.activity_renter_reservation_bicycle_detail_information_small_map_button);
        smallMapButton.setOnClickListener(this);
        switch (status) {
            case "RR":
                cancelButton.setVisibility(View.VISIBLE);
                payButton.setVisibility(View.VISIBLE);
                cancelButton2.setVisibility(View.GONE);
                inputPpostScriptionButton.setVisibility(View.GONE);
                break;
            case "RS":
                cancelButton.setVisibility(View.GONE);
                payButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.VISIBLE);
                inputPpostScriptionButton.setVisibility(View.GONE);
                break;
            case "RC":
                cancelButton.setVisibility(View.GONE);
                payButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.GONE);
                inputPpostScriptionButton.setVisibility(View.VISIBLE);
                break;
        }
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
                startActivity(intent);
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_small_map_button:
                intent = new Intent(RenterReservationBicycleDetailInformationActivity.this, SmallMapActivity.class);
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

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }
}
