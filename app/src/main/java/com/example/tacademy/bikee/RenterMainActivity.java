package com.example.tacademy.bikee;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class RenterMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    FragmentTabHost tabHost;
    ImageView iv;
    TextView tv;
    Button btn;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.renter_toolbar);
        setSupportActionBar(toolbar);

//        Intent intent = getIntent();
//        if(intent.getBooleanExtra("switch_onoff",false)){
//
//        }

        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("검색"), SearchResultFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("예약"), RenterReservationBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("메세지"), ChattingRoomListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("스마트락"), SmartKeyFragment.class, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        iv = (ImageView) findViewById(R.id.renter_side_menu_renter_image);
        iv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_fragment_register_card);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_evaluation_bicycle_script);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_authentication_information);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_push_alarm);
        tv.setOnClickListener(this);
        sw = (Switch) findViewById(R.id.renter_side_menu_push_alarm_switch);
        sw.setOnCheckedChangeListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_input_inquiry);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.renter_side_menu_version_information);
        tv.setOnClickListener(this);
        btn = (Button) findViewById(R.id.renter_side_menu_change_mode);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.renter_side_menu_renter_image:
                intent = new Intent(RenterMainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_fragment_register_card:
                intent = new Intent(RenterMainActivity.this, CardManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_evaluation_bicycle_script:
                intent = new Intent(RenterMainActivity.this, EvaluatingBicyclePostScriptListActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_authentication_information:
                intent = new Intent(RenterMainActivity.this, AuthenticationInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_push_alarm:
                break;
            case R.id.renter_side_menu_input_inquiry:
                intent = new Intent(RenterMainActivity.this, InputInquiryActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_version_information:
                break;
            case R.id.renter_side_menu_change_mode:
                intent = new Intent(RenterMainActivity.this, ListerMainActivity.class);
                startActivity(intent);
                finish();
//                tabHost.setCurrentTab(0);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.detach(getSupportFragmentManager().findFragmentByTag("tab1"));
//                ft.commit();
//                tabHost.clearAllTabs();
//                tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//                tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("예약"), SmartKeyFragment.class, null);
//                tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("검색"), SearchResultFragment.class, null);
//                tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("메세지"), ChattingRoomListFragment.class, null);
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

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            //displayView(position);
        }
    }
}