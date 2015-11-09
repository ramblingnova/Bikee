package com.example.tacademy.bikee.renter.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.POI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class SearchResultMapFragment extends Fragment implements OnMapReadyCallback {

    final Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    final Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();
    GoogleMap gm;

    public SearchResultMapFragment() {

    }

    private static View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(v != null) {
            ViewGroup parent = (ViewGroup)v.getParent();
            if(parent != null)
                parent.removeView(v);
        }

        try {
            v = inflater.inflate(R.layout.fragment_search_result_map, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch(Exception e) {

        }

        Button btn = (Button)v.findViewById(R.id.temp_marker);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerOptions options = new MarkerOptions();
                CameraPosition position = gm.getCameraPosition();
                options.position(position.target);
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                options.anchor(0.5f, 1);
                POI poi = new POI();

                poi.name = "자전거 제목";
                poi.upperAddrName = "가격|종류|신장";

                options.title(poi.name);
                options.snippet(poi.getAddress());

                options.draggable(true);

                Marker m = gm.addMarker(options);

                mMarkerResolver.put(poi, m);
                mPOIResolver.put(m, poi);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        gm.setMyLocationEnabled(true);

        gm.setIndoorEnabled(true);
        gm.setTrafficEnabled(true);
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gm.getUiSettings().setZoomControlsEnabled(true);

        gm.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                POI poi = mPOIResolver.get(marker);
                Toast.makeText(getContext().getApplicationContext(), "title : " + poi.name, Toast.LENGTH_SHORT).show();
                marker.showInfoWindow();
                Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
                getActivity().startActivity(intent);
                return false;
            }
        });
    }
}
