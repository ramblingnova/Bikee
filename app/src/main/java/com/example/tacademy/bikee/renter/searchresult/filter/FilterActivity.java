package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.google.android.gms.maps.model.LatLng;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.OnDateSelectedListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    @Bind(R.id.bicycle_location_address_text_view)
    TextView address;
    @Bind(R.id.calendar_summary_start_date_text_view)
    TextView startDateTextView;
    @Bind(R.id.calendar_summary_start_time_text_view)
    TextView startTimeTextView;
    @Bind(R.id.calendar_summary_end_date_text_view)
    TextView endDateTextView;
    @Bind(R.id.calendar_summary_end_time_text_view)
    TextView endTimeTextView;
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
    private String startDateTime;
    private String endDateTime;
    private boolean isStartDate;
    private List<Calendar> abledates;
    private List<Timepoint> abletime;
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    Calendar start_cal;
    Calendar end_cal;
    Date start_day;

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
        // TODO : 재설정 적용
        Toast.makeText(FilterActivity.this, "RESET!", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.calendar_start_date_summary,
            R.id.calendar_end_date_summary})
    void onClickCalendar(View view) {
        final Calendar now = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.calendar_end_date_summary: {
                now.setTime(start_day);
            }
        }

        dpd = DatePickerDialog.newInstance(
                FilterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        abledates = new ArrayList<>();
        int now_days = now.get(Calendar.DAY_OF_YEAR);
        for (int i = now_days; i < now_days + 60; i++) {
            Calendar ableday = Calendar.getInstance();
            ableday.set(Calendar.DAY_OF_YEAR, i);
            abledates.add(ableday);
        }

        dpd.setThemeDark(true);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setAccentColor(Color.parseColor("#1993F7"));
        dpd.setSelectableDays(abledates.toArray(new Calendar[abledates.size()]));
        dpd.show(getFragmentManager(), "Datepickerdialog");

        final TimePickerDialog tpd = TimePickerDialog.newInstance(
                FilterActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true //24시간으로 세팅.
        );

        switch (view.getId()) {
            case R.id.calendar_start_date_summary: {
                dpd.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(View view, Date date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault());
                        startDateTime = simpleDateFormat.format(date.getTime());
                        startDateTextView.setText(startDateTime);
                        if (Build.VERSION.SDK_INT < 23) {
                            startDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                        } else {
                            startDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
                        }
                        start_day = date;
                        openTimePicker(now, date, true);
                        isStartDate = true;
                    }
                });
                break;
            }
            case R.id.calendar_end_date_summary: {
                dpd.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(View view, Date date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault());
                        endDateTime = simpleDateFormat.format(date.getTime());
                        endDateTextView.setText(endDateTime);

                        if (Build.VERSION.SDK_INT < 23) {
                            endDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
                        } else {
                            endDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
                        }

                        openTimePicker(now, date, false);

                        isStartDate = false;
                    }
                });
                break;
            }
        }
    }

    public void openTimePicker(Calendar now, Date date, Boolean flag) {
        Calendar selDay = Calendar.getInstance();
        int now_min, now_hour;
        if (!flag) {
            /*start_cal.set(Calendar.MINUTE, 30);*/
            /*now = start_cal;*/
            now.setTime(start_day);
            now.add(Calendar.MINUTE, 30);
           /* tpd = TimePickerDialog.newInstance(
                    FilterActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true //24시간으로 세팅.
            );*/
            if (now.get(Calendar.AM_PM) == 0) {
                now_min = now.get(Calendar.MINUTE);
                now_hour = now.get(Calendar.HOUR_OF_DAY) + 12;
            } else {
                now_min = now.get(Calendar.MINUTE);
                now_hour = now.get(Calendar.HOUR_OF_DAY);
            }


//            now.add(Calendar.MINUTE, 30);
            /*now_min = start_cal.get(Calendar.MINUTE) +30;
            now_hour = start_cal.get(Calendar.HOUR_OF_DAY);
            if(now_min > 50){
                now_hour += 1;
                now_min -= 60;
            }*/
        } else {
          /*  tpd = TimePickerDialog.newInstance(
                    FilterActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true //24시간으로 세팅.
            );*/
            now_min = now.get(Calendar.MINUTE);
            now_hour = now.get(Calendar.HOUR_OF_DAY);
        }


        now_min = (int) Math.ceil((double) now_min / 10) * 10;
        selDay.setTime(date);
        if (!(now.get(Calendar.DAY_OF_YEAR) == selDay.get(Calendar.DAY_OF_YEAR))) {
            now_hour = 7;
            now_min = 0;
        }

        if (now_hour > 21 || now_hour < 7) {
            now_hour = 7;
            now_min = 0;
        }
        tpd = TimePickerDialog.newInstance(
                FilterActivity.this,
                now_hour,
                now_min,
                true //24시간으로 세팅.
        );
        tpd.setThemeDark(true);
        tpd.setAccentColor(Color.parseColor("#1993F7"));
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.setTitle("시작일");
        tpd.enableSeconds(false);

        abletime = new ArrayList<>();

        if (now.get(Calendar.DAY_OF_YEAR) == selDay.get(Calendar.DAY_OF_YEAR)) {
            if (now_min >= 50) {
                for (int h = now_hour + 1; h <= 21; h++) {
                    for (int m = 0; m <= 50; m += 10) {
                        abletime.add(new Timepoint(h, m));
                    }
                }
            } else {
                for (int h = now_hour; h <= 21; h++) {
                    if (h == now_hour) {
                        for (int m = now_min; m <= 50; m += 10) {
                            abletime.add(new Timepoint(h, m));
                        }
                    } else {
                        for (int m = 0; m <= 50; m += 10) {
                            abletime.add(new Timepoint(h, m));
                        }
                    }
                }
            }
        } else {
            for (int h = 7; h <= 21; h++) {
                for (int m = 0; m <= 50; m += 10) {
                    abletime.add(new Timepoint(h, m));
                }
            }
        }
        tpd.setSelectableTimes(abletime.toArray(new Timepoint[abletime.size()]));

        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (isStartDate) {
            startDateTime += " " + hourOfDay + ":" + minute;
            if (BuildConfig.DEBUG)
                Log.d(TAG, "startDateTime yyyy/MM/dd hh:mm : " + startDateTime);

            if (Build.VERSION.SDK_INT < 23) {
                startTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
            } else {
                startTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
            }

            startTimeTextView.setText(hourOfDay + ":" + minute);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            try {
                start_cal = Calendar.getInstance();
                start_cal.setTime(format.parse(startDateTime));
                start_day = format.parse(startDateTime);
            } catch (Exception e) {
            }
        } else {
            endDateTime += " " + hourOfDay + ":" + minute;
            if (BuildConfig.DEBUG)
                Log.d(TAG, "endDateTime yyyy/MM/dd hh:mm : " + endDateTime);
            if (Build.VERSION.SDK_INT < 23) {
                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
            } else {
                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
            }
            endTimeTextView.setText(hourOfDay + ":" + minute);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            try {

                end_cal = Calendar.getInstance();
                end_cal.setTime(format.parse(endDateTime));
                if (start_cal.after(end_cal)) {
                    // 지나친것
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                    Toast.makeText(FilterActivity.this, "날짜가 선택이 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 정상

                }
            } catch (Exception e) {
            }
        }
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
        intent.putExtra("START_DATE", startDateTime);
        intent.putExtra("END_DATE", endDateTime);
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
