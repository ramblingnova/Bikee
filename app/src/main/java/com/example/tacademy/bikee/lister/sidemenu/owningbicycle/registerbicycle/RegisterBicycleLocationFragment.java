package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

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
import com.example.tacademy.bikee.etc.Util;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleLocationFragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap gm;
    private EditText address;
    private Geocoder geocoder;
    private List<Address> listAddress;
    private Address addr;
    private double latitude;
    private double longitude;

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

        address = (EditText) view.findViewById(R.id.fragment_register_bicycle_location_location_edit_text);
        address.addTextChangedListener(tw);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.fragment_register_bicycle_location_text_view)
    void spinner() {
        Intent intent = new Intent(getActivity(), TempActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        gm.setMyLocationEnabled(true);

        gm.setIndoorEnabled(true);
        gm.setTrafficEnabled(false);
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gm.getUiSettings().setZoomControlsEnabled(true);
        gm.getUiSettings().setCompassEnabled(false);
        gm.getUiSettings().setRotateGesturesEnabled(false);
        gm.getUiSettings().setTiltGesturesEnabled(false);
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().matches(Util.REGEX_HANGUL))
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
    };

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
                if (listAddress.size() > 0) { // 주소값이 존재 하면
                    addr = listAddress.get(0); // Address형태로
//                    Log.i("RESULT", "주소로부터 취득한 위도 : " + lat + ", 경도 : " + lng);
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }

//    https://developers.daum.net/services/apis/local/geo/coord2addr 참고
}
