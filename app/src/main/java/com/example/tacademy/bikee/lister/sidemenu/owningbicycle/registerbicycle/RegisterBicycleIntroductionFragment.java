package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.manager.FontManager;

public class RegisterBicycleIntroductionFragment extends Fragment {
    private EditText nameEditText;
    private EditText introductionEditText;

    public static RegisterBicycleIntroductionFragment newInstance() {
        return new RegisterBicycleIntroductionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_introduction, container, false);

        nameEditText = (EditText)view.findViewById(R.id.fragment_register_bicycle_introduction_bicycle_name);
        introductionEditText = (EditText)view.findViewById(R.id.fragment_register_bicycle_introduction_bicycle_introduction);
        nameEditText.addTextChangedListener(tw);
        introductionEditText.addTextChangedListener(tw);

        return view;
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (("" != nameEditText.getText().toString()) && ("" != introductionEditText.getText().toString())) {
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

    public String getName() {
        return nameEditText.getText().toString();
    }

    public String getIntroduction() {
        return introductionEditText.getText().toString();
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
