package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterBicycleActivity extends AppCompatActivity {

    Fragment[] list = {
            RegisterBicycleLocationFragment.newInstance("two"),
            RegisterBicycleIntroductionFragment.newInstance("three"),
            RegisterBicyclePictureFragment.newInstance("four"),
            RegisterBicycleFeeFragment.newInstance("five")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bicycle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_register_bicycle_information_container, RegisterBicycleInformationFragment.newInstance("base")).commit();
        }

        Button btn = (Button) findViewById(R.id.fragment_register_bicycle_next_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count < list.length) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_bicycle_information_container, list[count]).addToBackStack(null).commit();
                } else {
//                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Intent intent = new Intent(RegisterBicycleActivity.this, FinallyRegisterBicycleActivity.class);
                    startActivity(intent);
//                    intent = getIntent();
//                    if(intent.getBooleanExtra("close", true)) {
//                        finish();
//                    }
                }
            }
        });
    }
}
