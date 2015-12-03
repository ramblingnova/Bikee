package com.example.tacademy.bikee.etc.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.reservationbicycle.RequestPaymentActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class ChoiceDialogFragment extends DialogFragment implements View.OnClickListener {

    private NoChoiceDialogFragment dialog;
    private TextView tv;
    private Button btn;
    private static final String BICYCLE_ID = "BICYCLEID";
    private static final String RESERVE_ID = "RESERVEID";
    private static final String STATUS = "STATUS";
    private static final String ARG_PARAM = "MESSAGE";
    private String message;
    private String button1Text;
    private String button2Text;
    private String bicycleId;
    private String reserveId;
    private String status;
    public static final int RENTER_CANCEL_RESERVATION = 1;
    public static final int RENTER_REQUEST_RESERVATION = 2;
    public static final int RENTER_PAY_RESERVATION = 3;
    public static final int RENTER_CANCEL_ALREADY_RESERVATION = 4;
    public static final int LISTER_CANCEL_RESERVATION = 5;

    public static ChoiceDialogFragment newInstance(int param) {
        ChoiceDialogFragment fragment = new ChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    public static ChoiceDialogFragment newInstance(String bicycleId, String reserveId, String status, int param) {
        ChoiceDialogFragment fragment = new ChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putString(BICYCLE_ID, bicycleId);
        args.putString(RESERVE_ID, reserveId);
        args.putString(STATUS, status);
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);

        if (getArguments() != null) {
            message = getMessageById(getArguments().getInt(ARG_PARAM));
            button1Text = getButton1TextById(getArguments().getInt(ARG_PARAM));
            button2Text = getButton2TextById(getArguments().getInt(ARG_PARAM));
            bicycleId = getArguments().getString(BICYCLE_ID);
            reserveId = getArguments().getString(RESERVE_ID);
            status = getArguments().getString(STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_dialog, container, false);

        tv = (TextView) view.findViewById(R.id.fragment_choice_dialog_message_text_view);
        tv.setText(message);
        btn = (Button) view.findViewById(R.id.fragment_choice_dialog_button1);
        btn.setText(button1Text);
        btn.setOnClickListener(this);
        btn = (Button) view.findViewById(R.id.fragment_choice_dialog_button2);
        btn.setText(button2Text);
        btn.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_choice_dialog_button1:
                switch (getArguments().getInt(ARG_PARAM)) {
                    case RENTER_CANCEL_RESERVATION:
                        dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION, NoChoiceDialogFragment.RENTER_MOVE_TO_SEARCH_RESULT);
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        break;
                    case RENTER_REQUEST_RESERVATION:
                        dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_RESERVATION, NoChoiceDialogFragment.RENTER_MOVE_TO_RENTER_RESERVATION);
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        break;
                    case RENTER_CANCEL_ALREADY_RESERVATION:
                        // RC
                        NetworkManager.getInstance().reserveStatus(bicycleId, reserveId, status, new Callback<ReceiveObject>() {
                            @Override
                            public void success(ReceiveObject receiveObject, Response response) {
                                Log.i("result", "RC onResponse Success");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("error", "onFailure Error : " + error.toString());
                            }
                        });
                        dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_CANCEL_ALREADY_RESERVATION, NoChoiceDialogFragment.RENTER_MOVE_TO_RENTER_RESERVATION);
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        break;
                    case RENTER_PAY_RESERVATION:
                        dismiss();
                        break;
                    case LISTER_CANCEL_RESERVATION:
                        // RC
                        NetworkManager.getInstance().reserveStatus(bicycleId, reserveId, status, new Callback<ReceiveObject>() {
                            @Override
                            public void success(ReceiveObject receiveObject, Response response) {
                                Log.i("result", "RC onResponse Success");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("error", "onFailure Error : " + error.toString());
                            }
                        });
                        dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.LISTER_CANCEL_RESERVATION, NoChoiceDialogFragment.LISTER_MOVE_TO_LISTER_REQUESTED);
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 1500);
                        // cancel
                        break;
                }
                break;
            case R.id.fragment_choice_dialog_button2:
                switch (getArguments().getInt(ARG_PARAM)) {
                    case RENTER_CANCEL_RESERVATION:
                        dismiss();
                        break;
                    case RENTER_REQUEST_RESERVATION:
                        dismiss();
                        break;
                    case RENTER_CANCEL_ALREADY_RESERVATION:
                        dismiss();
                        break;
                    case RENTER_PAY_RESERVATION:
//                        dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_PAYMENT, NoChoiceDialogFragment.RENTER_MOVE_TO_RENTER_RESERVATION);
//                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        // TODO : 결제 웹뷰
                        Intent intent = new Intent(MyApplication.getmContext(), RequestPaymentActivity.class);
                        startActivity(intent);
                        break;
                    case LISTER_CANCEL_RESERVATION:
                        dismiss();
                        break;
                }
//                    Intent intent = new Intent(getContext().getApplicationContext(), RenterMainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    dismiss();
//                    getActivity().finish(); 뭘까?
//                else if (i == 1) {
//                    NoChoiceDialogFragment dialog = new NoChoiceDialogFragment().newInstance(NoChoiceDialogFragment.RENTER_COMPLETE_RESERVATION);
//                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
//                    Intent intent = new Intent(getContext().getApplicationContext(), RenterMainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    dismiss();
//                    getActivity().finish();
                break;
        }
    }

    private String getMessageById(int id) {
        switch (id) {
            case RENTER_CANCEL_RESERVATION:
                message = "예약요청을 정말 취소하시겠습니까?";
                break;
            case RENTER_REQUEST_RESERVATION:
                message = "주인장에게 예약요청을 하시겠습니까?";
                break;
            case RENTER_PAY_RESERVATION:
                message = "자동결제하시겠습니까?";
                break;
            case RENTER_CANCEL_ALREADY_RESERVATION:
                message = "예약을 정말 취소하시겠습니까?";
                break;
            case LISTER_CANCEL_RESERVATION:
                message = "님에 대한 예약을 정말 취소하시겠습니까?";
                break;
        }
        return message;
    }

    private String getButton1TextById(int id) {
        switch (id) {
            case RENTER_CANCEL_RESERVATION:
                button1Text = "네";
                break;
            case RENTER_REQUEST_RESERVATION:
                button1Text = "네";
                break;
            case RENTER_PAY_RESERVATION:
                button1Text = "아니요";
                break;
            case RENTER_CANCEL_ALREADY_RESERVATION:
                button1Text = "네";
                break;
            case LISTER_CANCEL_RESERVATION:
                button1Text = "네";
                break;
        }
        return button1Text;
    }

    private String getButton2TextById(int id) {
        switch (id) {
            case RENTER_CANCEL_RESERVATION:
                button2Text = "아니요";
                break;
            case RENTER_REQUEST_RESERVATION:
                button2Text = "아니요";
                break;
            case RENTER_PAY_RESERVATION:
                button2Text = "네";
                break;
            case RENTER_CANCEL_ALREADY_RESERVATION:
                button2Text = "아니요";
                break;
            case LISTER_CANCEL_RESERVATION:
                button2Text = "아니요";
                break;
        }
        return button2Text;
    }
}
