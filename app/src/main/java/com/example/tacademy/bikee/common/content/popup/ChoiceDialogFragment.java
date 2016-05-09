package com.example.tacademy.bikee.common.content.popup;

import android.app.Dialog;
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

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.manager.NetworkManager;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-05-10.
 */
public class ChoiceDialogFragment extends DialogFragment {
    @Bind(R.id.fragment_choice_dialog_bottom_buttons_left_button)
    Button bottomButtonsLeftButton;
    @Bind(R.id.fragment_choice_dialog_bottom_buttons_right_button)
    Button bottomButtonsRightButton;

    private int from;
    private String bicycleId;
    private Date startDate;
    private Date endDate;

    private static final String TAG = "CHOICE_DIALOG_F";

    public static ChoiceDialogFragment newInstance(int from, String bicycleId, Date startDate, Date endDate) {
        ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("FROM", from);
        args.putString("BICYCLE_ID", bicycleId);
        args.putSerializable("START_DATE", startDate);
        args.putSerializable("END_DATE", endDate);
        choiceDialogFragment.setArguments(args);

        return choiceDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);

        Bundle args = getArguments();
        from = args.getInt("FROM");
        switch (from) {
            case CalendarDialogFragment.from:
                bicycleId = args.getString("BICYCLE_ID");
                startDate = (Date) args.getSerializable("START_DATE");
                endDate = (Date) args.getSerializable("END_DATE");
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_dialog, container, false);

        ButterKnife.bind(this, view);

        init();

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

    public void init() {
        switch (from) {
            case CalendarDialogFragment.from:
                bottomButtonsLeftButton.setText("예");
                bottomButtonsRightButton.setText("아니오");
                break;
        }
    }

    @OnClick({R.id.fragment_choice_dialog_bottom_buttons_left_button,
            R.id.fragment_choice_dialog_bottom_buttons_right_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_choice_dialog_bottom_buttons_left_button:
                switch (from) {
                    case CalendarDialogFragment.from: {
                        // TODO : 예약하기
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "예약하기");

                        Reserve reserve = new Reserve();
                        reserve.setRentStart(startDate);
                        reserve.setRentEnd(endDate);
                        NetworkManager.getInstance().insertReservation(
                                bicycleId,
                                reserve,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (receiveObject.isSuccess()) {
                                            if (receiveObject.isSuccess()) {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "insertReservation onResponse Success : " + receiveObject.isSuccess()
                                                                    + ", Code : " + receiveObject.getCode()
                                                                    + ", Msg : " + receiveObject.getMsg()
                                                    );
                                                PlainDialogFragment plainDialogFragment = PlainDialogFragment.newInstance(from, receiveObject.getMsg());
                                                plainDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
                                            } else {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "insertReservation onResponse Success : " + receiveObject.isSuccess());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onFailure Error : " + t.toString());
                                    }
                                });
                        break;
                    }
                }
                break;
            case R.id.fragment_choice_dialog_bottom_buttons_right_button:
                switch (from) {
                    case CalendarDialogFragment.from: {
                        // TODO : 예약보류
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "예약보류");
                        dismiss();
                        break;
                    }
                }
                break;
        }
    }
}
