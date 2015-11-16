package com.example.tacademy.bikee.renter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.common.chatting.ChattingRoomListFragment;
import com.example.tacademy.bikee.lister.ListerMainActivity;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.sidemenu.SignInActivity;
import com.example.tacademy.bikee.common.smartkey.SmartKeyFragment;
import com.example.tacademy.bikee.renter.reservationbicycle.RenterReservationBicycleListFragment;
import com.example.tacademy.bikee.renter.searchresult.SearchResultFragment;
import com.example.tacademy.bikee.common.sidemenu.AuthenticationInformationActivity;
import com.example.tacademy.bikee.renter.sidemenu.cardmanagement.CardManagementActivity;
import com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle.EvaluatingBicyclePostScriptListActivity;
import com.example.tacademy.bikee.common.sidemenu.InputInquiryActivity;

public class RenterMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DrawerLayout.DrawerListener, TabHost.OnTabChangeListener{
    private FragmentTabHost tabHost;
    private ImageView iv;
    private ImageView btt_iv1, btt_iv2, btt_iv3, btt_iv4;
    private TextView tv;
    private Button btn;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.renter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        setBottomTabImage();
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(btt_iv1), SearchResultFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), RenterReservationBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), ChattingRoomListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(btt_iv4), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerListener(this);

        iv = (ImageView) findViewById(R.id.renter_side_menu_renter_image_image_view);
        iv.setOnClickListener(this);
        Util.setCircleImageFromURL(this, "http://bikee.s3.amazonaws.com/detail_1446776196619.jpg", 0, iv);
        tv = (TextView) findViewById(R.id.renter_side_menu_member_name_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_mail_address_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_fragment_register_card_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_evaluation_bicycle_script_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_authentication_information_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_push_alarm_text_view);
        tv.setOnClickListener(this);
        cb = (CheckBox) findViewById(R.id.renter_side_menu_push_alarm_switch);
        cb.setOnCheckedChangeListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_input_inquiry_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_version_information_text_view);
        tv.setOnClickListener(this);
        btn = (Button) findViewById(R.id.renter_side_menu_change_mode_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.renter_side_menu_renter_image_image_view:
            case R.id.renter_side_menu_member_name_text_view:
            case R.id.renter_side_menu_mail_address_text_view:
                intent = new Intent(RenterMainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_fragment_register_card_text_view:
                intent = new Intent(RenterMainActivity.this, CardManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_evaluation_bicycle_script_text_view:
                intent = new Intent(RenterMainActivity.this, EvaluatingBicyclePostScriptListActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_authentication_information_text_view:
                intent = new Intent(RenterMainActivity.this, AuthenticationInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_push_alarm_text_view:
                break;
            case R.id.renter_side_menu_input_inquiry_text_view:
                intent = new Intent(RenterMainActivity.this, InputInquiryActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_version_information_text_view:
                break;
            case R.id.renter_side_menu_change_mode_button:
                intent = new Intent(RenterMainActivity.this, ListerMainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.renter_side_menu_push_alarm_switch:
                Toast.makeText(RenterMainActivity.this, "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(RenterMainActivity.this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setBottomTabImage() {
        btt_iv1 = new ImageView(this);
        btt_iv2 = new ImageView(this);
        btt_iv3 = new ImageView(this);
        btt_iv4 = new ImageView(this);

        if (Build.VERSION.SDK_INT < 23) {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon2));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon3));
            btt_iv4.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon4));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1_in));
        } else {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1, getTheme()));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon2, getTheme()));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon3, getTheme()));
            btt_iv4.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon4, getTheme()));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1_in, getTheme()));
        }
    }

    @Override
         public void onTabChanged(String tabId) {
        btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1));
        btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon2));
        btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon3));
        btt_iv4.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon4));
        switch (tabId){
            case "tab1": btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1_in)); break;
            case "tab2": btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon2_in)); break;
            case "tab3": btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon3_in)); break;
            case "tab4": btt_iv4.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon4_in)); break;
        }
    }
}