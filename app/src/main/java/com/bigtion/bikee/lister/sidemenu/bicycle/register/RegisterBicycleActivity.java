package com.bigtion.bikee.lister.sidemenu.bicycle.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bigtion.bikee.R;
import com.bigtion.bikee.common.content.ContentActivity;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.page1.RegisterBicycleInformationFragment;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.page2.RegisterBicycleLocationFragment;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.page3.RegisterBicycleIntroductionFragment;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.page4.RegisterBicyclePictureFragment;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.page5.RegisterBicycleFeeFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleActivity extends AppCompatActivity {
    @Bind(R.id.register_bicycle_toolbar_page1_image_view)
    ImageView page0;
    @Bind(R.id.register_bicycle_toolbar_page2_image_view)
    ImageView page1;
    @Bind(R.id.register_bicycle_toolbar_page3_image_view)
    ImageView page2;
    @Bind(R.id.register_bicycle_toolbar_page4_image_view)
    ImageView page3;
    @Bind(R.id.register_bicycle_toolbar_page5_image_view)
    ImageView page4;
    @Bind(R.id.fragment_register_bicycle_next_button)
    Button nextButton;

    private Intent intent;
    private Fragment[] list = {
            RegisterBicycleInformationFragment.newInstance(),
            RegisterBicycleLocationFragment.newInstance(),
            RegisterBicycleIntroductionFragment.newInstance(),
            RegisterBicyclePictureFragment.newInstance(),
            RegisterBicycleFeeFragment.newInstance()
    };
    private RegisterBicycleINF registerBicycleINF;
    private RegisterBicycleItem item;

    public static final String ITEM_TAG = "ITEM";
    public static final int from = 8;
    private static final String TAG = "REGISTER_BICYCLE_A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bicycle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_register_bicycle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.register_bicycle_toolbar);

        ButterKnife.bind(this);

        nextButton.setEnabled(false);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_register_bicycle_information_container, list[0]).commit();
        }
        RegisterBicycleInformationFragment registerBicycleInformationFragment = (RegisterBicycleInformationFragment) list[0];
        RegisterBicycleLocationFragment registerBicycleLocationFragment = (RegisterBicycleLocationFragment) list[1];
        RegisterBicycleIntroductionFragment registerBicycleIntroductionFragment = (RegisterBicycleIntroductionFragment) list[2];
        RegisterBicyclePictureFragment registerBicyclePictureFragment = (RegisterBicyclePictureFragment) list[3];
        RegisterBicycleFeeFragment registerBicycleFeeFragment = (RegisterBicycleFeeFragment) list[4];
        registerBicycleInformationFragment.setRegisterBicycleINF(registerBicycleINF = new RegisterBicycleINF() {
            @Override
            public void setEnable(boolean b) {
                nextButton.setEnabled(b);
            }

            @Override
            public boolean getEnable() {
                return nextButton.isEnabled();
            }
        });
        registerBicycleLocationFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicycleIntroductionFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicyclePictureFragment.setRegisterBicycleINF(registerBicycleINF);
        registerBicycleFeeFragment.setRegisterBicycleINF(registerBicycleINF);

        refreshToolbarPageNumber(0);
    }

    @OnClick(R.id.register_bicycle_toolbar_back_button_layout)
    void back() {
        hideKeyboard(this);
        int page = getSupportFragmentManager().getBackStackEntryCount();
        if (page > 0) {
            refreshToolbarPageNumber(page - 1);
        }
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(this);
        int page = getSupportFragmentManager().getBackStackEntryCount();
        if (page > 0) {
            refreshToolbarPageNumber(page - 1);
        }
        super.onBackPressed();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @OnClick(R.id.fragment_register_bicycle_next_button)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_register_bicycle_next_button:
                hideKeyboard(this);
                nextButton.setEnabled(false);

                int page = getSupportFragmentManager().getBackStackEntryCount();
                if (page < list.length - 1) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(
                                    R.id.fragment_register_bicycle_information_container,
                                    list[page + 1]
                            )
                            .addToBackStack(null)
                            .commit();
                    refreshToolbarPageNumber(page + 1);
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

                    intent = new Intent(RegisterBicycleActivity.this, ContentActivity.class);
                    intent.putExtra(ITEM_TAG, item);
                    intent.putExtra("FROM", from);
                    intent.putExtra("BICYCLE_LATITUDE", ((RegisterBicycleLocationFragment) list[1]).getLatitude());
                    intent.putExtra("BICYCLE_LONGITUDE", ((RegisterBicycleLocationFragment) list[1]).getLongitude());

                    startActivityForResult(intent, from);
                }
                break;
        }
    }

    private void refreshToolbarPageNumber(int page) {
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
            if (page == 0)
                finish();
            else
                getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == from) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
