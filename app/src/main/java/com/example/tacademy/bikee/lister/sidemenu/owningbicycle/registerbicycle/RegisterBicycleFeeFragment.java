package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;

public class RegisterBicycleFeeFragment extends Fragment {
    EditText hour;
    EditText day;
    EditText month;

    public static RegisterBicycleFeeFragment newInstance() {
        return new RegisterBicycleFeeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_fee, container, false);

        hour = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_hour_edit_text);
        day = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_day_edit_text);
        month = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_month_edit_text);
        hour.addTextChangedListener(tw);
        day.addTextChangedListener(tw);
        month.addTextChangedListener(tw);
//        FontManager.getInstance().setTextViewFont(FontManager.NOTO,
//                hour,
//                day,
//                month
//        );

        return view;
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ((hour.getText().length() > 0) && (day.getText().length() > 0) && (month.getText().length() > 0)) {
                if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
                    registerBicycleINF.setEnable(true);
                }
            } else {
                if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
                    registerBicycleINF.setEnable(false);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public int getHour() {
        return Integer.parseInt(hour.getText().toString());
    }

    public int getDay() {
        return Integer.parseInt(day.getText().toString());
    }

    public int getMonth() {
        return Integer.parseInt(month.getText().toString());
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
