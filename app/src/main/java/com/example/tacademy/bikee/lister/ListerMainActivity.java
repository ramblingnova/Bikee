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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.common.sidemenu.SignInActivity;
import com.example.tacademy.bikee.common.chatting.ChattingRoomListFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.sidemenu.InputInquiryActivity;
import com.example.tacademy.bikee.common.smartkey.SmartKeyFragment;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.lister.requestedbicycle.ListerRequestedBicycleListFragment;
import com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle.EvaluatedBicyclePostScriptListActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.OwningBicycleListActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;

public class ListerMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TabHost.OnTabChangeListener {
    private FragmentTabHost tabHost;
    private ImageView iv, btt_iv1, btt_iv2, btt_iv3;
    private TextView tv;
    private TextView name;
    private TextView email;
    private Button btn;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lister_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.lister_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.lister_main_tool_bar);

        setBottomTabImage();
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(btt_iv1), ListerRequestedBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), ChattingRoomListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(ListerMainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.lister_activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        iv = (ImageView) findViewById(R.id.lister_side_menu_lister_image_image_view);
        iv.setOnClickListener(this);
        Util.setCircleImageFromURL(this, "http://bikee.s3.amazonaws.com/detail_1446776196619.jpg", 0, iv);
        iv.setOnClickListener(this);
        name = (TextView) findViewById(R.id.lister_side_menu_member_name_text_view);
        name.setOnClickListener(this);
        email = (TextView) findViewById(R.id.lister_side_menu_mail_address_text_view);
        email.setOnClickListener(this);
        if (!PropertyManager.getInstance().getEmail().equals("") || !PropertyManager.getInstance().getPassword().equals("") ) {
            // TODO 이름 띄우기
            email.setText(PropertyManager.getInstance().getEmail());
        }
        tv = (TextView) findViewById(R.id.lister_side_menu_see_my_bicycle_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_register_smart_lock_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_receive_payment_information_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_evaluated_bicycle_script_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_authentication_information_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_push_alarm_text_view);
        tv.setOnClickListener(this);
        cb = (CheckBox) findViewById(R.id.lister_side_menu_push_alarm_switch);
        cb.setOnCheckedChangeListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_input_inquiry_text_view);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_version_information_text_view);
        tv.setOnClickListener(this);
        btn = (Button) findViewById(R.id.lister_side_menu_change_mode_button);
        btn.setOnClickListener(ListerMainActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.lister_side_menu_lister_image_image_view:
            case R.id.lister_side_menu_member_name_text_view:
            case R.id.lister_side_menu_mail_address_text_view:
                intent = new Intent(ListerMainActivity.this, SignInActivity.class);
                startActivity(intent);
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
                startActivity(intent);
                break;
            case R.id.lister_side_menu_version_information_text_view:
                break;
            case R.id.lister_side_menu_change_mode_button: {
                Toast.makeText(this, "renter_side_menu_change_mode", Toast.LENGTH_SHORT).show();
                intent = new Intent(ListerMainActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.lister_side_menu_push_alarm_switch:
                Toast.makeText(ListerMainActivity.this, "asdfasdf", Toast.LENGTH_SHORT).show();
                break;
        }
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

    private void setBottomTabImage() {
        btt_iv1 = new ImageView(this);
        btt_iv2 = new ImageView(this);
        btt_iv3 = new ImageView(this);

        if (Build.VERSION.SDK_INT < 23) {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1));
        } else {
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1, getTheme()));
            btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2, getTheme()));
            btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3, getTheme()));
            btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1, getTheme()));
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1));
        btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2));
        btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3));
        switch (tabId) {
            case "tab1":
                btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon1_1));
                break;
            case "tab2":
                btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon2_1));
                break;
            case "tab3":
                btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.lister_main_menu_icon3_1));
                break;
        }
    }
}
