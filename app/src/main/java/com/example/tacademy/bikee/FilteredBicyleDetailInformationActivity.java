package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FilteredBicyleDetailInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_bicyle_detail_information);

        Button btn = (Button)findViewById(R.id.bicycle_post_script_see_more_post_script);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredBicyleDetailInformationActivity.this, BicylePostScriptListActivity.class);
                startActivity(intent);
            }
        });
        btn = (Button)findViewById(R.id.activity_filtered_bicycle_detail_information_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilteredBicyleDetailInformationActivity.this, FinallyRegisterBicycleActivity.class);
                startActivity(intent);
            }
        });
    }
}
