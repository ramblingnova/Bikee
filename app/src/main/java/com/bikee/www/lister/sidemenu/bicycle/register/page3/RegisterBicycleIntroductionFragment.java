package com.bikee.www.lister.sidemenu.bicycle.register.page3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bikee.www.R;
import com.bikee.www.lister.sidemenu.bicycle.register.RegisterBicycleINF;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleIntroductionFragment extends Fragment implements TextWatcher {
    @Bind(R.id.fragment_register_bicycle_introduction_bicycle_name)
    EditText nameEditText;
    @Bind(R.id.fragment_register_bicycle_introduction_bicycle_introduction)
    EditText introductionEditText;
    private RegisterBicycleINF registerBicycleINF;
    private InputMethodManager imm;

    public static RegisterBicycleIntroductionFragment newInstance() {
        return new RegisterBicycleIntroductionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_introduction, container, false);

        ButterKnife.bind(this, view);

        nameEditText.addTextChangedListener(this);
        introductionEditText.addTextChangedListener(this);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }

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

    @OnClick({R.id.fragment_register_bicycle_introduction_bicycle_name_layout,
            R.id.fragment_register_bicycle_introduction_bicycle_introduction_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_introduction_bicycle_name_layout:
                nameEditText.requestFocus();
                imm.showSoftInput(nameEditText, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.fragment_register_bicycle_introduction_bicycle_introduction_layout:
                introductionEditText.requestFocus();
                imm.showSoftInput(introductionEditText, InputMethodManager.SHOW_FORCED);
                break;
        }
    }

    public String getName() {
        return nameEditText.getText().toString();
    }

    public String getIntroduction() {
        return introductionEditText.getText().toString();
    }

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
