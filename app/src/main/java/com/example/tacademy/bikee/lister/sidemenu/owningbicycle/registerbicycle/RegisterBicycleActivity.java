package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.Context;
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
//import com.tsengvn.typekit.TypekitContextWrapper;

public class RegisterBicycleActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private Fragment[] list = {
            RegisterBicycleInformationFragment.newInstance(),
            RegisterBicycleLocationFragment.newInstance(),
            RegisterBicycleIntroductionFragment.newInstance(),
            RegisterBicyclePictureFragment.newInstance(),
            RegisterBicycleFeeFragment.newInstance()
    };
    private RegisterBicycleItem item;
    public static final String ITEM_TAG = "ITEM";
    final private static int FINALLY_REGISTER_BICYCLE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bicycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_register_bicycle_information_container, list[0]).commit();
        }
        Button btn = (Button) findViewById(R.id.fragment_register_bicycle_next_button);
        btn.setOnClickListener(RegisterBicycleActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_register_bicycle_next_button:
                int page = getSupportFragmentManager().getBackStackEntryCount();
                if (page < list.length - 1) {
                    Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> page : " + (page + 2), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_bicycle_information_container, list[page + 1]).addToBackStack(null).commit();
                } else {
                    item = new RegisterBicycleItem();
                    item.setType(((RegisterBicycleInformationFragment) list[0]).getType());
                    item.setHeight(((RegisterBicycleInformationFragment) list[0]).getHeight());
                    item.setName(((RegisterBicycleIntroductionFragment) list[2]).getName());
                    item.setFile1(((RegisterBicyclePictureFragment) list[3]).getFile1());
                    item.setFile2(((RegisterBicyclePictureFragment) list[3]).getFile2());
                    item.setHour(((RegisterBicycleFeeFragment) list[4]).getHour());
                    item.setDay(((RegisterBicycleFeeFragment) list[4]).getDay());
                    item.setMonth(((RegisterBicycleFeeFragment) list[4]).getMonth());

                    intent = new Intent(RegisterBicycleActivity.this, FinallyRegisterBicycleActivity.class);
                    intent.putExtra(ITEM_TAG, item);
                    startActivityForResult(intent, FINALLY_REGISTER_BICYCLE_ACTIVITY);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int page = getSupportFragmentManager().getBackStackEntryCount();
            if (page == 0) {
                Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> finish()", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> page : " + page, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FINALLY_REGISTER_BICYCLE_ACTIVITY) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }
}
