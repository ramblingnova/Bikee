package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation;

import android.content.Context;
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
//import com.tsengvn.typekit.TypekitContextWrapper;

public class FinallyRequestReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private ChoiceDialogFragment dialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_request_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_finally_request_reservation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        Button btn = (Button) findViewById(R.id.activity_finally_request_reservation_cancel_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_confirm_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);
        btn = (Button) findViewById(R.id.activity_finally_request_reservation_small_map_button);
        btn.setOnClickListener(FinallyRequestReservationActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_finally_request_reservation_cancel_button:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_CANCEL_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_finally_request_reservation_confirm_button:
                dialog = new ChoiceDialogFragment().newInstance(ChoiceDialogFragment.RENTER_REQUEST_RESERVATION);
                dialog.show(getSupportFragmentManager(), "custom");
                break;
            case R.id.activity_finally_request_reservation_small_map_button:
                intent = new Intent(FinallyRequestReservationActivity.this, SmallMapActivity.class);
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
