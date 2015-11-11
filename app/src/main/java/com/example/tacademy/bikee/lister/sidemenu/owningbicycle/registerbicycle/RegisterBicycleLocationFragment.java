package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterBicycleLocationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public static RegisterBicycleLocationFragment newInstance(String param1) {
        RegisterBicycleLocationFragment fragment = new RegisterBicycleLocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterBicycleLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_bicycle_location, container, false);
        return v;
    }
}
