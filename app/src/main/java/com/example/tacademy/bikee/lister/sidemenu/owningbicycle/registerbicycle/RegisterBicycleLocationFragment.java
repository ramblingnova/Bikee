package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

public class RegisterBicycleLocationFragment extends Fragment {

    public static RegisterBicycleLocationFragment newInstance() {
        return new RegisterBicycleLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_bicycle_location, container, false);
        return v;
    }
}
