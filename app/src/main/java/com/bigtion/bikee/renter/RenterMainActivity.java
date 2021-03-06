package com.bigtion.bikee.renter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.common.chatting.ChattingRoomsFragment;
import com.bigtion.bikee.common.popup.ChoiceDialogFragment;
import com.bigtion.bikee.common.popup.OnApplicationFinish;
import com.bigtion.bikee.etc.utils.ImageUtil;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.lister.ListerMainActivity;
import com.bigtion.bikee.R;
import com.bigtion.bikee.common.sidemenu.SignInActivity;
import com.bigtion.bikee.common.smartkey.SmartKeyFragment;
import com.bigtion.bikee.renter.reservation.RenterReservationsFragment;
import com.bigtion.bikee.renter.searchresult.SearchResultFragment;
import com.bigtion.bikee.renter.searchresult.filter.FilterActivity;
import com.bigtion.bikee.renter.sidemenu.creditcard.CreditCardsActivity;
import com.bigtion.bikee.common.sidemenu.comment.CommentsActivity;
import com.bigtion.bikee.common.sidemenu.InquiryActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RenterMainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener, TabHost.OnTabChangeListener {
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_drawer_icon_image_view)
    ImageView toolbarLeftDrawerIconImageView;
    @Bind(R.id.toolbar_center_icon_image_view)
    ImageView toolbarCenterIconImageView;
    @Bind(R.id.toolbar_right_mode_icon_image_view)
    ImageView toolbarRightModeIconImageView;
    @Bind(R.id.renter_activity_main_drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.renter_side_menu_renter_image_image_view)
    ImageView renterImage;
    @Bind(R.id.renter_side_menu_member_name_text_view)
    TextView nameTextView;
    @Bind(R.id.renter_side_menu_mail_address_text_view)
    TextView emailTextView;
    @Bind(R.id.renter_side_menu_push_alarm_switch)
    CheckBox push;

    private Intent intent;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost tabHost;
    private ImageView btt_iv1, btt_iv2, btt_iv3, btt_iv4;

    public static final String TAG = "RENTER_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.renter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeBlue, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftDrawerIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 오른쪽 */
        toolbarRightModeIconImageView.setImageResource(R.drawable.rider_main_icon);
        toolbarRightModeIconImageView.setVisibility(View.VISIBLE);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        setBottomTabImage();
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(btt_iv1), SearchResultFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), RenterReservationsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), ChattingRoomsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(btt_iv4), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(this);

        initProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SignInActivity.SIGN_IN_ACTIVITY) {
            initProfile();
        } else if (resultCode == RESULT_OK && requestCode == FilterActivity.FILTER_ACTIVITY) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "onActivityResult");
            for (Fragment uploadType : getSupportFragmentManager()
                    .findFragmentById(R.id.realtabcontent)
                    .getFragmentManager()
                    .getFragments()) {
                if (uploadType != null) {
                    uploadType.onActivityResult(requestCode, resultCode, data);
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        android.os.Debug.stopMethodTracing();
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

    @OnClick({R.id.toolbar_left_layout,
            R.id.renter_side_menu_renter_image_image_view,
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
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_layout:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.renter_side_menu_renter_image_image_view:
            case R.id.renter_side_menu_member_name_text_view:
            case R.id.renter_side_menu_mail_address_text_view:
                intent = new Intent(RenterMainActivity.this, SignInActivity.class);
                intent.putExtra("FROM", TAG);
                startActivityForResult(intent, SignInActivity.SIGN_IN_ACTIVITY);
                break;
            case R.id.renter_side_menu_fragment_register_card_text_view:
                intent = new Intent(RenterMainActivity.this, CreditCardsActivity.class);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_evaluation_bicycle_script_text_view:
                intent = new Intent(RenterMainActivity.this, CommentsActivity.class);
                intent.putExtra("FROM", TAG);
                startActivity(intent);
                break;
            case R.id.renter_side_menu_authentication_information_text_view:
                break;
            case R.id.renter_side_menu_push_alarm_text_view:
                break;
            case R.id.renter_side_menu_input_inquiry_text_view:
                intent = new Intent(RenterMainActivity.this, InquiryActivity.class);
                intent.putExtra("FROM", TAG);
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

    @OnCheckedChanged(R.id.renter_side_menu_push_alarm_switch)
    void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.renter_side_menu_push_alarm_switch:
                if (isChecked)
                    PropertyManager.getInstance().setPushEnable(true);
                else
                    PropertyManager.getInstance().setPushEnable(false);
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
                        renterImage
                );
                nameTextView.setText(PropertyManager.getInstance().getName());
                emailTextView.setText(PropertyManager.getInstance().getEmail());
                break;
            case PropertyManager.SIGN_OUT_STATE:
                ImageUtil.setCircleImageFromURL(
                        this,
                        "https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png",
                        R.drawable.noneimage,
                        renterImage
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

    private void finishApplication() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        drawerLayout = (DrawerLayout) findViewById(R.id.renter_activity_main_drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            ChoiceDialogFragment choiceDialogFragment = ChoiceDialogFragment.newInstance(ChoiceDialogFragment.RENTER_APPLICATION_FINISH);
            choiceDialogFragment.setOnApplicationFinish(new OnApplicationFinish() {
                @Override
                public void onApplicationFinish() {
                    finishApplication();
                }
            });
            choiceDialogFragment.show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}