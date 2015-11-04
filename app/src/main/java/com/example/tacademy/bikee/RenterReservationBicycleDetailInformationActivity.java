package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RenterReservationBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_bicycle_detail_information);

        Intent intent = getIntent();
        int i = intent.getIntExtra("STATE", -1);
        Toast.makeText(RenterReservationBicycleDetailInformationActivity.this, "STATE : " + i, Toast.LENGTH_SHORT).show();
        if(i == 0) {
            btn = (Button)findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);                  btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);                     btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);                 btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);    btn.setVisibility(View.GONE);
        } else if(i == 1) {
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);                  btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);                     btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);                 btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);    btn.setVisibility(View.GONE);
        } else if(i == 2) {
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);                  btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);                     btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);                 btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);    btn.setVisibility(View.VISIBLE);
        }
        btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button);                  btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_pay_button);                     btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2);                 btn.setOnClickListener(this);
        btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button);    btn.setOnClickListener(this);

        btn = (Button)findViewById(R.id.activity_renter_reservation_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RenterReservationBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button:
                finish();
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_pay_button:
                // TODO
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_cancel_button2:
                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
                dialog.setMessage("예약을 정말 취소하시겠습니까?", 1);
                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                break;
            case R.id.activity_renter_reservation_bicycle_detail_information_input_post_scription_button:
                Intent intent = new Intent(RenterReservationBicycleDetailInformationActivity.this, InputBicyclePostScriptActivity.class);
                startActivity(intent);
                break;
        }
    }
}
