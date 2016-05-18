package com.example.tacademy.bikee.renter.sidemenu.creditcard.register.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.interfaces.OnAdapterClickListener;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-05-12.
 */
public class SelectDateDialogFragment extends DialogFragment implements OnAdapterClickListener {
    @Bind(R.id.fragment_select_date_dialog_recycler_view)
    RecyclerView recyclerView;

    private GetDialogResultListener getDialogResultListener;
    private LinearLayoutManager layoutManager;
    private SelectDateAdapter adapter;
    private int mode;
    private String birthDateYearString;
    private String birthDateMonthString;
    private String birthDateDayString;
    private int birthDateYearInt;
    private int birthDateMonthInt;
    private int birthDateDayInt;

    public static final int EXPIRATION_DATE_MONTH = 1;
    public static final int EXPIRATION_DATE_YEAR = 2;
    public static final int BIRTH_DATE_YEAR = 3;
    public static final int BIRTH_DATE_MONTH = 4;
    public static final int BIRTH_DATE_DAY = 5;
    private static final String TAG = "DATE_SELECT_DIALOG_F";

    public static SelectDateDialogFragment newInstance(int mode) {
        SelectDateDialogFragment fragment = new SelectDateDialogFragment();

        Bundle args = new Bundle();
        args.putInt("MODE", mode);

        fragment.setArguments(args);

        return fragment;
    }

    public static SelectDateDialogFragment newInstance(int mode,
                                                       String birthDateYear,
                                                       String birthDateMonth,
                                                       String birthDateDay) {
        SelectDateDialogFragment fragment = new SelectDateDialogFragment();

        Bundle args = new Bundle();
        args.putInt("MODE", mode);
        args.putString("BIRTH_DATE_YEAR", birthDateYear);
        args.putString("BIRTH_DATE_MONTH", birthDateMonth);
        args.putString("BIRTH_DATE_DAY", birthDateDay);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);

        Bundle args = getArguments();
        mode = args.getInt("MODE");

        if ((mode == BIRTH_DATE_YEAR)
                || (mode == BIRTH_DATE_MONTH)
                || (mode == BIRTH_DATE_DAY)) {
            birthDateYearString = args.getString("BIRTH_DATE_YEAR");
            birthDateMonthString = args.getString("BIRTH_DATE_MONTH");
            birthDateDayString = args.getString("BIRTH_DATE_DAY");

            if (birthDateYearString != null) {
                birthDateYearInt = Integer.parseInt(birthDateYearString);
            }

            if (birthDateMonthString != null) {
                if (birthDateMonthString.startsWith("0"))
                    birthDateMonthString = birthDateMonthString.substring(1);
                birthDateMonthInt = Integer.parseInt(birthDateMonthString);
            }

            if (birthDateDayString != null) {
                if (birthDateDayString.startsWith("0"))
                    birthDateDayString = birthDateDayString.substring(1);
                birthDateDayInt = Integer.parseInt(birthDateDayString);
            }
        }

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new SelectDateAdapter();
        adapter.setOnAdapterClickListener(this);

        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_date_dialog, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog d = getDialog();
        if ((mode == EXPIRATION_DATE_MONTH)
                || (mode == BIRTH_DATE_MONTH)
                || (mode == BIRTH_DATE_DAY))
            d.getWindow().setLayout(
                    getResources().getDimensionPixelSize(R.dimen.dialog_fragment_width1),
                    getResources().getDimensionPixelSize(R.dimen.dialog_fragment_height)
            );
        else if ((mode == BIRTH_DATE_YEAR)
                || (mode == EXPIRATION_DATE_YEAR))
            d.getWindow().setLayout(
                    getResources().getDimensionPixelSize(R.dimen.dialog_fragment_width2),
                    getResources().getDimensionPixelSize(R.dimen.dialog_fragment_height)
            );

        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 0;

        d.getWindow().setAttributes(params);
        d.setCanceledOnTouchOutside(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        getDialogResultListener.getDialogResult(mode, (String) item);
        dismiss();
    }

    public void init() {
        switch (mode) {
            case EXPIRATION_DATE_MONTH:
                adapter.add("직접입력");
                for (int i = 0; i < 12; i++)
                    if ((i >= 0) && (i < 9))
                        adapter.add("0" + (i + 1));
                    else
                        adapter.add("" + (i + 1));
                break;
            case EXPIRATION_DATE_YEAR:
                adapter.add("직접입력");
                for (int i = 0; i < 10; i++)
                    adapter.add("" + (Calendar.getInstance().get(Calendar.YEAR) + i));
                break;
            case BIRTH_DATE_YEAR: {
                adapter.add("직접입력");
                Calendar calendar = Calendar.getInstance();
                adapter.add("" + calendar.get(Calendar.YEAR));
                for (int i = 0; i < 69; i++) {
                    calendar.add(Calendar.YEAR, -1);
                    adapter.add("" + calendar.get(Calendar.YEAR));
                }
                break;
            }
            case BIRTH_DATE_MONTH:
                // TODO : 31일을 고른 상태에서 월을 2월로 바꿨을 경우 처리 필요
                adapter.add("직접입력");
                for (int i = 0; i < 12; i++)
                    if ((i >= 0) && (i < 9))
                        adapter.add("0" + (i + 1));
                    else
                        adapter.add("" + (i + 1));
                break;
            case BIRTH_DATE_DAY: {
                if ((birthDateYearString != null)
                        && (birthDateMonthString != null)) {
                    adapter.add("직접입력");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(birthDateYearInt, birthDateMonthInt - 1, 1);
                    for (int i = 0; i < calendar.getActualMaximum(Calendar.DATE); i++)
                        if ((i >= 0) && (i < 9))
                            adapter.add("0" + (i + 1));
                        else
                            adapter.add("" + (i + 1));
                } else {
                    Log.d(TAG, "else");
                    // TODO : 연/월을 중에 하나라도 입력하지 않고 팝업을 열었을 때, 처리 필요
                }
                break;
            }
            default:
                break;
        }
    }

    public void setGetDialogResultListener(GetDialogResultListener getDialogResultListener) {
        this.getDialogResultListener = getDialogResultListener;
    }
}
