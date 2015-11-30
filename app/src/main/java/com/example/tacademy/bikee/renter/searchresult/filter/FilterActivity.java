package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.squareup.timessquare.CalendarPickerView;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    private CalendarPickerView calendarPickerView, calendarPickerViewTemp;
    private Calendar calendar;
    private Date start, end, select;
    private Animation anim;

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

        Button btn = (Button) findViewById(R.id.activity_filter_search_button);
        btn.setOnClickListener(FilterActivity.this);

        Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Toast.makeText(FilterActivity.this, simpleDateFormat.format(date), Toast.LENGTH_SHORT).show();
                select = date;
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

        btn = (Button) findViewById(R.id.activity_filter_prev_button);
        btn.setOnClickListener(FilterActivity.this);
        btn = (Button) findViewById(R.id.activity_filter_next_button);
        btn.setOnClickListener(FilterActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_filter_search_button:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.activity_filter_prev_button:
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
                break;
            case R.id.activity_filter_next_button:
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
                break;
        }
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
