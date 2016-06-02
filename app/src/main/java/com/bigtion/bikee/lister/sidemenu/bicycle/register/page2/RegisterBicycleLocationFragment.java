package com.bigtion.bikee.lister.sidemenu.bicycle.register.page2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.common.popup.AddressDialogFragment;
import com.bigtion.bikee.common.popup.OnAddressDialogFragmentClickListener;
import com.bigtion.bikee.etc.dao.GetAddressReceiveObject;
import com.bigtion.bikee.etc.manager.DaumNetworkManager;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.RegisterBicycleINF;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBicycleLocationFragment extends Fragment implements TextWatcher, OnMapReadyCallback {
    @Bind(R.id.fragment_register_bicycle_location_address_edit_text)
    EditText addressEditText;
    @Bind(R.id.fragment_register_bicycle_location_address_search_button)
    Button addressSearchButton;

    private View view;
    private Geocoder geocoder;
    private List<Address> listAddress;
    private Address addr;
    private double latitude;
    private double longitude;
    private RegisterBicycleINF registerBicycleINF;
    private GoogleMap googleMap;
    private Marker preMarker;

    private static final int PERMISSION_REQUEST_CODE = 104;
    private static final String TAG = "REGISTER_BICYCLE_L_F";

    public static RegisterBicycleLocationFragment newInstance() {
        return new RegisterBicycleLocationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getContext());

        latitude = Double.parseDouble(
                PropertyManager
                        .getInstance()
                        .getLatitude()
        );
        longitude = Double.parseDouble(
                PropertyManager
                        .getInstance()
                        .getLongitude()
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            if ((googleMap != null)
                    && !googleMap.isMyLocationEnabled()
                    && (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
                googleMap.setMyLocationEnabled(true);
            else if ((googleMap != null)
                    && !googleMap.isMyLocationEnabled()
                    && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                googleMap.setMyLocationEnabled(false);
        }
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_register_bicycle_location_small_map);
        mapFragment.getMapAsync(this);

        addressEditText.addTextChangedListener(this);

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (s.toString().matches(CheckUtil.REGEX_HANGUL))
//            findGeoPoint(s.toString());
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

    @Override
    public void onMapReady(GoogleMap gm) {
        this.googleMap = gm;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("권한 요청")
                            .setMessage("위치정보 권한이 있어야 앱이 올바르게 작동합니다.")
                            .setPositiveButton(
                                    "설정",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                                            startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                                        }
                                    })
                            .setNegativeButton(
                                    "취소",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                            .setCancelable(true)
                            .create()
                            .show();
                } else {
                    // TODO : DELME 도달할 수 없는 경로로 판단됨
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            },
                            PERMISSION_REQUEST_CODE
                    );
                }
            } else {
                this.googleMap.setMyLocationEnabled(true);
            }
        } else if (Build.VERSION.SDK_INT < 23) {
            this.googleMap.setMyLocationEnabled(true);
        }

        this.googleMap.setIndoorEnabled(true);
        this.googleMap.setTrafficEnabled(false);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setRotateGesturesEnabled(false);
        this.googleMap.getUiSettings().setTiltGesturesEnabled(false);

        moveMap(latitude, longitude);

        this.googleMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        DaumNetworkManager.getInstance().getAddress(
                                latLng.longitude,
                                latLng.latitude,
                                null,
                                new Callback<GetAddressReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<GetAddressReceiveObject> call, Response<GetAddressReceiveObject> response) {
                                        GetAddressReceiveObject receiveObject = response.body();

                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "getAddress onResponse fullname : " + receiveObject.getFullName());

                                        addressEditText.setText(receiveObject.getFullName());

                                        MarkerOptions options = new MarkerOptions();
                                        options.position(new LatLng(latitude, longitude));
                                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
                                        options.anchor(0.5f, 0.5f);
                                        Marker marker = googleMap.addMarker(options);
                                        if (preMarker != null)
                                            preMarker.remove();
                                        preMarker = marker;
                                    }

                                    @Override
                                    public void onFailure(Call<GetAddressReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "getAddress onFailure", t);
                                    }
                                }
                        );
                    }
                }
        );
    }

    @OnClick(R.id.fragment_register_bicycle_location_address_search_button)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_location_address_search_button:
                AddressDialogFragment addressDialogFragment
                        = AddressDialogFragment.newInstance(addressEditText.getText().toString());
                addressDialogFragment.setOnAddressDialogFragmentClickListener(
                        new OnAddressDialogFragmentClickListener() {
                            @Override
                            public void onAddressDialogFragmentClick(
                                    View view,
                                    String addr,
                                    double lat,
                                    double lng) {
                                addressEditText.setText(addr);
                                latitude = lat;
                                longitude = lng;
                                moveMap(latitude, longitude);

                                MarkerOptions options = new MarkerOptions();
                                options.position(new LatLng(latitude, longitude));
                                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
                                options.anchor(0.5f, 0.5f);
                                Marker marker = googleMap.addMarker(options);
                                if (preMarker != null)
                                    preMarker.remove();
                                preMarker = marker;
                            }
                        }
                );
                addressDialogFragment.show(getFragmentManager(), TAG);
                break;
        }
    }

    private void moveMap(double lat, double lng) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(lat, lng));
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        googleMap.moveCamera(update);
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
