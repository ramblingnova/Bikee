package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OwningBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owning_bicycle_detail_information);

        Intent intent = getIntent();
        int i = intent.getIntExtra("STATE", -1);
        Toast.makeText(OwningBicycleDetailInformationActivity.this, "STATE : " + i, Toast.LENGTH_SHORT).show();
        if(i == 0) {
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_deactivate_button);                  btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_back_button);                     btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_approval_button);                 btn.setVisibility(View.GONE);
        } else if(i == 1) {
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_deactivate_button);                  btn.setVisibility(View.GONE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_back_button);                     btn.setVisibility(View.VISIBLE);
            btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_approval_button);                 btn.setVisibility(View.VISIBLE);
        }
        btn = (Button)findViewById(R.id.activity_owning_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwningBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_owning_bicycle_detail_information_deactivate_button:
                // TODO
                finish();
                break;
            case R.id.activity_owning_bicycle_detail_information_back_button:
                finish();
                break;
            case R.id.activity_owning_bicycle_detail_information_approval_button:
                ChoiceDialogFragment dialog = new ChoiceDialogFragment();
                dialog.setMessage("예약을 정말 취소하시겠습니까?", 1);
                dialog.show(getSupportFragmentManager(), "custom");
                finish();
                break;
        }
    }
}
