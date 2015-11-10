package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation.FinallyRequestReservationActivity;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription.BicyclePostScriptListActivity;

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

        btn = (Button)findViewById(R.id.activity_filtered_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
            }
        });
    }
}
