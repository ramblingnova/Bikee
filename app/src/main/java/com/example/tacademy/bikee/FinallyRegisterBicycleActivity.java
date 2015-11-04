package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinallyRegisterBicycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_register_bicycle);

        Button btn = (Button) findViewById(R.id.activity_finally_register_bicycle_back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("close", false);
                finish();
            }
        });
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_ok_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("close", true);
                finish();
            }
        });
        btn = (Button) findViewById(R.id.activity_finally_register_bicycle_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinallyRegisterBicycleActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }
}
