package com.bikee.www.renter.searchresult;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bikee.www.etc.manager.PropertyManager;
import com.bikee.www.renter.searchresult.filter.FilterActivity;
import com.bikee.www.renter.searchresult.list.SearchResultListFragment;
import com.bikee.www.renter.searchresult.map.SearchResultMapFragment;
import com.bikee.www.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultFragment extends Fragment implements SearchSwitchView.OnCheckedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";

    private Intent intent;
    private Fragment currentFragment;
    private SearchResultListFragment searchResultListFragment;
    private SearchResultMapFragment searchResultMapFragment;
    private SearchSwitchView searchSwitchView;
    @Bind(R.id.fragment_search_result_search_edit_text) EditText address;

    private boolean b = false;

    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_RESOLVE_ERROR = 1001; // Request code to use when launching the resolution activity
    private boolean mResolvingError = false; // Bool to track whether the app is already resolving an error

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
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, SearchResultFragment.this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        searchResultListFragment = new SearchResultListFragment();
        searchResultMapFragment = new SearchResultMapFragment();

        searchSwitchView = (SearchSwitchView) v.findViewById(R.id.search_bar_switch);
        searchSwitchView.setOnCheckedListener(this);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!searchSwitchView.isChecked())
            fragmentChange(false);
    }

    // Location관련
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            // sharedPreparence에 저장 -> Map과 List에 위도, 경도 전송
            PropertyManager.getInstance().setLatitude("" + location.getLatitude());
            PropertyManager.getInstance().setLongitude("" + location.getLongitude());
            if (searchResultListFragment != null)
                searchResultListFragment.onResponseLocation(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.onResponseLocation(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
        } else {
            // sharedPreparence에 저장된 값 호출 -> Map과 List에 위도, 경도 전송
            if (searchResultListFragment != null)
                searchResultListFragment.onResponseLocation(
                        PropertyManager.getInstance().getLatitude(),
                        PropertyManager.getInstance().getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.onResponseLocation(
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
        Log.i("MYLOCATION", "Latitude : " + location.getLatitude() + ", Longitude : " + location.getLongitude());
        if (location != null) {
            // sharedPreparence에 저장 -> Map과 List에 위도, 경도 전송
            PropertyManager.getInstance().setLatitude("" + location.getLatitude());
            PropertyManager.getInstance().setLongitude("" + location.getLongitude());
            if (searchResultListFragment != null)
                searchResultListFragment.onResponseLocation(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.onResponseLocation(
                        "" + location.getLatitude(),
                        "" + location.getLongitude()
                );
        } else {
            // sharedPreparence에 저장된 값 호출 -> Map과 List에 위도, 경도 전송
            if (searchResultListFragment != null)
                searchResultListFragment.onResponseLocation(
                        PropertyManager.getInstance().getLatitude(),
                        PropertyManager.getInstance().getLongitude()
                );
            if (searchResultMapFragment != null)
                searchResultMapFragment.onResponseLocation(
                        PropertyManager.getInstance().getLatitude(),
                        PropertyManager.getInstance().getLongitude()
                );

        }
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
                b = true;
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
    public void onChecked(boolean isChecked) {
        fragmentChange(isChecked);
    }


    @OnClick(R.id.fragment_search_result_filter_image_view)
    void adjustFilter() {
        intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra("ADDRESS", address.getText().toString());
        getActivity().startActivityForResult(intent, FilterActivity.FILTER_ACTIVITY);
    }

    private void fragmentChange(boolean isChecked) {
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