package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.utils.RegExUtil;
import com.example.tacademy.bikee.lister.sidemenu.bicycle.register.RegisterBicycleINF;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleLocationFragment extends Fragment implements TextWatcher, OnMapReadyCallback {
    // TODO : UI 작업 필요
    private View view;
    @Bind(R.id.fragment_register_bicycle_location_location_edit_text)
    EditText address;
    private Geocoder geocoder;
    private List<Address> listAddress;
    private Address addr;
    private double latitude;
    private double longitude;
    private RegisterBicycleINF registerBicycleINF;

    public static RegisterBicycleLocationFragment newInstance() {
        return new RegisterBicycleLocationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_register_bicycle_location, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        ButterKnife.bind(this, view);

        address.addTextChangedListener(this);

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().matches(RegExUtil.REGEX_HANGUL))
            findGeoPoint(s.toString());
        if ((latitude != 0) && (longitude != 0)) {
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

    // TODO : DELME?
    @OnClick(R.id.fragment_register_bicycle_location_text_view)
    void spinner() {
        Intent intent = new Intent(getActivity(), TempActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap gm = googleMap;
        gm.setMyLocationEnabled(true);

        gm.setIndoorEnabled(true);
        gm.setTrafficEnabled(false);
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gm.getUiSettings().setZoomControlsEnabled(true);
        gm.getUiSettings().setCompassEnabled(false);
        gm.getUiSettings().setRotateGesturesEnabled(false);
        gm.getUiSettings().setTiltGesturesEnabled(false);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private void findGeoPoint(String address) {
        if (!TextUtils.isEmpty(address)) {
            try {
                listAddress = geocoder.getFromLocationName(address, 1);
                if (listAddress.size() > 0) {
                    addr = listAddress.get(0);
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
