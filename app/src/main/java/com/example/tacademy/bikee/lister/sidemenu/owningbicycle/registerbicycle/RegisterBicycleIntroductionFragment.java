package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        return view;
    }

    public String getName() {
        return nameEditText.getText().toString();
    }

    public String getIntroduction() {
        return introductionEditText.getText().toString();
    }
}
