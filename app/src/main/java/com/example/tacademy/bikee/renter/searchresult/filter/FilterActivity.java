package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.squareup.timessquare.CalendarPickerView;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {
    private Intent intent;
    private CalendarPickerView calendarPickerView, calendarPickerViewTemp;
    private Calendar calendar;
    private Date start, end, select;
    private Animation anim;
    @Bind(R.id.bicycle_location_address_text_view) TextView address;
    private boolean isStartDatePicked = false;
    private boolean isEndDatePicked = false;
    @Bind(R.id.bicycle_type_check_box1) CheckBox type1;
    @Bind(R.id.bicycle_type_check_box2) CheckBox type2;
    @Bind(R.id.bicycle_type_check_box3) CheckBox type3;
    @Bind(R.id.bicycle_type_check_box4) CheckBox type4;
    @Bind(R.id.bicycle_type_check_box5) CheckBox type5;
    @Bind(R.id.bicycle_type_check_box6) CheckBox type6;
    @Bind(R.id.bicycle_type_check_box7) CheckBox type7;
    @Bind(R.id.bicycle_order_price_order_check_box) CheckBox priceOrder;
    @Bind(R.id.bicycle_order_distance_order_check_box) CheckBox distanceOrder;

    public final static int RESULT_OK = 1;
    public final static int RESULT_CANCEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_filter_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent.getStringExtra("ADDRESS").equals("")) {
            View view = findViewById(R.id.activity_filter_bicycle_location);
            view.setVisibility(View.GONE);
        } else {
            // TODO : 캘린더 위 margin 부여
            // 참고 http://stackoverflow.com/questions/11062187/how-to-set-margin-dynamically-in-android
            address.setText(intent.getStringExtra("ADDRESS"));
            Toast.makeText(FilterActivity.this, "address : " + findGeoPoint(intent.getStringExtra("ADDRESS")), Toast.LENGTH_SHORT).show();
        }

        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (isStartDatePicked == false) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Toast.makeText(FilterActivity.this, "State Date : " + simpleDateFormat.format(date), Toast.LENGTH_SHORT).show();
                    select = date;
                    isStartDatePicked = true;
                } else if (isEndDatePicked == false) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Toast.makeText(FilterActivity.this, "End Date : " + simpleDateFormat.format(date), Toast.LENGTH_SHORT).show();
                    select = date;
                    isEndDatePicked = true;
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        calendarPickerViewTemp = (CalendarPickerView) findViewById(R.id.calendar_view_temp);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        start = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        end = calendar.getTime();
        calendarPickerView.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
        calendarPickerViewTemp.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
    }

    @OnClick(R.id.activity_filter_prev_button) void prevButton() {
        calendarPickerViewTemp.init(start, end);

        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            calendarPickerViewTemp.selectDate(select);
        }

        calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH, -1);
        start = calendar.getTime();

        calendar.setTime(end);
        calendar.add(Calendar.MONTH, -1);
        end = calendar.getTime();

        calendarPickerView.init(start, end);

        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            calendarPickerView.selectDate(select);
        }

        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.left_in);
        calendarPickerView.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.right_out);
        calendarPickerViewTemp.startAnimation(anim);
    }

    @OnClick(R.id.activity_filter_next_button) void nextButton() {
        calendarPickerViewTemp.init(start, end);

        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            calendarPickerViewTemp.selectDate(select);
        }

        calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH, 1);
        start = calendar.getTime();

        calendar.setTime(end);
        calendar.add(Calendar.MONTH, 1);
        end = calendar.getTime();

        calendarPickerView.init(start, end);

        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            calendarPickerView.selectDate(select);
        }

        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.right_in);
        calendarPickerView.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.left_out);
        calendarPickerViewTemp.startAnimation(anim);
    }

    @OnClick({R.id.bicycle_type_check_box1,
            R.id.bicycle_type_check_box2,
            R.id.bicycle_type_check_box3,
            R.id.bicycle_type_check_box4,
            R.id.bicycle_type_check_box5,
            R.id.bicycle_type_check_box6,
            R.id.bicycle_type_check_box7})
    void checkType(View view) {
        type1.setChecked(false);
        type2.setChecked(false);
        type3.setChecked(false);
        type4.setChecked(false);
        type5.setChecked(false);
        type6.setChecked(false);
        type7.setChecked(false);
        switch (view.getId()) {
            case R.id.bicycle_type_check_box1: type1.setChecked(true); break;
            case R.id.bicycle_type_check_box2: type2.setChecked(true); break;
            case R.id.bicycle_type_check_box3: type3.setChecked(true); break;
            case R.id.bicycle_type_check_box4: type4.setChecked(true); break;
            case R.id.bicycle_type_check_box5: type5.setChecked(true); break;
            case R.id.bicycle_type_check_box6: type6.setChecked(true); break;
            case R.id.bicycle_type_check_box7: type7.setChecked(true); break;
        }
    }

    @OnClick({R.id.bicycle_order_price_order_check_box, R.id.bicycle_order_distance_order_check_box})
    void checkOrder(View view) {
        switch (view.getId()) {
            case R.id.bicycle_order_price_order_check_box:
                priceOrder.setChecked(true);
                distanceOrder.setChecked(false);
                break;
            case R.id.bicycle_order_distance_order_check_box:
                priceOrder.setChecked(false);
                distanceOrder.setChecked(true);
                break;
        }
    }

    @OnClick(R.id.activity_filter_search_button) void searchButton() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCEL);
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

    private String findGeoPoint(String address) {
        Geocoder geocoder = new Geocoder(this);
        Address addr;
        String location = null;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 1);
            if (listAddress.size() > 0) { // 주소값이 존재 하면
                addr = listAddress.get(0); // Address형태로
                int lat = (int) (addr.getLatitude() * 1E6);
                int lng = (int) (addr.getLongitude() * 1E6);
                location = lat + ", " + lng;

                Log.d("RESULT", "주소로부터 취득한 위도 : " + lat + ", 경도 : " + lng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }
}
