package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page1.RegisterBicycleInformationFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page2.RegisterBicycleLocationFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page3.RegisterBicycleIntroductionFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.RegisterBicyclePictureFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page5.RegisterBicycleFeeFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    @Bind(R.id.lister_backable_addable_tool_bar_top_num1_image_view)
    ImageView page0;
    @Bind(R.id.lister_backable_addable_tool_bar_top_num2_image_view)
    ImageView page1;
    @Bind(R.id.lister_backable_addable_tool_bar_top_num3_image_view)
    ImageView page2;
    @Bind(R.id.lister_backable_addable_tool_bar_top_num4_image_view)
    ImageView page3;
    @Bind(R.id.lister_backable_addable_tool_bar_top_num5_image_view)
    ImageView page4;
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
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bicycle);

        toolbar = (Toolbar) findViewById(R.id.activity_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_backable_page_movable_tool_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_register_bicycle_information_container, list[0]).commit();
        }

        final Button btn = (Button) findViewById(R.id.fragment_register_bicycle_next_button);
        btn.setOnClickListener(RegisterBicycleActivity.this);
        btn.setEnabled(false);

        RegisterBicycleInformationFragment registerBicycleInformationFragment = (RegisterBicycleInformationFragment) list[0];
        RegisterBicycleLocationFragment registerBicycleLocationFragment = (RegisterBicycleLocationFragment) list[1];
        RegisterBicycleIntroductionFragment registerBicycleIntroductionFragment = (RegisterBicycleIntroductionFragment) list[2];
        RegisterBicyclePictureFragment registerBicyclePictureFragment = (RegisterBicyclePictureFragment) list[3];
        RegisterBicycleFeeFragment registerBicycleFeeFragment = (RegisterBicycleFeeFragment) list[4];

        registerBicycleInformationFragment.setRegisterBicycleINF(registerBicycleINF = new RegisterBicycleINF() {
            @Override
            public void setEnable(boolean b) {
                btn.setEnabled(b);
            }

            @Override
            public boolean getEnable() {
                return btn.isEnabled();
            }
        });
        registerBicycleLocationFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicycleIntroductionFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicyclePictureFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicycleFeeFragment.setRegisterBicycleINF(registerBicycleINF);

        ButterKnife.bind(this);

        refreshTopPageNumber(0);

        imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
    }

    @OnClick(R.id.lister_backable_page_movable_tool_bar_back_button_layout)
    void back() {
        int page = getSupportFragmentManager().getBackStackEntryCount();
        if (page > 0) {
            refreshTopPageNumber(page - 1);
        }
        if (imm.isActive() == true) {
            Log.i("isActive() : ", "" +imm.isActive());
//            imm.hideSoftInputFromWindow(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        int page = getSupportFragmentManager().getBackStackEntryCount();
        if (page > 0) {
            refreshTopPageNumber(page - 1);
        }
        if (imm.isActive() == true) {
            Log.i("isActive() : ", "" +imm.isActive());
//            imm.hideSoftInputFromWindow(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        super.onBackPressed();
    }

    RegisterBicycleINF registerBicycleINF;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_register_bicycle_next_button:
                Button btn = (Button) v.findViewById(R.id.fragment_register_bicycle_next_button);
                btn.setEnabled(false);

                int page = getSupportFragmentManager().getBackStackEntryCount();
                if (page < list.length - 1) {
//                    Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> page : " + (page + 2), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_bicycle_information_container, list[page + 1]).addToBackStack(null).commit();
                    refreshTopPageNumber(page + 1);
                } else {
                    item = new RegisterBicycleItem();
                    item.setType(((RegisterBicycleInformationFragment) list[0]).getType());
                    item.setComponents(((RegisterBicycleInformationFragment) list[0]).getComponents());
                    item.setHeight(((RegisterBicycleInformationFragment) list[0]).getHeight());
                    item.setLatitude(((RegisterBicycleLocationFragment) list[1]).getLatitude());
                    item.setLongitude(((RegisterBicycleLocationFragment) list[1]).getLongitude());
                    item.setName(((RegisterBicycleIntroductionFragment) list[2]).getName());
                    item.setIntroduction(((RegisterBicycleIntroductionFragment) list[2]).getIntroduction());
                    item.setFiles(((RegisterBicyclePictureFragment) list[3]).getFiles());
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

    private void refreshTopPageNumber(int page) {
        page0.setImageResource(R.drawable.topnum_1);
        page1.setImageResource(R.drawable.topnum_2);
        page2.setImageResource(R.drawable.topnum_3);
        page3.setImageResource(R.drawable.topnum_4);
        page4.setImageResource(R.drawable.topnum_5);
        switch (page) {
            case 0:
                page0.setImageResource(R.drawable.topnum_1_this);
                break;
            case 1:
                page1.setImageResource(R.drawable.topnum_2_this);
                break;
            case 2:
                page2.setImageResource(R.drawable.topnum_3_this);
                break;
            case 3:
                page3.setImageResource(R.drawable.topnum_4_this);
                break;
            case 4:
                page4.setImageResource(R.drawable.topnum_5_this);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            int page = getSupportFragmentManager().getBackStackEntryCount();
            if (page == 0) {
//                Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> finish()", Toast.LENGTH_SHORT).show();
                finish();
            } else {
//                Toast.makeText(RegisterBicycleActivity.this, "page : " + (page + 1) + " -> page : " + page, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
