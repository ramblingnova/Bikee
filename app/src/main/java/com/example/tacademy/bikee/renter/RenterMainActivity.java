package com.example.tacademy.bikee.renter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.common.chatting.ChattingRoomListFragment;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
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
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RenterMainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener, TabHost.OnTabChangeListener {
    // TODO : get filter result and send to filter result, handle shadow
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost tabHost;
    private ImageView btt_iv1, btt_iv2, btt_iv3, btt_iv4;
    @Bind(R.id.renter_side_menu_renter_image_image_view)
    ImageView renterImage;
    @Bind(R.id.renter_side_menu_member_name_text_view)
    TextView nameTextView;
    @Bind(R.id.renter_side_menu_mail_address_text_view)
    TextView emailTextView;
    private Intent intent;

    final public static String from = "RENTER";
    private static final String TAG = "RENTER_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_activity_main);

        toolbar = (Toolbar) findViewById(R.id.renter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);

        drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setBottomTabImage();
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(btt_iv1), SearchResultFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), RenterReservationBicycleListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), ChattingRoomListFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(btt_iv4), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(this);

        ButterKnife.bind(this);

        initProfile();
    }

    @OnClick(R.id.renter_main_tool_bar_hamburger_icon_layout)
    void clickHamburgerIcon() {
        drawer.openDrawer(Gravity.LEFT);
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

    private void initProfile() {
        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getName().equals("")) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, PropertyManager.getInstance().getImage());
            ImageUtil.setCircleImageFromURL(
                    this,
                    PropertyManager.getInstance().getImage(),
                    R.drawable.noneimage,
                    0,
                    renterImage
            );
            nameTextView.setText(PropertyManager.getInstance().getName());
            emailTextView.setText(PropertyManager.getInstance().getEmail());
        } else {
            ImageUtil.setCircleImageFromURL(
                    this,
                    "https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png",
                    R.drawable.noneimage,
                    0,
                    renterImage
            );
            nameTextView.setText(R.string.renter_side_menu_member_name_text_view_string);
            emailTextView.setText(R.string.renter_side_menu_mail_address_text_view_string);
        }
    }

    @OnClick({R.id.renter_side_menu_renter_image_image_view,
            R.id.renter_side_menu_member_name_text_view,
            R.id.renter_side_menu_mail_address_text_view,
            R.id.renter_side_menu_fragment_register_card_text_view,
            R.id.renter_side_menu_evaluation_bicycle_script_text_view,
            R.id.renter_side_menu_authentication_information_text_view,
            R.id.renter_side_menu_push_alarm_text_view,
            R.id.renter_side_menu_input_inquiry_text_view,
            R.id.renter_side_menu_version_information_text_view,
            R.id.renter_side_menu_change_mode_layout,
            R.id.renter_side_menu_change_mode_button})
    void selectRenterSideMenu(View v) {
        switch (v.getId()) {
            case R.id.renter_side_menu_renter_image_image_view:
            case R.id.renter_side_menu_member_name_text_view:
            case R.id.renter_side_menu_mail_address_text_view:
                intent = new Intent(RenterMainActivity.this, SignInActivity.class);
                intent.putExtra("FROM", from);
                startActivityForResult(intent, SignInActivity.SIGN_IN_ACTIVITY);
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
                intent.putExtra("FROM", from);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_version_information_text_view:
                break;
            case R.id.renter_side_menu_change_mode_layout:
            case R.id.renter_side_menu_change_mode_button:
                intent = new Intent(RenterMainActivity.this, ListerMainActivity.class);
                startActivity(intent);
                finish();
                break;
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
        switch (tabId) {
            case "tab1":
                btt_iv1.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon1_in));
                break;
            case "tab2":
                btt_iv2.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon2_in));
                break;
            case "tab3":
                btt_iv3.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon3_in));
                break;
            case "tab4":
                btt_iv4.setImageDrawable(getResources().getDrawable(R.drawable.rider_main_menu_icon4_in));
                break;
        }
    }

    @OnCheckedChanged(R.id.renter_side_menu_push_alarm_switch)
    void pushAlramCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.renter_side_menu_push_alarm_switch:
                Toast.makeText(RenterMainActivity.this, "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SignInActivity.SIGN_IN_ACTIVITY) {
            initProfile();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}