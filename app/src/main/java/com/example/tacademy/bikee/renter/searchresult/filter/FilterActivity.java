package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.timessquare.CalendarPickerView;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.OnDateSelectedListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    // TODO : handle filter and send filter result, handle renew button
    @Bind(R.id.bicycle_location_address_text_view)
    TextView address;
    @Bind(R.id.calendar_content)
    View calendarContent;
    @Bind(R.id.bicycle_type_check_box1)
    CheckBox type1;
    @Bind(R.id.bicycle_type_check_box2)
    CheckBox type2;
    @Bind(R.id.bicycle_type_check_box3)
    CheckBox type3;
    @Bind(R.id.bicycle_type_check_box4)
    CheckBox type4;
    @Bind(R.id.bicycle_type_check_box5)
    CheckBox type5;
    @Bind(R.id.bicycle_type_check_box6)
    CheckBox type6;
    @Bind(R.id.bicycle_type_check_box7)
    CheckBox type7;
    @Bind(R.id.bicycle_recommendation_height_check_box1)
    CheckBox height1;
    @Bind(R.id.bicycle_recommendation_height_check_box2)
    CheckBox height2;
    @Bind(R.id.bicycle_recommendation_height_check_box3)
    CheckBox height3;
    @Bind(R.id.bicycle_recommendation_height_check_box4)
    CheckBox height4;
    @Bind(R.id.bicycle_recommendation_height_check_box5)
    CheckBox height5;
    @Bind(R.id.bicycle_recommendation_height_check_box6)
    CheckBox height6;
    @Bind(R.id.bicycle_order_price_order_check_box)
    CheckBox priceOrderCheckBox;
    @Bind(R.id.bicycle_order_distance_order_check_box)
    CheckBox distanceOrderCheckBox;

    private Intent intent;
    private String latitude;
    private String longitude;
    private String type;
    private String height;
    private boolean smartLock;
    private String order;
    private String startDate;
    private String endDate;

    public static final int FILTER_ACTIVITY = 2;

    private static final String TAG = "FILTER_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_filter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.filter_backable_tool_bar);
        ButterKnife.bind(this);

        intent = getIntent();
        String addressString = intent.getStringExtra("ADDRESS");
        if (addressString.equals("")) {
            View view = findViewById(R.id.activity_filter_bicycle_location);
            view.setVisibility(View.GONE);
        } else {
            address.setText(addressString);
            // TODO : what?
            Toast.makeText(FilterActivity.this, "address : " + RefinementUtil.findGeoPoint(MyApplication.getmContext(), addressString), Toast.LENGTH_SHORT).show();
            LatLng latLng = RefinementUtil.findGeoPoint(MyApplication.getmContext(), addressString);
            latitude = "" + latLng.latitude;
            longitude = "" + latLng.longitude;
        }
    }

    @OnClick(R.id.filter_backable_tool_bar_back_button_layout)
    void back(View view) {
        finish();
    }

    @OnClick(R.id.filter_backable_tool_bar_reset_text_view)
    void reset(View view) {
        Toast.makeText(FilterActivity.this, "RESET!", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.calendar_start_date_summary,
            R.id.calendar_end_date_summary})
    void onClickCalendar(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                FilterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        switch (view.getId()) {
            case R.id.calendar_start_date_summary:
                dpd.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(View view, Date date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm", java.util.Locale.getDefault());
                        Log.d(TAG, "calendar_start_date_summary yyyy-MM-dd hh:mm : " + simpleDateFormat.format(date.getTime()));
                        startDate = simpleDateFormat.format(date.getTime());
                    }
                });
                break;
            case R.id.calendar_end_date_summary:
                dpd.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(View view, Date date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm", java.util.Locale.getDefault());
                        Log.d(TAG, "calendar_end_date_summary yyyy-MM-dd hh:mm : " + simpleDateFormat.format(date.getTime()));
                        endDate = simpleDateFormat.format(date.getTime());
                    }
                });
                break;
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @OnClick({R.id.bicycle_type_check_box1,
            R.id.bicycle_type_check_box2,
            R.id.bicycle_type_check_box3,
            R.id.bicycle_type_check_box4,
            R.id.bicycle_type_check_box5,
            R.id.bicycle_type_check_box6,
            R.id.bicycle_type_check_box7})
    void selectType(View view) {
        type1.setChecked(false);
        type2.setChecked(false);
        type3.setChecked(false);
        type4.setChecked(false);
        type5.setChecked(false);
        type6.setChecked(false);
        type7.setChecked(false);
        switch (view.getId()) {
            case R.id.bicycle_type_check_box1:
                type1.setChecked(true);
                type = "A";
                break;
            case R.id.bicycle_type_check_box2:
                type2.setChecked(true);
                type = "B";
                break;
            case R.id.bicycle_type_check_box3:
                type3.setChecked(true);
                type = "C";
                break;
            case R.id.bicycle_type_check_box4:
                type4.setChecked(true);
                type = "D";
                break;
            case R.id.bicycle_type_check_box5:
                type5.setChecked(true);
                type = "E";
                break;
            case R.id.bicycle_type_check_box6:
                type6.setChecked(true);
                type = "F";
                break;
            case R.id.bicycle_type_check_box7:
                type7.setChecked(true);
                type = "G";
                break;
        }
    }

    @OnClick({R.id.bicycle_recommendation_height_check_box1,
            R.id.bicycle_recommendation_height_check_box2,
            R.id.bicycle_recommendation_height_check_box3,
            R.id.bicycle_recommendation_height_check_box4,
            R.id.bicycle_recommendation_height_check_box5,
            R.id.bicycle_recommendation_height_check_box6})
    void selectHeight(View view) {
        height1.setChecked(false);
        height2.setChecked(false);
        height3.setChecked(false);
        height4.setChecked(false);
        height5.setChecked(false);
        height6.setChecked(false);
        switch (view.getId()) {
            case R.id.bicycle_recommendation_height_check_box1:
                height1.setChecked(true);
                height = "01";
                break;
            case R.id.bicycle_recommendation_height_check_box2:
                height2.setChecked(true);
                height = "02";
                break;
            case R.id.bicycle_recommendation_height_check_box3:
                height3.setChecked(true);
                height = "03";
                break;
            case R.id.bicycle_recommendation_height_check_box4:
                height4.setChecked(true);
                height = "04";
                break;
            case R.id.bicycle_recommendation_height_check_box5:
                height5.setChecked(true);
                height = "05";
                break;
            case R.id.bicycle_recommendation_height_check_box6:
                height6.setChecked(true);
                height = "06";
                break;
        }
    }

    @OnClick({R.id.bicycle_order_price_order_check_box,
            R.id.bicycle_order_distance_order_check_box})
    void selectOrder(View view) {
        switch (view.getId()) {
            case R.id.bicycle_order_price_order_check_box:
                priceOrderCheckBox.setChecked(true);
                distanceOrderCheckBox.setChecked(false);
                break;
            case R.id.bicycle_order_distance_order_check_box:
                priceOrderCheckBox.setChecked(false);
                distanceOrderCheckBox.setChecked(true);
                break;
        }
    }

    @OnCheckedChanged({R.id.bicycle_smart_lock_check_box,
            R.id.bicycle_order_distance_order_check_box,
            R.id.bicycle_order_price_order_check_box})
    void temp(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.bicycle_smart_lock_check_box:
                smartLock = isChecked;
                break;
            case R.id.bicycle_order_price_order_check_box:
                if (isChecked)
                    order = "price";
                break;
            case R.id.bicycle_order_distance_order_check_box:
                if (isChecked)
                    order = "distance";
                break;
        }
    }

    @OnClick(R.id.activity_filter_search_button)
    void searchDetail() {
        intent = new Intent();
        intent.putExtra("LATITUDE", latitude);
        intent.putExtra("LONGITUDE", longitude);
        intent.putExtra("TYPE", type);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("SMART_LOCK", smartLock);
        intent.putExtra("ORDER", order);
        intent.putExtra("START_DATE", startDate);
        intent.putExtra("END_DATE", endDate);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
