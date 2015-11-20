package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.tacademy.bikee.R;

public class RegisterBicycleIntroductionFragment extends Fragment {
    private EditText editText;

    public static RegisterBicycleIntroductionFragment newInstance() {
        return new RegisterBicycleIntroductionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_introduction, container, false);

        editText = (EditText)view.findViewById(R.id.fragment_register_bicycle_introduction_bicycle_name);

        return view;
    }

    public String getName() {
        return editText.getText().toString();
    }
}
