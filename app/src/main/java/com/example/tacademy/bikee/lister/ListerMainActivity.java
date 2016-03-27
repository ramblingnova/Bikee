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

import com.example.tacademy.bikee.common.chatting.ChattingRoomsFragment;
import com.example.tacademy.bikee.common.sidemenu.SignInActivity;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.sidemenu.InputInquiryActivity;
import com.example.tacademy.bikee.common.smartkey.SmartKeyFragment;
import com.example.tacademy.bikee.etc.SendBirdHelper;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.lister.requestedbicycle.ListerRequestedBicycleListFragment;
import com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle.EvaluatedBicyclePostScriptListActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.OwningBicycleListActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;
import com.sendbird.android.SendBird;
import com.sendbird.android.UserListQuery;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ListerMainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    // TODO : need scenario
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FragmentTabHost tabHost;
    private ImageView btt_iv1, btt_iv2, btt_iv3;
    @Bind(R.id.lister_side_menu_lister_image_image_view)
    ImageView listerImage;
    @Bind(R.id.lister_side_menu_member_name_text_view)
    TextView nameTextView;
    @Bind(R.id.lister_side_menu_mail_address_text_view)
    TextView emailTextView;

    final public static String from = "LISTER";

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

        final String appId = "2E377FE1-E1AD-4484-A66F-696AF1306F58"; /* Sample SendBird Application */
        String userId = SendBirdHelper.generateDeviceUUID(ListerMainActivity.this); /* Generate Device UUID */
        String userName = "User-" + "20B5A"; /* Generate User Nickname */
        String gcmRegToken = "f7x_1qavNuM:APA91bGB8RVUTMtxFbTehOYO-gr5JFUORJQZDLtzAsXoDD_o2ZBqHn_PhqAfzpJwSbY6SF6iY7_mfK4nrEERZsZbq5HuddaVqKPBA6OKBdjJrSTxjEJEyfIzLcJeNpPcgoo0f66cXwxY";

        SendBird.init(this, appId);
        SendBird.login(SendBird.LoginOption.build(userId).setUserName(userName).setGCMRegToken(gcmRegToken));

        UserListQuery mUserListQuery = SendBird.queryUserList();
        mUserListQuery.setLimit(30);

        Bundle args = new Bundle();
        args.putString("APP_ID", appId);
        args.putString("USER_ID", userId);
        args.putString("USER_NAME", userName);
        args.putString("GCM_TOKEN", gcmRegToken);

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(btt_iv2), ChattingRoomsFragment.class, args);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(btt_iv3), SmartKeyFragment.class, null);
        tabHost.setOnTabChangedListener(this);

        ButterKnife.bind(this);

        initProfile();
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
        Intent intent;
        switch (v.getId()) {
            case R.id.lister_side_menu_lister_image_image_view:
            case R.id.lister_side_menu_member_name_text_view:
            case R.id.lister_side_menu_mail_address_text_view:
                intent = new Intent(ListerMainActivity.this, SignInActivity.class);
                intent.putExtra("FROM", from);
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
                intent.putExtra("FROM", from);
                startActivity(intent);
                break;
            case R.id.lister_side_menu_version_information_text_view:
                break;
            case R.id.lister_side_menu_change_mode_layout:
            case R.id.lister_side_menu_change_mode_button: {
//                Toast.makeText(this, "renter_side_menu_change_mode", Toast.LENGTH_SHORT).show();
                intent = new Intent(ListerMainActivity.this, RenterMainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    @Bind(R.id.lister_side_menu_push_alarm_switch)
    CheckBox push;
    @OnCheckedChanged(R.id.lister_side_menu_push_alarm_switch)
    void pushAlramCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.lister_side_menu_push_alarm_switch:
                if (isChecked) {
                    PropertyManager.getInstance().setPushEnable(true);
                } else {
                    PropertyManager.getInstance().setPushEnable(false);
//                    try {
//                        InstanceID.getInstance(this).deleteToken(getString(R.string.GCM_SenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
//                    } catch (Exception e) {}
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SignInActivity.SIGN_IN_ACTIVITY) {
            initProfile();
        }
    }

    private void initProfile() {
        if (!PropertyManager.getInstance().getEmail().equals("")
                || !PropertyManager.getInstance().getName().equals("")) {
            Log.i("Result", PropertyManager.getInstance().getImage());
            ImageUtil.setCircleImageFromURL(
                    this,
                    PropertyManager.getInstance().getImage(),
                    R.drawable.noneimage,
                    0,
                    listerImage
            );
            nameTextView.setText(PropertyManager.getInstance().getName());
            emailTextView.setText(PropertyManager.getInstance().getEmail());
        } else {
            ImageUtil.setCircleImageFromURL(
                    this,
                    "https://s3-ap-northeast-1.amazonaws.com/bikee/KakaoTalk_20151128_194521490.png",
                    R.drawable.noneimage,
                    0,
                    listerImage
            );
            nameTextView.setText(R.string.renter_side_menu_member_name_text_view_string);
            emailTextView.setText(R.string.renter_side_menu_mail_address_text_view_string);
        }

        if (PropertyManager.getInstance().isPushEnable())
            push.setChecked(true);
        else
            push.setChecked(false);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
