package com.example.tacademy.bikee.renter.reservationbicycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.R;

public class InputBicyclePostScriptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bicycle_post_script);

        Button btn = (Button)findViewById(R.id.activity_input_bicycle_post_script_input_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
