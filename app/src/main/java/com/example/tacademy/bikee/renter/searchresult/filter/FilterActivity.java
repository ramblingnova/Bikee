package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.squareup.timessquare.CalendarPickerView;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements CalendarPickerView.OnDateSelectedListener {
    // TODO : handle filter and send filter result, handle renew button
    private Intent intent;
    private CalendarPickerView currentCalendarPickerView, oldCalendarPickerView;
    private Calendar calendar;
    private Date start, end, select;
    private Animation anim;
    @Bind(R.id.bicycle_location_address_text_view)
    TextView address;
    private boolean isStartDatePicked = false;
    private boolean isEndDatePicked = false;
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
    CheckBox priceOrder;
    @Bind(R.id.bicycle_order_distance_order_check_box)
    CheckBox distanceOrder;
    public final static int RESULT_OK = 1;
    public final static int RESULT_CANCEL = 2;

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
        }

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        start = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        end = calendar.getTime();

        currentCalendarPickerView = (CalendarPickerView) findViewById(R.id.current_calendar_view);
        currentCalendarPickerView.setOnDateSelectedListener(this);
        oldCalendarPickerView = (CalendarPickerView) findViewById(R.id.old_calendar_view);
        currentCalendarPickerView.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        oldCalendarPickerView.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        oldCalendarPickerView.setVisibility(View.GONE);
    }

    @OnClick(R.id.filter_backable_tool_bar_back_button_layout)
    void back(View view) {
        finish();
    }

    @OnClick(R.id.filter_backable_tool_bar_reset_text_view)
    void reset(View view) {
        Toast.makeText(FilterActivity.this, "RESET!", Toast.LENGTH_SHORT).show();
    }

    boolean openStartCalendar = false;
    boolean openEndCalendar = false;
    @OnClick({R.id.calendar_start_date_summary,
            R.id.calendar_end_date_summary})
    void openCloseCalendar(View view) {
        switch (view.getId()) {
            case R.id.calendar_start_date_summary:
                if (openStartCalendar) {
                    openStartCalendar = false;
                    closeCalendar();
                } else {
                    openStartCalendar = true;
                    if (openEndCalendar) {
                        openEndCalendar = false;
                        closeCalendar();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                openCalendar();
                            }
                        }, 500);
                    } else {
                        openCalendar();
                    }
                }
                break;
            case R.id.calendar_end_date_summary:
                if (openEndCalendar) {
                    openEndCalendar = false;
                    closeCalendar();
                } else {
                    openEndCalendar = true;
                    if (openStartCalendar) {
                        openStartCalendar = false;
                        closeCalendar();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                openCalendar();
                            }
                        }, 500);
                    } else {
                        openCalendar();
                    }
                }
                break;
        }
    }

    public void openCalendar() {
        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.calendar_top_in);
        anim.setFillAfter(true);
        calendarContent.startAnimation(anim);
        calendarContent.setVisibility(View.VISIBLE);
    }

    public void closeCalendar() {
        anim = AnimationUtils.loadAnimation(FilterActivity.this, R.anim.calendar_top_out);
        anim.setFillAfter(true);
        calendarContent.startAnimation(anim);
        calendarContent.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                calendarContent.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @OnClick({R.id.activity_filter_calendar_prev_button,
            R.id.activity_filter_calendar_next_button})
    void changeCalendarMonth(View view) {
        int currentViewId = view.getId();

        oldCalendarPickerView.setVisibility(View.VISIBLE);
        oldCalendarPickerView.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            oldCalendarPickerView.selectDate(select);
        }

        calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH,
                (currentViewId == R.id.activity_filter_calendar_prev_button) ? -1 : 1);
        start = calendar.getTime();
        calendar.setTime(end);
        calendar.add(Calendar.MONTH,
                (currentViewId == R.id.activity_filter_calendar_prev_button) ? -1 : 1);
        end = calendar.getTime();
        currentCalendarPickerView.init(start, end)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        if (select != null && select.getTime() >= start.getTime() && select.getTime() <= end.getTime()) {
            currentCalendarPickerView.selectDate(select);
        }

        anim = AnimationUtils.loadAnimation(FilterActivity.this,
                (currentViewId == R.id.activity_filter_calendar_prev_button) ? R.anim.calendar_left_in : R.anim.calendar_right_in);
        anim.setFillAfter(true);
        currentCalendarPickerView.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(FilterActivity.this,
                (currentViewId == R.id.activity_filter_calendar_prev_button) ? R.anim.calendar_right_out : R.anim.calendar_left_out);
        anim.setFillAfter(false);
        oldCalendarPickerView.startAnimation(anim);
        oldCalendarPickerView.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                oldCalendarPickerView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

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
                break;
            case R.id.bicycle_type_check_box2:
                type2.setChecked(true);
                break;
            case R.id.bicycle_type_check_box3:
                type3.setChecked(true);
                break;
            case R.id.bicycle_type_check_box4:
                type4.setChecked(true);
                break;
            case R.id.bicycle_type_check_box5:
                type5.setChecked(true);
                break;
            case R.id.bicycle_type_check_box6:
                type6.setChecked(true);
                break;
            case R.id.bicycle_type_check_box7:
                type7.setChecked(true);
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
                break;
            case R.id.bicycle_recommendation_height_check_box2:
                height2.setChecked(true);
                break;
            case R.id.bicycle_recommendation_height_check_box3:
                height3.setChecked(true);
                break;
            case R.id.bicycle_recommendation_height_check_box4:
                height4.setChecked(true);
                break;
            case R.id.bicycle_recommendation_height_check_box5:
                height5.setChecked(true);
                break;
            case R.id.bicycle_recommendation_height_check_box6:
                height6.setChecked(true);
                break;
        }
    }

    @OnClick({R.id.bicycle_order_price_order_check_box,
            R.id.bicycle_order_distance_order_check_box})
    void selectOrder(View view) {
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

    @OnClick(R.id.activity_filter_search_button)
    void searchDetail() {
        // TODO : need putExtra for filtered searching
        setResult(RESULT_OK, intent);
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
}
