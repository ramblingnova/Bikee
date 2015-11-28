package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.tacademy.bikee.R;

public class FinallyRequestReservationConfirmDialogFragment extends DialogFragment {
    private static final String ARG_PARAM = "MESSAGE";

    public static FinallyRequestReservationConfirmDialogFragment newInstance(int param) {
        FinallyRequestReservationConfirmDialogFragment fragment = new FinallyRequestReservationConfirmDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_finally_request_reservation_confirm_dialog, container, false);
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
}
