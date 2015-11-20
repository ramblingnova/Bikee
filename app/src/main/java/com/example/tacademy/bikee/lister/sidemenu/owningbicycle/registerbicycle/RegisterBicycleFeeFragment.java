package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tacademy.bikee.R;

public class RegisterBicycleFeeFragment extends Fragment {
    EditText hour;
    EditText day;
    EditText month;

    public static RegisterBicycleFeeFragment newInstance() {
        return new RegisterBicycleFeeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_fee, container, false);

        hour = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_hour_edit_text);
        day = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_day_edit_text);
        month = (EditText)view.findViewById(R.id.fragment_register_bicycle_fee_per_month_edit_text);

        return view;
    }

    public int getHour() {
        return Integer.parseInt(hour.getText().toString());
    }

    public int getDay() {
        return Integer.parseInt(day.getText().toString());
    }

    public int getMonth() {
        return Integer.parseInt(month.getText().toString());
    }
}
