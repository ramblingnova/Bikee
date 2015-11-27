package com.example.tacademy.bikee.renter.searchresult.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

import com.example.tacademy.bikee.common.POI;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultINF;
import com.example.tacademy.bikee.renter.searchresult.SearchResultMapItem;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.FilteredBicycleDetailInformationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnCameraChangeListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    final private Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    final private Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();
    private GoogleMap gm;
    private LocationManager locationManager;
    private View view;
//    private SearchResultINF searchResultINF;
//    private TimerTask timerTask;
//    private Timer timer;
//    private boolean battery;
    private String latitude = null;
    private String longitude = null;

    public SearchResultMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for (Marker marker : mPOIResolver.keySet()) {
            marker.remove();
        }
        mMarkerResolver.clear();
        mPOIResolver.clear();

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_search_result_map, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        requestData();
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (battery) {
//                    requestData();
//                }
//            }
//        };
//        timer = new Timer();
//        battery = true;
//        timer.schedule(timerTask, 0, 5000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        //if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        provider = LocationManager.NETWORK_PROVIDER;
        try {
            locationManager.requestLocationUpdates(provider, 1000, 1, mListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        Log.i("MAP", "STOP!!");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("MAP", "INIT!!");
        gm = googleMap;
        gm.setMyLocationEnabled(true);

        gm.setIndoorEnabled(true);
        gm.setTrafficEnabled(true);
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gm.getUiSettings().setZoomControlsEnabled(true);

        gm.setOnInfoWindowClickListener(this);
        gm.setInfoWindowAdapter(new BicycleInfoWindowView(MyApplication.getmContext(), mPOIResolver));

        gm.setOnMapClickListener(this);
        gm.setOnMarkerClickListener(this);

        if ((null == latitude) || (null == longitude)) {
            latitude = PropertyManager.getInstance().getLatitude();
            longitude = PropertyManager.getInstance().getLongitude();
        }
        moveMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    private void moveMap(double lat, double lng) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(lat, lng));
        builder.zoom(15);
//        builder.bearing(30);
//        builder.tilt(30);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        gm.animateCamera(update);
    }

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                if (gm != null) {
                    if ((null == latitude) || (null == longitude)) {
                        latitude = PropertyManager.getInstance().getLatitude();
                        longitude = PropertyManager.getInstance().getLongitude();
                    } else {
                        latitude = "" + location.getLatitude();
                        longitude = "" + location.getLongitude();
                        PropertyManager.getInstance().setLatitude(latitude);
                        PropertyManager.getInstance().setLongitude(longitude);
                    }
                    moveMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
                } else {
                    if ((null == latitude) || (null == longitude)) {
                        latitude = PropertyManager.getInstance().getLatitude();
                        longitude = PropertyManager.getInstance().getLongitude();
                    } else {
                        latitude = "" + location.getLatitude();
                        longitude = "" + location.getLongitude();
                        PropertyManager.getInstance().setLatitude(latitude);
                        PropertyManager.getInstance().setLongitude(longitude);
                    }
                }
                try {
                    locationManager.removeUpdates(mListener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
        intent.putExtra("ID", mPOIResolver.get(marker).getItem().getBicycleId());
        getActivity().startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        POI poi = mPOIResolver.get(marker);
        Toast.makeText(getContext().getApplicationContext(), "title : " + poi.getName(), Toast.LENGTH_SHORT).show();

        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    public void onResponseLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void requestData() {
        if ((null == latitude) || (null == longitude)) {
            latitude = PropertyManager.getInstance().getLatitude();
            longitude = PropertyManager.getInstance().getLongitude();
        }
        String lat = latitude;
        String lon = longitude;
        NetworkManager.getInstance().selectAllMapBicycle(
                lon, lat, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "Map!! onResponse Code : " + receiveObject.getCode()
                                        + ", Success : " + receiveObject.isSuccess()
                                        + ", Msg : " + receiveObject.getMsg()
                                        + ", Error : "
                        );
                        List<Result> results = receiveObject.getResult();
                        String imageURL;
                        for (Result result : results) {
                            if ((null == result.getImage().getCdnUri()) || (null == result.getImage().getFiles())) {
                                imageURL = "";
                            } else {
                                imageURL = result.getImage().getCdnUri() + "/mini_" + result.getImage().getFiles().get(0);
                            }
                            Log.i("result", "onResponse : " + result.get_id()
                                            + ", ImageURL : " + imageURL
                                            + ", Name : " + result.getTitle()
                                            + ", Type : " + result.getType()
                                            + ", Height : " + result.getHeight()
                                            + ", Price.month : " + result.getPrice().getMonth()
                                            + ", lat : " + result.getLoc().getCoordinates().get(1)
                                            + ", lon : " + result.getLoc().getCoordinates().get(0)
                            );

                            // 지도에 뿌리기
                            MarkerOptions options = new MarkerOptions();
                            options.position(new LatLng(result.getLoc().getCoordinates().get(1), result.getLoc().getCoordinates().get(0)));
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
                            options.anchor(0.5f, 1);

                            POI poi = new POI();
                            poi.setItem(
                                    new SearchResultMapItem(
                                            result.get_id(),
                                            imageURL,
                                            result.getTitle(),
                                            result.getType(),
                                            result.getHeight(),
                                            "" + result.getPrice().getMonth()
                                    )
                            );

                            poi.setName("자전거 제목");
                            poi.setUpperAddrName("가격|종류|신장");

                            options.title(poi.getName());
                            options.snippet(poi.getAddress());

                            options.draggable(true);

                            Marker m = gm.addMarker(options);

                            mMarkerResolver.put(poi, m);
                            mPOIResolver.put(m, poi);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
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
