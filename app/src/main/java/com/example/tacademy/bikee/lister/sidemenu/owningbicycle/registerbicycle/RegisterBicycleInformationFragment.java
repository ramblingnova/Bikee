package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

public class RegisterBicycleInformationFragment extends Fragment {
    private static final String REGISTER_BICYCLE_INFORMATION_FRAGMENT = "REGISTER_BICYCLE_INFORMATION_FRAGMENT";
    private String mParam1;

    public static RegisterBicycleInformationFragment newInstance(String page) {
        RegisterBicycleInformationFragment fragment = new RegisterBicycleInformationFragment();
        Bundle args = new Bundle();
        args.putString(REGISTER_BICYCLE_INFORMATION_FRAGMENT, page);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterBicycleInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(REGISTER_BICYCLE_INFORMATION_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_bicycle_information, container, false);

        return v;
    }
}
