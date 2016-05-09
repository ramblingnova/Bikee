package com.example.tacademy.bikee.common.content.popup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.SelectReservationReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-05-09.
 */
public class CalendarDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    @Bind(R.id.calendar_summary_start_date_text_view)
    TextView startDateTextView;
    @Bind(R.id.calendar_summary_start_time_text_view)
    TextView startTimeTextView;
    @Bind(R.id.calendar_summary_end_date_text_view)
    TextView endDateTextView;
    @Bind(R.id.calendar_summary_end_time_text_view)
    TextView endTimeTextView;
    @Bind(R.id.fragment_calendar_dialog_reservation_button)
    Button ReservationButton;

    private String bicycleId;
    private String startDateTime;
    private String endDateTime;
    private boolean isStartDate;
    private List<Calendar> disabledates;
    private List<Calendar> abledates;
    private List<Timepoint> abletime;
    private List<SelectReservationReceiveObject.Result> results;
    int start_rent_hour = 7;
    int start_rent_minute = 0;
    int end_rent_hour = 21;
    int end__rent_minute = 50;
    private DatePickerDialog dpd;

    private static final String TAG = "CALENDAR_DIALOG_F";

    public static CalendarDialogFragment newInstance(String bicycleId) {
        CalendarDialogFragment fragment = new CalendarDialogFragment();

        Bundle args = new Bundle();
        args.putString("BICYCLE_ID", bicycleId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);

        bicycleId = getArguments().getString("BICYCLE_ID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_dialog, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        d.getWindow().setLayout(800, 400);
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.x = 0;
        params.y = 0;
        d.getWindow().setAttributes(params);
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @OnClick({R.id.calendar_start_date_summary,
            R.id.calendar_end_date_summary})
    void onClick(View view) {
        final Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CalendarDialogFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        abledates = new ArrayList<>();
        disabledates = new ArrayList<>();

        final TimePickerDialog tpd = TimePickerDialog.newInstance(
                CalendarDialogFragment.this,
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
                            startDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getActivity().getTheme()));
                        }

                        openTimePicker(tpd, now, date);
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
                            endDateTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getActivity().getTheme()));
                        }

                        openTimePicker(tpd, now, date);
                        isStartDate = false;
                    }
                });
                break;
            }
        }

        NetworkManager.getInstance().selectReservation(
                bicycleId,
                null,
                new Callback<SelectReservationReceiveObject>() {
                    @Override
                    public void onResponse(Call<SelectReservationReceiveObject> call, Response<SelectReservationReceiveObject> response) {
                        Log.d(TAG, response.body().toString());
                        dpd.setThemeDark(true);
                        dpd.vibrate(true);
                        dpd.dismissOnPause(true);
                        dpd.showYearPickerFirst(false);
                        dpd.setAccentColor(Color.parseColor("#1993F7"));

                        SelectReservationReceiveObject bikes = response.body();
                        /*List<Result> results = bikes.getResult();*/
                        results = bikes.getResult();

                        Calendar today = Calendar.getInstance();
                        int now_day = today.get(Calendar.DAY_OF_YEAR);
                        if (today.get(Calendar.HOUR_OF_DAY) >= 22) {
                            now_day += 1;
                        }

                        for (int i = now_day; i < now_day + 60; i++) {
                            boolean flag = true;
                            Calendar albeday = Calendar.getInstance();
                            for (SelectReservationReceiveObject.Result result : results) {
                                SelectReservationReceiveObject.Result.Reserve reserve = result.getReserve();
                                try {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm", java.util.Locale.getDefault());
                                    Date rentStart = reserve.getRentStart();
                                    Date rentEnd = reserve.getRentEnd();

                                    Calendar start = Calendar.getInstance();
                                    Calendar end = Calendar.getInstance();
                                    Calendar disableday = Calendar.getInstance();
                                    start.setTime(rentStart);
                                    end.setTime(rentEnd);
                                    int start_day = start.get(Calendar.DAY_OF_YEAR);
                                    int end_day = end.get(Calendar.DAY_OF_YEAR);
                                    int start_hour = start.get(Calendar.HOUR_OF_DAY);
                                    int end_hour = end.get(Calendar.HOUR_OF_DAY);
                                    int start_min = start.get(Calendar.MINUTE);
                                    int end_min = end.get(Calendar.MINUTE);
                                    //오전 7시00분 ~ 오후 9시 50까지
                                    if ((start_hour <= start_rent_hour && start_min == start_rent_minute) || start_hour < start_rent_hour || today.getTimeInMillis() > start.getTimeInMillis()) {
                                        start_day += -1;
                                    }
                                    if ((end_hour >= end_rent_hour && end_min == end__rent_minute) || end_hour > end_rent_hour) {
                                        end_day += 1;
                                    }
                                    if (start_day < i && end_day > i) {
                                        flag = false;
                                        disableday.set(Calendar.DAY_OF_YEAR, i);
                                        disabledates.add(disableday);
                                    }
//                                    disabletime.add(new Timepoint(i+3,0));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (flag) {
                                albeday.set(Calendar.DAY_OF_YEAR, i);
                                abledates.add(albeday);//disable ?????? ??? ????
                            }
                        }
                        dpd.setSelectableDays(abledates.toArray(new Calendar[abledates.size()]));
                        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                    }

                    @Override
                    public void onFailure(Call<SelectReservationReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "예약 기록을 읽어올 수 없습니다. 관리자에 문의바랍니다.", t);
                    }
                });
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
                startTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getActivity().getTheme()));
            }
            startTimeTextView.setText(hourOfDay + ":" + minute);
        } else {
            endDateTime += " " + hourOfDay + ":" + minute;
            if (BuildConfig.DEBUG)
                Log.d(TAG, "endDateTime yyyy/MM/dd hh:mm : " + endDateTime);
            if (Build.VERSION.SDK_INT < 23) {
                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue));
            } else {
                endTimeTextView.setTextColor(getResources().getColor(R.color.bikeeBlue, getActivity().getTheme()));
            }
            endTimeTextView.setText(hourOfDay + ":" + minute);
        }
    }

    public void openTimePicker(TimePickerDialog tpd, Calendar now, Date date) {
        abletime = new ArrayList<>();
        now = Calendar.getInstance();
        int now_h = now.get(Calendar.HOUR_OF_DAY);
        int now_m = now.get(Calendar.MINUTE);
        if (now_h > 21 || now_h < 7) {
            now_h = 7;
            now_m = 0;
        }

        tpd = TimePickerDialog.newInstance(
                CalendarDialogFragment.this,
                now_h,
                now_m,
                true //24시간으로 세팅.
        );
        tpd.setThemeDark(true);
        tpd.setAccentColor(Color.parseColor("#1993F7"));
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.setTitle("시작일");
        tpd.enableSeconds(false);
        Calendar sel = Calendar.getInstance();
        sel.setTime(date);

        try {
            /*Context context = getActivity();
            SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            p = context.getSharedPreferences("pref", context.MODE_PRIVATE);*/
            /*String sel = p.getString("selDay", "");*/
            int selDay = sel.get(Calendar.DAY_OF_YEAR);

            Log.d("SELDAY ", "" + selDay);
            int now_day = now.get(Calendar.DAY_OF_YEAR);
            int now_hour = now.get(Calendar.HOUR_OF_DAY);
            int now_min = now.get(Calendar.MINUTE);
            now_min = (int) Math.ceil((double) now_min / 10) * 10;
            boolean time_flag = true;

            for (SelectReservationReceiveObject.Result result : results) {
                SelectReservationReceiveObject.Result.Reserve reserve = result.getReserve();
                Date rentStart = reserve.getRentStart();
                Date rentEnd = reserve.getRentEnd();
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                Calendar today = Calendar.getInstance();
                start.setTime(rentStart);
                end.setTime(rentEnd);
                int start_day = start.get(Calendar.DAY_OF_YEAR);
                int end_day = end.get(Calendar.DAY_OF_YEAR);
                int today_day = today.get(Calendar.DAY_OF_YEAR);
                int start_hour = start.get(Calendar.HOUR_OF_DAY);
                int end_hour = end.get(Calendar.HOUR_OF_DAY);
                int today_hour = today.get(Calendar.HOUR_OF_DAY);
                int start_min = start.get(Calendar.MINUTE);
                int end_min = end.get(Calendar.MINUTE);
                int today_min = today.get(Calendar.MINUTE);
                start_min = (int) Math.ceil((double) start_min / 10) * 10;
                end_min = (int) Math.ceil((double) end_min / 10) * 10;
                Log.d("START ", "" + start_day);
                Log.d("END ", "" + end_day);
                    /*
                    * 시작, 종료, 현재 같은경우.
                    * */
                if (selDay == now_day && start_day == now_day && end_day == now_day && start_day == end_day) {
                    time_flag = false;
                    if (today.getTimeInMillis() > start.getTimeInMillis()) {
                        for (int h = end_hour; h <= end_rent_hour; h++) {
                            if (h == end_hour) {
                                for (int m = end_min; m <= 50; m += 10) {
                                    abletime.add(new Timepoint(h, m));
                                }
                            } else {
                                for (int m = 0; m <= 50; m += 10) {
                                    abletime.add(new Timepoint(h, m));
                                }
                            }
                        }
                    } else {
                        if (now_hour == start_hour) {
                            for (int m = now_min; m <= start_min; m += 10) {
                                abletime.add(new Timepoint(start_hour, m));
                            }
                        } else {
                            for (int h = now_hour; h <= start_hour; h++) {
                                if (h == now_hour) {
                                    for (int m = now_min; m <= 50; m += 10) {
                                        abletime.add(new Timepoint(start_hour, m));
                                    }
                                } else if (h == start_hour) {
                                    for (int m = 0; m <= start_min; m += 10) {
                                        abletime.add(new Timepoint(start_hour, m));
                                    }
                                } else {
                                    for (int m = 0; m <= 50; m += 10) {
                                        abletime.add(new Timepoint(start_hour, m));
                                    }
                                }

                            }
                        }

                        for (int h = end_hour; h <= end_rent_hour; h++) {
                            if (h == end_hour) {
                                for (int m = end_min; m <= 50; m += 10) {
                                    abletime.add(new Timepoint(h, m));
                                }
                            } else {
                                for (int m = 0; m <= 50; m += 10) {
                                    abletime.add(new Timepoint(h, m));
                                }
                            }
                        }
                    }
                }

                    /*
                    * 현재 , 시작, 종료 다른경우.
                    * */

                if (selDay == start_day) {
                    time_flag = false;
                    for (int h = start_rent_hour; h <= start_hour; h++) {
                        if (h == start_hour) {
                            for (int m = 0; m <= start_min; m += 10) {
                                abletime.add(new Timepoint(h, m));
                            }
                        } else {
                            for (int m = 0; m <= 50; m += 10) {
                                abletime.add(new Timepoint(h, m));
                            }
                        }

                    }
                }

                if (selDay == end_day) {
                    time_flag = false;
                    for (int h = end_hour; h <= end_rent_hour; h++) {
                        if (h == end_hour) {
                            for (int m = end_min; m <= 50; m += 10) {
                                abletime.add(new Timepoint(h, m));
                            }
                        } else {
                            for (int m = 0; m <= 50; m += 10) {
                                abletime.add(new Timepoint(h, m));
                            }
                        }

                    }
                }
            }
            if (time_flag) {
                if (selDay == now_day && (now_hour >= 7)) {
                    if (now_min >= 50) {
                        for (int h = now_hour + 1; h <= end_rent_hour; h++) {
                            for (int m = 0; m <= 50; m += 10) {
                                abletime.add(new Timepoint(h, m));
                            }
                        }
                    } else {
                        for (int h = now_hour; h <= end_rent_hour; h++) {
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
                    for (int h = start_rent_hour; h <= end_rent_hour; h++) {
                        for (int m = 0; m <= 50; m += 10) {
                            abletime.add(new Timepoint(h, m));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tpd.setSelectableTimes(abletime.toArray(new Timepoint[abletime.size()]));
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @OnClick(R.id.fragment_calendar_dialog_reservation_button)
    void reservation(View view) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        try {
            ChoiceDialogFragment choiceDialogFragment = ChoiceDialogFragment.newInstance(
                    ChoiceDialogFragment.RENTER_RESERVATION_DATE_CHOICE,
                    bicycleId,
                    format.parse(startDateTime),
                    format.parse(endDateTime)
            );
            choiceDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "SimpleDateFormat parse ERROR..", e);
        }
    }
}
