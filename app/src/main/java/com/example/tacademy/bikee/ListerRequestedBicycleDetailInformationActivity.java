package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListerRequestedBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_requested_bicycle_detail_information);

        Intent intent = getIntent();
        int i = intent.getIntExtra("STATE", -1);
        Toast.makeText(ListerRequestedBicycleDetailInformationActivity.this, "STATE : " + i, Toast.LENGTH_SHORT).show();
        if (i == 0) {
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
            btn.setVisibility(View.VISIBLE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
            btn.setVisibility(View.VISIBLE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
            btn.setVisibility(View.GONE);
        } else if (i == 1) {
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
            btn.setVisibility(View.VISIBLE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
            btn.setVisibility(View.GONE);
        } else if (i == 2) {
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
            btn.setVisibility(View.GONE);
            btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
            btn.setVisibility(View.VISIBLE);
        }
        btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_approval_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_cancel_button2);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_lister_requested_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListerRequestedBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button: {
                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
                dialog.setMessage("xxx님에 대한 예약을 정말 취소하시겠습니까?", 1);
                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                break;
            }
            case R.id.activity_lister_requested_bicycle_detail_information_approval_button: {
                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
                dialog.setMessage("xxx님에 대한 예약이 승인되었습니다.", 1);
                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                // TODO
                break;
            }
            case R.id.activity_lister_requested_bicycle_detail_information_cancel_button2: {
                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
                dialog.setMessage("xxx님에 대한 예약을 정말 취소하시겠습니까?", 1);
                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                break;
            }
            case R.id.activity_lister_requested_bicycle_detail_information_input_post_back_button:
                finish();
                break;
        }
    }
}
