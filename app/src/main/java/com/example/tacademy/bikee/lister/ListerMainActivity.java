package com.example.tacademy.bikee.lister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.tacademy.bikee.common.chatting.ChattingRoomListFragment;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.smartkey.SmartKeyFragment;
import com.example.tacademy.bikee.lister.requestedbicycle.ListerRequestedBicycleListFragment;
import com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle.EvaluatedBicyclePostScriptListActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.OwningBicycleListActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;

public class ListerMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    FragmentTabHost tabHost;
    ImageView iv;
    TextView tv;
    Button btn;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lister_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.lister_toolbar);
        setSupportActionBar(toolbar);

        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("예약관리"), ListerRequestedBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("메세지"), ChattingRoomListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("스마트락"), SmartKeyFragment.class, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.lister_activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        iv = (ImageView) findViewById(R.id.lister_side_menu_lister_image);
        iv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_fragment_see_my_bicycle);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_fragment_register_smart_lock);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_fragment_receive_payment_information);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_evaluation_bicycle_script);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_authentication_information);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_push_alarm);
        tv.setOnClickListener(this);
        sw = (Switch) findViewById(R.id.lister_side_menu_push_alarm_switch);
        sw.setOnCheckedChangeListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_input_inquiry);
        tv.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.lister_side_menu_version_information);
        tv.setOnClickListener(this);
        btn = (Button) findViewById(R.id.lister_side_menu_change_mode);
        btn.setOnClickListener(ListerMainActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.lister_side_menu_lister_image: {
                break;
            }
            case R.id.lister_side_menu_fragment_see_my_bicycle: {
                intent = new Intent(ListerMainActivity.this, OwningBicycleListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lister_side_menu_fragment_register_smart_lock:
                break;
            case R.id.lister_side_menu_fragment_receive_payment_information:
                break;
            case R.id.lister_side_menu_evaluation_bicycle_script: {
                intent = new Intent(ListerMainActivity.this, EvaluatedBicyclePostScriptListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lister_side_menu_authentication_information:
                break;
            case R.id.lister_side_menu_push_alarm:
                break;
            case R.id.lister_side_menu_push_alarm_switch:
                break;
            case R.id.lister_side_menu_input_inquiry:
                break;
            case R.id.lister_side_menu_version_information:
                break;
            case R.id.lister_side_menu_change_mode: {
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
