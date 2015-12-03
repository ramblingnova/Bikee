package com.example.tacademy.bikee.etc.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.FontManager;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.lister.ListerMainActivity;
import com.example.tacademy.bikee.renter.RenterMainActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class NoChoiceDialogFragment extends DialogFragment {
    private Intent intent;
    private TextView tv;
    private static final String ARG_PARAM1 = "MESSAGE";
    private static final String ARG_PARAM2 = "SUB_MESSAGE";
    private static final String BICYCLE_ID = "BICYCLEID";
    private static final String RESERVE_ID = "RESERVEID";
    private static final String STATUS = "STATUS";
    private String message;
    private String subMessage;
    public static final int NO_MESSAGE = 0;
    public static final int RENTER_COMPLETE_RESERVATION = 1;
    public static final int RENTER_CANCEL_RESERVATION = 2;
    public static final int RENTER_COMPLETE_PAYMENT = 3;
    public static final int RENTER_APPROVED_RESERVATION = 4;
    public static final int LISTER_APPROVE_RESERVATION = 5;
    public static final int LISTER_CANCEL_RESERVATION = 6;
    public static final int LISTER_MODIFY_BICYCLE_INFORMATION = 7;
    public static final int RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION = 8;
    public static final int NO_SUB_MESSAGE = 100;
    public static final int RENTER_MOVE_TO_RENTER_RESERVATION = 101;
    public static final int RENTER_MOVE_TO_SEARCH_RESULT = 102;
    public static final int LISTER_MOVE_TO_LISTER_REQUESTED = 103;
    private String bicycleId;
    private String reserveId;
    private String status;

    public static NoChoiceDialogFragment newInstance(int param1) {
        NoChoiceDialogFragment fragment = new NoChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoChoiceDialogFragment newInstance(int param1, int param2) {
        NoChoiceDialogFragment fragment = new NoChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoChoiceDialogFragment newInstance(String bicycleId, String reserveId, String status, int param1, int param2) {
        NoChoiceDialogFragment fragment = new NoChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(BICYCLE_ID, bicycleId);
        args.putString(RESERVE_ID, reserveId);
        args.putString(STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);

        if (getArguments() != null) {
            message = getMessageById(getArguments().getInt(ARG_PARAM1));
            if (getArguments().getInt(ARG_PARAM2, NO_SUB_MESSAGE) != NO_SUB_MESSAGE) {
                subMessage = getSubMessageById(getArguments().getInt(ARG_PARAM2));
            }
            if ((null != getArguments().get(BICYCLE_ID)) && (null != getArguments().getString(RESERVE_ID)) && (null != getArguments().getString(STATUS))) {
                bicycleId = getArguments().getString(BICYCLE_ID);
                reserveId = getArguments().getString(RESERVE_ID);
                status = getArguments().getString(STATUS);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_choice_dialog, container, false);

        tv = (TextView) view.findViewById(R.id.fragment_no_choice_dialog_message_text_view);
        tv.setText(message);
        tv = (TextView) view.findViewById(R.id.fragment_no_choice_dialog_submessage_text_view);
        if (subMessage != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(subMessage);
        }

        if (getArguments().getInt(ARG_PARAM2, RENTER_MOVE_TO_RENTER_RESERVATION) == RENTER_MOVE_TO_RENTER_RESERVATION
                || getArguments().getInt(ARG_PARAM2, RENTER_MOVE_TO_SEARCH_RESULT) == RENTER_MOVE_TO_SEARCH_RESULT) {
            intent = new Intent(getContext().getApplicationContext(), RenterMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//                    public static final int RENTER_MOVE_TO_RENTER_RESERVATION = 101;
//                    public static final int RENTER_MOVE_TO_SEARCH_RESULT = 102;
//                    public static final int LISTER_MOVE_TO_LISTER_REQUESTED = 103;
        } else if ((getArguments().getInt(ARG_PARAM1, LISTER_APPROVE_RESERVATION) == LISTER_APPROVE_RESERVATION)
                && (getArguments().getInt(ARG_PARAM2, LISTER_MOVE_TO_LISTER_REQUESTED) == LISTER_MOVE_TO_LISTER_REQUESTED)) {
            NetworkManager.getInstance().reserveStatus(bicycleId, reserveId, status, new Callback<ReceiveObject>() {
                @Override
                public void success(ReceiveObject receiveObject, Response response) {
                    Log.i("result", "RC onResponse Success");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(getContext().getApplicationContext(), ListerMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }, 1500);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("error", "onFailure Error : " + error.toString());
                }
            });
        } else if (getArguments().getInt(ARG_PARAM2, LISTER_MOVE_TO_LISTER_REQUESTED) == LISTER_MOVE_TO_LISTER_REQUESTED) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent = new Intent(getContext().getApplicationContext(), ListerMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 1500);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        d.getWindow().setLayout(400, 400);
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.x = 0;
        params.y = 0;
        d.getWindow().setAttributes(params);
    }

    private String getMessageById(int id) {
        switch (id) {
            case NO_MESSAGE:
                message = null;
                break;
            case RENTER_COMPLETE_RESERVATION:
                message = "예약요청 완료!";
                break;
            case RENTER_CANCEL_RESERVATION:
                message = "예약요청이 취소되었습니다.";
                break;
            case RENTER_COMPLETE_PAYMENT:
                message = "결제가 완료되었습니다.";
                break;
            case RENTER_APPROVED_RESERVATION:
                message = "예약이 승인되었습니다.";
                break;
            case LISTER_APPROVE_RESERVATION:
                message = "님에 대한 예약이 승인되었습니다.";
                break;
            case LISTER_CANCEL_RESERVATION:
                message = "님에 대한 예약이 취소되었습니다.";
                break;
            case LISTER_MODIFY_BICYCLE_INFORMATION:
                message = "수정되었습니다.";
                break;
            case RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION:
                message = "예약이 취소되었습니다.";
                break;
            default:
                message = null;
                break;
        }
        return message;
    }

    private String getSubMessageById(int id) {
        switch (id) {
            case NO_SUB_MESSAGE:
                subMessage = null;
                break;
            case RENTER_MOVE_TO_RENTER_RESERVATION:
                subMessage = "예약관리페이지로 이동합니다.";
                break;
            case RENTER_MOVE_TO_SEARCH_RESULT:
                subMessage = "검색결과 페이지로 이동합니다.";
                break;
            case LISTER_MOVE_TO_LISTER_REQUESTED:
                subMessage = "예약관리페이지로 이동합니다.";
            default:
                message = null;
                break;
        }
        return subMessage;
    }
}
