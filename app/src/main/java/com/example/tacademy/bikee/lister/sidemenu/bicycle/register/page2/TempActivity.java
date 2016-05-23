package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.tacademy.bikee.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private AutoCompleteTextView mAutoCompleteTextView;
    private ArrayAdapter<String> adapter;
    private List<Integer> placeTypeList;
    private AutocompleteFilter mAutocompleteFilter;
    private PendingResult<AutocompletePredictionBuffer> result;
    private LatLngBounds bounds;
    private LatLng southwest, northeast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        /*mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edit);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        adapter.setNotifyOnChange(true);
        mAutoCompleteTextView.setAdapter(adapter);
        mAutoCompleteTextView.addTextChangedListener(tw);
        placeTypeList = new ArrayList<>();
        placeTypeList.add(Place.TYPE_GEOCODE);
        mAutocompleteFilter = AutocompleteFilter.create(placeTypeList);

        southwest = new LatLng(33.0, 124.5);
        northeast = new LatLng(38.9, 132.0);
        bounds = new LatLngBounds(southwest, northeast);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.i("TEXTWATCHER", "BEFORE");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            /*Log.i("TEXTWATCHER", "ONCHANGE");
//            if (s.toString().matches(ImageUtil.REGEX_ADDRESS)) {
                result = Places.GeoDataApi.getAutocompletePredictions(
                        mGoogleApiClient,
                        mAutoCompleteTextView.getText().toString(),
                        bounds,
                        mAutocompleteFilter
                );
                result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                    @Override
                    public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                        adapter.clear();
                        for (AutocompletePrediction autocompletePrediction : autocompletePredictions) {
                            String s = autocompletePrediction.getDescription();
                            if (s.startsWith("대한민국")) {
                                s = s.split("대한민국 ")[1];
                                Log.i("RESULT", s);
                                adapter.add(s);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        autocompletePredictions.release();
                    }
                });
//            }*/
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i("TEXTWATCHER", "AFTER");
        }
    };
}
