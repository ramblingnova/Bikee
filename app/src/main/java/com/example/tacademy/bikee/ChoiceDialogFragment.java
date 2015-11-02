package com.example.tacademy.bikee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class ChoiceDialogFragment extends DialogFragment {

    String s;
    int i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_dialog, container, false);
        TextView tv = (TextView) view.findViewById(R.id.fragment_choice_dialog_text_view);
        tv.setText(s);
        Button btn = (Button) view.findViewById(R.id.fragment_choice_dialog_cancel_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 아니오 -> FinallyRegisterBicycleActivity로
                dismiss();
            }
        });
        btn = (Button) view.findViewById(R.id.fragment_choice_dialog_confirm_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    // TODO 예 -> 예약 프래그먼트로

                    NoChoiceDialogFragment dialog = new NoChoiceDialogFragment();
                    dialog.setMessage("예약요청 되었습니다..");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dismiss();
                    getActivity().finish();
                } else if (i == 1) {
                    // TODO 예 -> SearchResultMapFragment로, 예약 프래그먼트로
                    NoChoiceDialogFragment dialog = new NoChoiceDialogFragment();
                    dialog.setMessage("예약이 취소되었습니다.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    dismiss();
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        d.getWindow().setLayout(R.dimen.dialog_width, R.dimen.dialog_height);//view.getWidth(), view.getHeight());
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.x = 0;
        params.y = 0;
        d.getWindow().setAttributes(params);
    }

    public void setMessage(String s, int i) {
        this.s = s;
        this.i = i;
    }

}
