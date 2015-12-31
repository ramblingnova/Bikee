package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dialog.NoChoiceDialogFragment;
import com.example.tacademy.bikee.renter.RenterMainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinallyRequestReservationCancelDialogFragment extends DialogFragment {
    private Intent intent;

    private static final String ARG_PARAM = "MESSAGE";

    public static FinallyRequestReservationCancelDialogFragment newInstance(int param) {
        FinallyRequestReservationCancelDialogFragment fragment = new FinallyRequestReservationCancelDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finally_request_cancel_reservation_dialog, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        d.getWindow().setLayout(400, 400);
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        DisplayMetrics matrix = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(matrix);
        params.x = 0;
        params.y = 0;
        d.getWindow().setAttributes(params);
    }

    @OnClick(R.id.fragment_choice_dialog_button1) void doButton1Work() {
        NoChoiceDialogFragment dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION, NoChoiceDialogFragment.RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION);
        dialog.show(getActivity().getSupportFragmentManager(), "custom");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(getContext().getApplicationContext(), RenterMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 1500);
    }

    @OnClick(R.id.fragment_choice_dialog_button2) void doButton2Work() {
        dismiss();
    }
}
