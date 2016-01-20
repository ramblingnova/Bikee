package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.BroadcastReceiver;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TempActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.edit);
        items = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        adapter.setNotifyOnChange(true);
        actv.setAdapter(adapter);
        List<Integer> list = new ArrayList<>();
        list.add(Place.TYPE_LOCALITY);
        list.add(Place.TYPE_AIRPORT);
        list.add(Place.TYPE_BANK);
        list.add(Place.TYPE_TRAIN_STATION);
        list.add(Place.TYPE_SUBWAY_STATION);
        list.add(Place.TYPE_BUS_STATION);
        list.add(Place.TYPE_LIBRARY);
        list.add(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_1);
        list.add(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_2);
        list.add(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_3);
        list.add(Place.TYPE_GAS_STATION);
        AutocompleteFilter mAutocompleteFilter = AutocompleteFilter.create(list);
        PendingResult<AutocompletePredictionBuffer> result = Places.GeoDataApi.getAutocompletePredictions(
                mGoogleApiClient,
                actv.getText().toString(),
                new LatLngBounds(new LatLng(33.0, 124.5), new LatLng(38.9, 132.0)),
                mAutocompleteFilter
        );
        result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                for (AutocompletePrediction autocompletePrediction : autocompletePredictions) {
                    Toast.makeText(TempActivity.this, "" + autocompletePrediction.getDescription(), Toast.LENGTH_SHORT).show();
                    Log.i("RESULT", "" + autocompletePrediction.getDescription());
//                    items.add("" + autocompletePrediction.getDescription());
                    adapter.add("" + autocompletePrediction.getDescription());
                }
                adapter.notifyDataSetChanged();
            }
        });
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
}
