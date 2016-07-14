package com.bigtion.bikee.renter.searchresult.filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    // TODO : 날짜 시작 값 시작 -> 현재 시간, 종료 -> 현재시간 + 1시간
    @Bind(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @Bind(R.id.toolbar_left_back_icon_image_view)
    ImageView toolbarLeftBackIconImageView;
    @Bind(R.id.toolbar_center_text_view)
    TextView toolbarCenterTextView;
    @Bind(R.id.toolbar_right_text_view)
    TextView toolbarRightTextView;
    @Bind(R.id.activity_filter_bicycle_location)
    View bicycleLocationView;
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
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;
    private List<Calendar> ableDates;
    private List<Timepoint> ableTimes;
    private Calendar currentDateTime;
    private Calendar selectedStartDateTime;
    private Calendar selectedEndDateTime;
    private String currentTimeTag;

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
        getSupportActionBar().setCustomView(R.layout.toolbar);

        ButterKnife.bind(this);

        /* 툴바 배경 */
        if (Build.VERSION.SDK_INT < 23)
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite));
        else
            toolbarLayout.setBackgroundColor(getResources().getColor(R.color.bikeeWhite, getTheme()));

        /* 툴바 왼쪽 */
        toolbarLeftBackIconImageView.setImageResource(R.drawable.icon_before);
        toolbarLeftBackIconImageView.setVisibility(View.VISIBLE);

        /* 툴바 가운데 */
        toolbarCenterTextView.setText("필터링");
        if (Build.VERSION.SDK_INT < 23)
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeDarkGray));
        else
            toolbarCenterTextView.setTextColor(getResources().getColor(R.color.bikeeDarkGray, getTheme()));
        toolbarCenterTextView.setVisibility(View.VISIBLE);

        /* 툴바 오른쪽 */
        toolbarRightTextView.setVisibility(View.VISIBLE);

        intent = getIntent();
        if (intent.getBooleanExtra("HAS_LAT_LNG", false)) {
            String addressString = intent.getStringExtra("ADDRESS");
            address.setText(addressString);
            latitude = "" + intent.getDoubleExtra("LATITUDE", -1.0);
            longitude = "" + intent.getDoubleExtra("LONGITUDE", -1.0);
            if (BuildConfig.DEBUG)
                Log.d(TAG, "onCreate latitude : " + latitude + ", longitude : " + longitude);
        } else {
            bicycleLocationView.setVisibility(View.GONE);
        }

        currentDateTime = Calendar.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ableDates == null) {
            ableDates = new ArrayList<>();

            int nowDay = currentDateTime.get(Calendar.DAY_OF_YEAR);
            int nowHour = currentDateTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = (int) (Math.ceil(currentDateTime.get(Calendar.MINUTE) / 10.0) * 10);

            for (int i = nowDay; i < nowDay + 60; i++) {
                Calendar ableDate = Calendar.getInstance();
                ableDate.set(Calendar.DAY_OF_YEAR, i);
                ableDates.add(ableDate);
            }

            if ((nowHour < 7) || (nowMinute > 21)) {
                currentDateTime.set(Calendar.HOUR_OF_DAY, 7);
                currentDateTime.set(Calendar.MINUTE, 0);
            }
        }

        if (startDatePickerDialog == null) {
            startDatePickerDialog = DatePickerDialog.newInstance(
                    FilterActivity.this,
                    currentDateTime.get(Calendar.YEAR),
                    currentDateTime.get(Calendar.MONTH),
                    currentDateTime.get(Calendar.DAY_OF_MONTH)
            );

            startDatePickerDialog.setOnDateSetListener(this);
            startDatePickerDialog.dismissOnPause(true);
            startDatePickerDialog.setThemeDark(true);
            startDatePickerDialog.vibrate(true);
            startDatePickerDialog.showYearPickerFirst(false);
            startDatePickerDialog.setAccentColor(Color.parseColor("#1993F7"));
            startDatePickerDialog.setSelectableDays(ableDates.toArray(new Calendar[ableDates.size()]));

            if (selectedStartDateTime == null) {
                selectedStartDateTime = Calendar.getInstance();
                selectedStartDateTime.set(
                        currentDateTime.get(Calendar.YEAR),
                        currentDateTime.get(Calendar.MONTH),
                        currentDateTime.get(Calendar.DAY_OF_MONTH)
                );
            }
        }

        if (endDatePickerDialog == null) {
            endDatePickerDialog = DatePickerDialog.newInstance(
                    FilterActivity.this,
                    currentDateTime.get(Calendar.YEAR),
                    currentDateTime.get(Calendar.MONTH),
                    currentDateTime.get(Calendar.DAY_OF_MONTH)
            );

            endDatePickerDialog.setOnDateSetListener(this);
            endDatePickerDialog.dismissOnPause(true);
            endDatePickerDialog.setThemeDark(true);
            endDatePickerDialog.vibrate(true);
            endDatePickerDialog.dismissOnPause(true);
            endDatePickerDialog.showYearPickerFirst(false);
            endDatePickerDialog.setAccentColor(Color.parseColor("#1993F7"));
            endDatePickerDialog.setSelectableDays(ableDates.toArray(new Calendar[ableDates.size()]));

            if (selectedEndDateTime == null) {
                selectedEndDateTime = Calendar.getInstance();
                selectedEndDateTime.set(
                        currentDateTime.get(Calendar.YEAR),
                        currentDateTime.get(Calendar.MONTH),
                        currentDateTime.get(Calendar.DAY_OF_MONTH)
                );
            }
        }

        if (startTimePickerDialog == null) {
            int nowHour = currentDateTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = currentDateTime.get(Calendar.MINUTE);
            ableTimes = new ArrayList<>();
            startTimePickerDialog = TimePickerDialog.newInstance(
                    FilterActivity.this,
                    nowHour,
                    nowMinute,
                    true
            );

            startTimePickerDialog.setOnTimeSetListener(this);
            startTimePickerDialog.dismissOnPause(true);
            startTimePickerDialog.enableSeconds(false);
            startTimePickerDialog.setThemeDark(true);
            startTimePickerDialog.setAccentColor(Color.parseColor("#1993F7"));
            startTimePickerDialog.vibrate(true);
            startTimePickerDialog.setTitle("시작일");

            if (currentDateTime.get(Calendar.DAY_OF_YEAR) == selectedStartDateTime.get(Calendar.DAY_OF_YEAR)) {
                if (nowMinute >= 50) {
                    for (int h = nowHour + 1; h <= 21; h++)
                        for (int m = 0; m <= 50; m += 10)
                            ableTimes.add(new Timepoint(h, m));
                } else {
                    for (int h = nowHour; h <= 21; h++)
                        if (h == nowHour)
                            for (int m = nowMinute; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                        else
                            for (int m = 0; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                }
            } else {
                for (int h = 7; h <= 21; h++)
                    for (int m = 0; m <= 50; m += 10)
                        ableTimes.add(new Timepoint(h, m));
            }

            startTimePickerDialog.setSelectableTimes(ableTimes.toArray(new Timepoint[ableTimes.size()]));
        }

        if (endTimePickerDialog == null) {
            int nowHour = currentDateTime.get(Calendar.HOUR_OF_DAY);
            int nowMinute = currentDateTime.get(Calendar.MINUTE);
            ableTimes = new ArrayList<>();
            endTimePickerDialog = TimePickerDialog.newInstance(
                    FilterActivity.this,
                    nowHour,
                    nowMinute,
                    true
            );

            endTimePickerDialog.dismissOnPause(true);
            endTimePickerDialog.setOnTimeSetListener(this);
            endTimePickerDialog.enableSeconds(false);
            endTimePickerDialog.setThemeDark(true);
            endTimePickerDialog.setAccentColor(Color.parseColor("#1993F7"));
            endTimePickerDialog.vibrate(true);
            endTimePickerDialog.setTitle("종료일");

            if (currentDateTime.get(Calendar.DAY_OF_YEAR) == selectedEndDateTime.get(Calendar.DAY_OF_YEAR)) {
                if (nowMinute >= 50) {
                    for (int h = nowHour + 1; h <= 21; h++)
                        for (int m = 0; m <= 50; m += 10)
                            ableTimes.add(new Timepoint(h, m));
                } else {
                    for (int h = nowHour; h <= 21; h++)
                        if (h == nowHour)
                            for (int m = nowMinute; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                        else
                            for (int m = 0; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                }
            } else {
                for (int h = 7; h <= 21; h++)
                    for (int m = 0; m <= 50; m += 10)
                        ableTimes.add(new Timepoint(h, m));
            }

            endTimePickerDialog.setSelectableTimes(ableTimes.toArray(new Timepoint[ableTimes.size()]));
        }
    }

    @OnClick({R.id.toolbar_left_layout,
            R.id.toolbar_right_layout,
            R.id.bicycle_type_check_box1,
            R.id.bicycle_type_check_box2,
            R.id.bicycle_type_check_box3,
            R.id.bicycle_type_check_box4,
            R.id.bicycle_type_check_box5,
            R.id.bicycle_type_check_box6,
            R.id.bicycle_type_check_box7,
            R.id.bicycle_recommendation_height_check_box1,
            R.id.bicycle_recommendation_height_check_box2,
            R.id.bicycle_recommendation_height_check_box3,
            R.id.bicycle_recommendation_height_check_box4,
            R.id.bicycle_recommendation_height_check_box5,
            R.id.bicycle_recommendation_height_check_box6,
            R.id.bicycle_order_price_order_check_box,
            R.id.bicycle_order_distance_order_check_box,
            R.id.activity_filter_search_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                onBackPressed();
                break;
            case R.id.toolbar_right_layout:
                // TODO : 재설정 적용
                Toast.makeText(FilterActivity.this, "RESET!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bicycle_type_check_box1:
            case R.id.bicycle_type_check_box2:
            case R.id.bicycle_type_check_box3:
            case R.id.bicycle_type_check_box4:
            case R.id.bicycle_type_check_box5:
            case R.id.bicycle_type_check_box6:
            case R.id.bicycle_type_check_box7:
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
                break;
            case R.id.bicycle_recommendation_height_check_box1:
            case R.id.bicycle_recommendation_height_check_box2:
            case R.id.bicycle_recommendation_height_check_box3:
            case R.id.bicycle_recommendation_height_check_box4:
            case R.id.bicycle_recommendation_height_check_box5:
            case R.id.bicycle_recommendation_height_check_box6:
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
                break;
            case R.id.bicycle_order_price_order_check_box:
                priceOrderCheckBox.setChecked(true);
                distanceOrderCheckBox.setChecked(false);
                break;
            case R.id.bicycle_order_distance_order_check_box:
                priceOrderCheckBox.setChecked(false);
                distanceOrderCheckBox.setChecked(true);
                break;
            case R.id.activity_filter_search_button:
                intent = new Intent();
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);
                intent.putExtra("TYPE", type);
                intent.putExtra("HEIGHT", height);
                intent.putExtra("SMART_LOCK", smartLock);
                intent.putExtra("ORDER", order);
//                intent.putExtra("START_DATE", startDateTime);
//                intent.putExtra("END_DATE", endDateTime);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @OnCheckedChanged({R.id.bicycle_smart_lock_check_box,
            R.id.bicycle_order_distance_order_check_box,
            R.id.bicycle_order_price_order_check_box})
    void onCheckedChange(CompoundButton buttonView, boolean isChecked) {
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

    @OnClick({R.id.calendar_start_date_summary,
            R.id.calendar_end_date_summary})
    void onClickCalendar(View view) {
        String tag = TAG;

        switch (view.getId()) {
            case R.id.calendar_start_date_summary:
                tag += "_START_DATE";
                startDatePickerDialog.show(getFragmentManager(), tag);
                break;
            case R.id.calendar_end_date_summary:
                tag += "_END_DATE";
                endDatePickerDialog.show(getFragmentManager(), tag);
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tag = TAG;
        int nowHour = currentDateTime.get(Calendar.HOUR_OF_DAY);
        int nowMinute = currentDateTime.get(Calendar.MINUTE);

        switch (view.getTag()) {
            case TAG + "_START_DATE":
                tag += "_START_TIME";
                currentTimeTag = tag;
                ableTimes = new ArrayList<>();
                selectedStartDateTime.set(year, monthOfYear, dayOfMonth);

                if (currentDateTime.get(Calendar.DAY_OF_YEAR) == selectedStartDateTime.get(Calendar.DAY_OF_YEAR)) {
                    if (nowMinute >= 50) {
                        for (int h = nowHour + 1; h <= 21; h++)
                            for (int m = 0; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                    } else {
                        for (int h = nowHour; h <= 21; h++)
                            if (h == nowHour)
                                for (int m = nowMinute; m <= 50; m += 10)
                                    ableTimes.add(new Timepoint(h, m));
                            else
                                for (int m = 0; m <= 50; m += 10)
                                    ableTimes.add(new Timepoint(h, m));
                    }
                } else {
                    for (int h = 7; h <= 21; h++)
                        for (int m = 0; m <= 50; m += 10)
                            ableTimes.add(new Timepoint(h, m));
                }

                startTimePickerDialog.setSelectableTimes(ableTimes.toArray(new Timepoint[ableTimes.size()]));
                startTimePickerDialog.show(getFragmentManager(), tag);
                break;
            case TAG + "_END_DATE":
                tag += "_END_TIME";
                currentTimeTag = tag;
                ableTimes = new ArrayList<>();
                selectedEndDateTime.set(year, monthOfYear, dayOfMonth);

                if (currentDateTime.get(Calendar.DAY_OF_YEAR) == selectedEndDateTime.get(Calendar.DAY_OF_YEAR)) {
                    if (nowMinute >= 50) {
                        for (int h = nowHour + 1; h <= 21; h++)
                            for (int m = 0; m <= 50; m += 10)
                                ableTimes.add(new Timepoint(h, m));
                    } else {
                        for (int h = nowHour; h <= 21; h++)
                            if (h == nowHour)
                                for (int m = nowMinute; m <= 50; m += 10)
                                    ableTimes.add(new Timepoint(h, m));
                            else
                                for (int m = 0; m <= 50; m += 10)
                                    ableTimes.add(new Timepoint(h, m));
                    }
                } else {
                    for (int h = 7; h <= 21; h++)
                        for (int m = 0; m <= 50; m += 10)
                            ableTimes.add(new Timepoint(h, m));
                }

                endTimePickerDialog.setSelectableTimes(ableTimes.toArray(new Timepoint[ableTimes.size()]));
                endTimePickerDialog.show(getFragmentManager(), tag);
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        switch (currentTimeTag) {
            case TAG + "_START_TIME":
                selectedStartDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedStartDateTime.set(Calendar.MINUTE, minute);

                startTimePickerDialog.setStartTime(
                        hourOfDay,
                        minute
                );
                break;
            case TAG + "_END_TIME":
                selectedEndDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedEndDateTime.set(Calendar.MINUTE, minute);

                endTimePickerDialog.setStartTime(
                        hourOfDay,
                        minute
                );
                break;
        }

//        if (isStartDate) {
//            startDateTime += " " + hourOfDay + ":" + minute;
//            if (BuildConfig.DEBUG)
//                Log.d(TAG, "startDateTime yyyy/MM/dd hh:mm : " + startDateTime);
//
//            if (Build.VERSION.SDK_INT < 23) {
//                startTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
//            } else {
//                startTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
//            }
//
//            startTimeTextView.setText(hourOfDay + ":" + minute);
//            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//            try {
//                start_cal = Calendar.getInstance();
//                start_cal.setTime(format.parse(startDateTime));
//                start_day = format.parse(startDateTime);
//            } catch (Exception e) {
//            }
//        } else {
//            endDateTime += " " + hourOfDay + ":" + minute;
//            if (BuildConfig.DEBUG)
//                Log.d(TAG, "endDateTime yyyy/MM/dd hh:mm : " + endDateTime);
//            if (Build.VERSION.SDK_INT < 23) {
//                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
//            } else {
//                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getTheme()));
//            }
//            endTimeTextView.setText(hourOfDay + ":" + minute);
//            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm", java.util.Locale.getDefault());
//            try {
//                end_cal = Calendar.getInstance();
//                end_cal.setTime(format.parse(endDateTime));
//                if (start_cal.after(end_cal)) {
//                    // 지나친것
//                    dpd.show(getFragmentManager(), "Datepickerdialog");
//                    Toast.makeText(FilterActivity.this, "날짜가 선택이 잘못됐습니다.", Toast.LENGTH_SHORT).show();
//                } else {
//                    // 정상
//                }
//            } catch (Exception e) {
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
