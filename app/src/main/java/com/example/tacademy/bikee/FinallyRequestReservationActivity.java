package com.example.tacademy.bikee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinallyRequestReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_request_reservation);

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
    }

    public void onCustomDialog1(View view) {
        ChoiceDialogFragment dialog = new ChoiceDialogFragment();
        dialog.setMessage("주인장에게 예약요청을 하시겠습니까?", 0);
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void onCustomDialog2(View view) {
        ChoiceDialogFragment dialog = new ChoiceDialogFragment();
        dialog.setMessage("예약을 정말 취소하시겠습니까?", 1);
        dialog.show(getSupportFragmentManager(), "custom");
    }
}
