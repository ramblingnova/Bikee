package com.example.tacademy.bikee.lister;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.common.chatting.ChattingRoomsFragment;
import com.example.tacademy.bikee.common.sidemenu.SignInActivity;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.sidemenu.InputInquiryActivity;
import com.example.tacademy.bikee.common.smartkey.SmartKeyFragment;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.lister.requestedbicycle.ListerRequestedBicycleListFragment;
import com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle.EvaluatedBicyclePostScriptListActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.OwningBicycleListActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ListerMainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    @Bind(R.id.lister_side_menu_lister_image_image_view)
    ImageView listerImage;
    @Bind(R.id.lister_side_menu_member_name_text_view)
    TextView nameTextView;
    @Bind(R.id.lister_side_menu_mail_address_text_view)
    TextView emailTextView;
    @Bind(R.id.lister_side_menu_push_alarm_switch)
    CheckBox push;

    private Intent intent;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost tabHost;
    private ImageView btt_iv1, btt_iv2, btt_iv3;

    public static final String from = "LISTER";
    private static final String TAG = "LISTER_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lister_activity_main);

        toolbar = (Toolbar) findViewById(R.id.lister_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        drawer = (DrawerLayout) findViewById(R.id.lister_activity_main_drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setBottomTabImage();
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(btt_iv1), ListerRequestedBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), ChattingRoomsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(this);

        ButterKnife.bind(this);

        initProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SignInActivity.SIGN_IN_ACTIVITY) {
            initProfile();
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1));
        btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2_1));
        btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3_1));
        switch (tabId) {
            case "tab1":
                btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1));
                break;
            case "tab2":
                btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2));
                break;
            case "tab3":
                btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3));
                break;
        }
    }

    @OnClick(R.id.lister_main_tool_bar_hamburger_icon_layout)
    void clickHamburgerIcon() {
        drawer.openDrawer(Gravity.LEFT);
    }

    @OnClick({R.id.lister_side_menu_lister_image_image_view,
            R.id.lister_side_menu_member_name_text_view,
            R.id.lister_side_menu_mail_address_text_view,
            R.id.lister_side_menu_see_my_bicycle_text_view,
            R.id.lister_side_menu_register_smart_lock_text_view,
            R.id.lister_side_menu_receive_payment_information_text_view,
            R.id.lister_side_menu_evaluated_bicycle_script_text_view,
            R.id.lister_side_menu_authentication_information_text_view,
            R.id.lister_side_menu_push_alarm_text_view,
            R.id.lister_side_menu_input_inquiry_text_view,
            R.id.lister_side_menu_version_information_text_view,
            R.id.lister_side_menu_change_mode_layout,
            R.id.lister_side_menu_change_mode_button})
    void selectListerSideMenu(View v) {
        switch (v.getId()) {
            case R.id.lister_side_menu_lister_image_image_view:
            case R.id.lister_side_menu_member_name_text_view:
            case R.id.lister_side_menu_mail_address_text_view:
                intent = new Intent(ListerMainActivity.this, SignInActivity.class);
                intent.putExtra("FROM", from);
                startActivityForResult(intent, SignInActivity.SIGN_IN_ACTIVITY);
                break;
            case R.id.lister_side_menu_see_my_bicycle_text_view: {
                intent = new Intent(ListerMainActivity.this, OwningBicycleListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lister_side_menu_register_smart_lock_text_view:
                break;
            case R.id.lister_side_menu_receive_payment_information_text_view:
                break;
            case R.id.lister_side_menu_evaluated_bicycle_script_text_view: {
                intent = new Intent(ListerMainActivity.this, EvaluatedBicyclePostScriptListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lister_side_menu_authentication_information_text_view:
                break;
            case R.id.lister_side_menu_push_alarm_text_view:
                break;
            case R.id.lister_side_menu_input_inquiry_text_view:
                intent = new Intent(ListerMainActivity.this, InputInquiryActivity.class);
                intent.putExtra("FROM", from);
                startActivity(intent);
                break;
            case R.id.lister_side_menu_version_information_text_view:
                break;
            case R.id.lister_side_menu_change_mode_layout:
            case R.id.lister_side_menu_change_mode_button: {
                intent = new Intent(ListerMainActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    @OnCheckedChanged(R.id.lister_side_menu_push_alarm_switch)
    void pushAlramCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.lister_side_menu_push_alarm_switch:
                if (isChecked) {
                    PropertyManager.getInstance().setPushEnable(true);
                } else {
                    PropertyManager.getInstance().setPushEnable(false);
                }
                break;
        }
    }

    private void setBottomTabImage() {
        btt_iv1 = new ImageView(this);
        btt_iv2 = new ImageView(this);
        btt_iv3 = new ImageView(this);

        if (Build.VERSION.SDK_INT < 23) {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2_1));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3_1));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1));
        } else {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1, getTheme()));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2_1, getTheme()));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3_1, getTheme()));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1, getTheme()));
        }
    }

    private void initProfile() {
        switch (PropertyManager.getInstance().getSignInState()) {
            case PropertyManager.SIGN_IN_FACEBOOK_STATE:
            case PropertyManager.SIGN_IN_LOCAL_STATE:
                if (BuildConfig.DEBUG)
                    Log.d(TAG, PropertyManager.getInstance().getImage());
                ImageUtil.setCircleImageFromURL(
                        this,
                        PropertyManager.getInstance().getImage(),
                        R.drawable.noneimage,
                        0,
                        listerImage
                );
                nameTextView.setText(PropertyManager.getInstance().getName());
                emailTextView.setText(PropertyManager.getInstance().getEmail());
                break;
            case PropertyManager.SIGN_OUT_STATE:
                ImageUtil.setCircleImageFromURL(
                        this,
                        "https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png",
                        R.drawable.noneimage,
                        0,
                        listerImage
                );
                nameTextView.setText(R.string.renter_side_menu_member_name_text_view_string);
                emailTextView.setText(R.string.renter_side_menu_mail_address_text_view_string);
                break;
        }

        if (PropertyManager.getInstance().isPushEnable())
            push.setChecked(true);
        else
            push.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.lister_activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
