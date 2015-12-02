package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.tacademy.bikee.R;

public class RegisterBicycleInformationFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private CheckBox cb;
    private String type;
    private String height;

    public static RegisterBicycleInformationFragment newInstance() {
        return new RegisterBicycleInformationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_information, container, false);

        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box1);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box2);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box3);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box4);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box5);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box6);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_type_check_box7);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box1);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box2);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box3);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box4);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box5);
        cb.setOnCheckedChangeListener(this);
        cb = (CheckBox) view.findViewById(R.id.bicycle_recommendation_height_check_box6);
        cb.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.bicycle_type_check_box1:
                type = isChecked ? "A" : null;
                break;
            case R.id.bicycle_type_check_box2:
                type = isChecked ? "B" : null;
                break;
            case R.id.bicycle_type_check_box3:
                type = isChecked ? "C" : null;
                break;
            case R.id.bicycle_type_check_box4:
                type = isChecked ? "D" : null;
                break;
            case R.id.bicycle_type_check_box5:
                type = isChecked ? "E" : null;
                break;
            case R.id.bicycle_type_check_box6:
                type = isChecked ? "F" : null;
                break;
            case R.id.bicycle_type_check_box7:
                type = isChecked ? "G" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box1:
                height = isChecked ? "01" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box2:
                height = isChecked ? "02" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box3:
                height = isChecked ? "03" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box4:
                height = isChecked ? "04" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box5:
                height = isChecked ? "05" : null;
                break;
            case R.id.bicycle_recommendation_height_check_box6:
                height = isChecked ? "06" : null;
                break;
        }

        if ((null != type) && (null != height)) {
            if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(true);
            }
        } else {
            if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(false);
            }
        }
    }

    public String getType() {
        return type;
    }

    public String getHeight() {
        return height;
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
