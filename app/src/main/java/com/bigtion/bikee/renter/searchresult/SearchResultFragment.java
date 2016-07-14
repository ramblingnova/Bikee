package com.bigtion.bikee.renter.searchresult;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.common.popup.AddressDialogFragment;
import com.bigtion.bikee.common.popup.OnAddressDialogFragmentClickListener;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.renter.searchresult.filter.FilterActivity;
import com.bigtion.bikee.renter.searchresult.list.SearchResultListFragment;
import com.bigtion.bikee.renter.searchresult.map.OnSearchResultMapFragmentListener;
import com.bigtion.bikee.renter.searchresult.map.SearchResultMapFragment;
import com.bigtion.bikee.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
//import android.location.LocationListener; vs import com.google.android.gms.location.LocationListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    @Bind(R.id.fragment_search_result_search_edit_text)
    EditText addressEditText;

    private Intent intent;
    private Fragment currentFragment;
    private SearchResultListFragment searchResultListFragment;
    private SearchResultMapFragment searchResultMapFragment;
    private SearchSwitchView searchSwitchView;
    private boolean hasLatLng;
    private double mLatitude;
    private double mLongitude;
    private String completeAddress;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false; // Bool to track whether the app is already resolving an error

    private static final int PERMISSION_REQUEST_CODE = 2302;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String TAG = "SEARCH_RESULT_F";
    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";

    public SearchResultFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        if (mGoogleApiClient.isConnected()) {
            LocationRequest request = LocationRequest.create();
            request.setInterval(10000);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            request.setNumUpdates(1);
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
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, SearchResultFragment.this);
                }
            } else if (Build.VERSION.SDK_INT < 23) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, SearchResultFragment.this);
            }
        }

        hasLatLng = false;
        completeAddress = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        ButterKnife.bind(this, view);

        // TODO : new?
        searchResultListFragment = new SearchResultListFragment();
        searchResultMapFragment = new SearchResultMapFragment();
        searchResultMapFragment.setOnSearchResultMapFragmentListener(
                new OnSearchResultMapFragmentListener() {
                    @Override
                    public void setAddress(String address, double latitude, double longitude) {
                        addressEditText.setText(address);
                        completeAddress = address;
                        mLatitude = latitude;
                        mLongitude = longitude;
                    }

                    @Override
                    public void setHasLatLng(boolean b) {
                        hasLatLng = b;
                    }
                }
        );

        addressEditText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            if (searchResultMapFragment != null)
                                searchResultMapFragment.removeMarker();
                            hasLatLng = false;
                            completeAddress = "";
                        } else if (s.length() != completeAddress.length()) {
                            hasLatLng = false;
                            completeAddress = "";
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        searchSwitchView = (SearchSwitchView) view.findViewById(R.id.search_bar_switch);
        searchSwitchView.setOnCheckedListener(
                new SearchSwitchView.OnCheckedListener() {
                    @Override
                    public void onChecked(boolean isChecked) {
                        changeChildFragment(isChecked);
                    }
                }
        );

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == FilterActivity.FILTER_ACTIVITY) {
            for (Fragment uploadType : getChildFragmentManager()
                    .getFragments()) {
                if (uploadType != null) {
                    uploadType.onActivityResult(requestCode, resultCode, data);
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        switch (resultCode) {
            case FilterActivity.RESULT_OK:
                break;
            case FilterActivity.RESULT_CANCELED:
                break;
        }
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == Activity.RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!searchSwitchView.isChecked())
            changeChildFragment(false);
    }

    // Location관련
    @Override
    public void onConnected(Bundle bundle) {
        Location location = null;
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
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        } else if (Build.VERSION.SDK_INT < 23) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (location != null) {
            PropertyManager.getInstance().setLatitude("" + location.getLatitude());
            PropertyManager.getInstance().setLongitude("" + location.getLongitude());
            if (searchResultListFragment != null)
                searchResultListFragment.changeUserPosition(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.changeUserPosition(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
        } else {
            if (searchResultListFragment != null)
                searchResultListFragment.changeUserPosition(
                        PropertyManager.getInstance().getLatitude(),
                        PropertyManager.getInstance().getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.changeUserPosition(
                        PropertyManager.getInstance().getLatitude(),
                        PropertyManager.getInstance().getLongitude()
                );

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            mResolvingError = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onLocationChanged latitude : " + location.getLatitude()
                    + ", longitude : " + location.getLongitude());
        PropertyManager.getInstance().setLatitude("" + location.getLatitude());
        PropertyManager.getInstance().setLongitude("" + location.getLongitude());
        if (searchResultListFragment != null)
            searchResultListFragment.changeUserPosition(
                    "" + location.getLatitude(),
                    "" + location.getLongitude()
            );
        if (searchResultMapFragment != null)
            searchResultMapFragment.changeUserPosition(
                    "" + location.getLatitude(),
                    "" + location.getLongitude()
            );
    }

    @OnClick(R.id.fragment_search_result_filter_image_view)
    void onClick() {
        if (hasLatLng) {
            intent = new Intent(getActivity(), FilterActivity.class);
            intent.putExtra("HAS_LAT_LNG", hasLatLng);
            intent.putExtra("LATITUDE", mLatitude);
            intent.putExtra("LONGITUDE", mLongitude);
            intent.putExtra("ADDRESS", completeAddress);
            getActivity().startActivityForResult(intent, FilterActivity.FILTER_ACTIVITY);
        } else if ((addressEditText.getText().toString().length() == 0)
                || (addressEditText.getText().toString().equals(""))) {
            intent = new Intent(getActivity(), FilterActivity.class);
            intent.putExtra("HAS_LAT_LNG", hasLatLng);
            getActivity().startActivityForResult(intent, FilterActivity.FILTER_ACTIVITY);
        } else {
            AddressDialogFragment addressDialogFragment = AddressDialogFragment.newInstance(addressEditText.getText().toString());
            addressDialogFragment.setOnAddressDialogFragmentClickListener(
                    new OnAddressDialogFragmentClickListener() {
                        @Override
                        public void onAddressDialogFragmentClick(View view, String address, double latitude, double longitude) {
                            addressEditText.setText(address);

                            if (searchResultMapFragment != null)
                                searchResultMapFragment.changeMarkerPosition(
                                        latitude,
                                        longitude
                                );

                            hasLatLng = true;

                            intent = new Intent(getActivity(), FilterActivity.class);
                            intent.putExtra("HAS_LAT_LNG", hasLatLng);
                            intent.putExtra("ADDRESS", addressEditText.getText().toString());
                            intent.putExtra("LATITUDE", latitude);
                            intent.putExtra("LONGITUDE", longitude);
                            getActivity().startActivityForResult(intent, FilterActivity.FILTER_ACTIVITY);
                        }
                    }
            );
            addressDialogFragment.show(getFragmentManager(), TAG);
        }
    }

    private void changeChildFragment(boolean isChecked) {
        String CURRENT_TAG = (isChecked) ? SEARCH_RESULT_LIST_FRAGMENT_TAG : SEARCH_RESULT_MAP_FRAGMENT_TAG;
        Fragment old = getChildFragmentManager().findFragmentByTag(CURRENT_TAG);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        if (currentFragment != null && currentFragment != old && old != null) {
            ft.detach(currentFragment);
        }

        if (old == null) {
            currentFragment = (isChecked) ? searchResultListFragment : searchResultMapFragment;
            ft.add(R.id.fragment_search_result_container, searchResultListFragment, SEARCH_RESULT_LIST_FRAGMENT_TAG);
            ft.detach(searchResultListFragment);
            ft.add(R.id.fragment_search_result_container, searchResultMapFragment, SEARCH_RESULT_MAP_FRAGMENT_TAG);
        } else if (currentFragment != old) {
            ft.attach(currentFragment = old);
        }

        ft.commit();
    }
}
