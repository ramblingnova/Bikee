package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

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
        Toolbar toolbar = (Toolbar)findViewById(R.id.activity_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_register_bicycle_information_container, RegisterBicycleInformationFragment.newInstance("base")).commit();
        }

        Button btn = (Button) findViewById(R.id.fragment_register_bicycle_next_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count < list.length) {
                    Toast.makeText(RegisterBicycleActivity.this, "count : " + count, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int count;
            switch (count = getSupportFragmentManager().getBackStackEntryCount()) {
                case 0:
                    Toast.makeText(RegisterBicycleActivity.this, "case : " + count, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 1: case 2: case 3: case 4:
                    Toast.makeText(RegisterBicycleActivity.this, "case : " + count, Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().popBackStack();
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
