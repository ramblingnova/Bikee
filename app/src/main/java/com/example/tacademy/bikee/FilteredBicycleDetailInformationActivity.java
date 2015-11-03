package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FilteredBicycleDetailInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_bicycle_detail_information);

        Button btn = (Button)findViewById(R.id.bicycle_post_script_see_more_post_script);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredBicycleDetailInformationActivity.this, BicyclePostScriptListActivity.class);
                startActivity(intent);
            }
        });
        btn = (Button)findViewById(R.id.activity_filtered_bicycle_detail_information_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredBicycleDetailInformationActivity.this, FinallyRequestReservationActivity.class);
                startActivity(intent);
            }
        });
    }
}
