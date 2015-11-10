package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.etc.dialog.ChoiceDialogFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;

public class FinallyRequestReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_request_reservation);
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_finally_request_reservation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn = (Button) findViewById(R.id.activity_finally_request_reservation_cancel_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomDialog2(v);
            }
        });
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_confirm_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomDialog1(v);
            }
        });
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinallyRequestReservationActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onCustomDialog1(View view) {
        // TODO 싱글탑? 아무튼 한 번에 꺼지는게 맞는 듯...
        ChoiceDialogFragment dialog = new ChoiceDialogFragment();
        dialog.setMessage("주인장에게 예약요청을 하시겠습니까?", 0);
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void onCustomDialog2(View view) {
        ChoiceDialogFragment dialog = new ChoiceDialogFragment();
        dialog.setMessage("예약을 정말 취소하시겠습니까?", 1);
        dialog.show(getSupportFragmentManager(), "custom");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}