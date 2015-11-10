package com.example.tacademy.bikee.renter.searchresult.filter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.squareup.timessquare.CalendarGridView;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class FilterActivity extends AppCompatActivity {
    public final static int RESULT_OK = 1;
    public final static int RESULT_CANCEL = 2;

    CalendarPickerView calendarPickerView;
    Calendar calendar;
    Date start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Button btn = (Button)findViewById(R.id.activity_filter_search_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
                //intent.putExtra(KEY_FILTER,true);
                setResult(RESULT_OK);
                finish();
            }
        });

        Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
        calendarPickerView = (CalendarPickerView)findViewById(R.id.calendar_picker_view);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        start = calendar.getTime();
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE, -1);
        end = calendar.getTime();
        calendarPickerView.init(start,end) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDate(new Date());

        btn = (Button)findViewById(R.id.activity_filter_prev_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
        btn = (Button)findViewById(R.id.activity_filter_next_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCEL);
        super.onBackPressed();
    }
}
