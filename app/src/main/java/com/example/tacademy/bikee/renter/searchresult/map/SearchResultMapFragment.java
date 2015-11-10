package com.example.tacademy.bikee.renter.searchresult.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

import com.example.tacademy.bikee.common.POI;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.FilteredBicycleDetailInformationActivity;
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

import java.util.HashMap;
import java.util.Map;

public class SearchResultMapFragment extends Fragment implements OnMapReadyCallback {

    final Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    final Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();
    GoogleMap gm;
    LocationManager locationManager;

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

                poi.setName("자전거 제목");
                poi.setUpperAddrName("가격|종류|신장");

                options.title(poi.getName());
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
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        //if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            provider = LocationManager.NETWORK_PROVIDER;
        try {
            locationManager.requestLocationUpdates(provider,1000,1,mListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
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

        gm.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
                Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        gm.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                POI poi = mPOIResolver.get(marker);
                Toast.makeText(getContext().getApplicationContext(), "title : " + poi.getName(), Toast.LENGTH_SHORT).show();
                marker.showInfoWindow();
                return true;
            }
        });

        if(cacheLocation!=null){
            moveMap(cacheLocation.getLatitude(),cacheLocation.getLongitude());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void moveMap(double lat, double lng) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(lat, lng));
        builder.zoom(16);
//        builder.bearing(30);
//        builder.tilt(30);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10);
        gm.animateCamera(update);
    }

    Location cacheLocation;
    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (gm != null) {
                moveMap(location.getLatitude(), location.getLongitude());
                Toast.makeText(getActivity(), "lat:"+location.getLatitude()+", lng:"+location.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                cacheLocation = location;
            }
            try{
                locationManager.removeUpdates(mListener);
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
