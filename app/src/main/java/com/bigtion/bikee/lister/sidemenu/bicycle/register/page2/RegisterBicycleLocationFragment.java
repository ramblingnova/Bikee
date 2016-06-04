package com.bigtion.bikee.lister.sidemenu.bicycle.register.page2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.bigtion.bikee.etc.utils.CheckUtil;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBicycleLocationFragment extends Fragment implements TextWatcher, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    @Bind(R.id.fragment_register_bicycle_location_address_edit_text)
    EditText addressEditText;
    @Bind(R.id.fragment_register_bicycle_location_address_search_button)
    Button addressSearchButton;

    private View view;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private double mLatitude;
    private double mLongitude;
    private boolean hasLatLng;
    private RegisterBicycleINF registerBicycleINF;

    private static final int PERMISSION_REQUEST_CODE = 104;
    private static final String TAG = "REGISTER_BICYCLE_L_F";

    public static RegisterBicycleLocationFragment newInstance() {
        return new RegisterBicycleLocationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = null;
        mGoogleMap = null;
        mMarker = null;
        mLatitude = Double.parseDouble(
                PropertyManager
                        .getInstance()
                        .getLatitude()
        );
        mLongitude = Double.parseDouble(
                PropertyManager
                        .getInstance()
                        .getLongitude()
        );
        hasLatLng = false;
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
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.fragment_register_bicycle_location_small_map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        ButterKnife.bind(this, view);

        addressEditText.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            if ((mGoogleMap != null)
                    && !mGoogleMap.isMyLocationEnabled()
                    && (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
                mGoogleMap.setMyLocationEnabled(true);
            else if ((mGoogleMap != null)
                    && !mGoogleMap.isMyLocationEnabled()
                    && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                mGoogleMap.setMyLocationEnabled(false);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);
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
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else if (Build.VERSION.SDK_INT < 23) {
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(final LatLng latLng) {
                        DaumNetworkManager.getInstance().getAddress(
                                latLng.longitude,
                                latLng.latitude,
                                null,
                                new Callback<GetAddressReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<GetAddressReceiveObject> call, Response<GetAddressReceiveObject> response) {
                                        GetAddressReceiveObject receiveObject = response.body();

                                        if (receiveObject != null) {
                                            addressEditText.setText(receiveObject.getFullName());

                                            mLatitude = latLng.latitude;
                                            mLongitude = latLng.longitude;

                                            MarkerOptions options = new MarkerOptions();
                                            options.position(latLng);
                                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
                                            options.anchor(0.5f, 0.5f);
                                            if (mMarker != null)
                                                mMarker.remove();
                                            mMarker = mGoogleMap.addMarker(options);

                                            hasLatLng = true;
                                            if ((null != registerBicycleINF)
                                                    && (!registerBicycleINF.getEnable()))
                                                registerBicycleINF.setEnable(true);
                                        }
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
        mGoogleMap.setOnMarkerClickListener(this);

        moveMap(mLatitude, mLongitude, false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0)
            addressSearchButton.setEnabled(true);
        else
            addressSearchButton.setEnabled(false);

        if (hasLatLng) {
            if ((null != registerBicycleINF)
                    && (!registerBicycleINF.getEnable()))
                registerBicycleINF.setEnable(true);
        } else {
            if ((null != registerBicycleINF)
                    && (registerBicycleINF.getEnable()))
                registerBicycleINF.setEnable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
                                    String address,
                                    double latitude,
                                    double longitude) {
                                addressEditText.setText(address);

                                mLatitude = latitude;
                                mLongitude = longitude;
                                moveMap(mLatitude, mLongitude, true);

                                MarkerOptions options = new MarkerOptions();
                                options.position(new LatLng(mLatitude, mLongitude));
                                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
                                options.anchor(0.5f, 0.5f);
                                if (mMarker != null)
                                    mMarker.remove();
                                mMarker = mGoogleMap.addMarker(options);

                                hasLatLng = true;
                                if ((null != registerBicycleINF)
                                        && (!registerBicycleINF.getEnable()))
                                    registerBicycleINF.setEnable(true);
                            }
                        }
                );
                addressDialogFragment.show(getFragmentManager(), TAG);
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    private void moveMap(double latitude, double longitude, boolean smooth) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder
                .target(new LatLng(latitude, longitude))
                .zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        if (smooth)
            mGoogleMap.animateCamera(update);
        else
            mGoogleMap.moveCamera(update);
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
