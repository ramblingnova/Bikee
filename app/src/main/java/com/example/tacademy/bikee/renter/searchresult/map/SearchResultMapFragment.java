package com.example.tacademy.bikee.renter.searchresult.map;

import android.app.Activity;
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

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;

import com.example.tacademy.bikee.common.content.ContentActivity;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultItem;
import com.example.tacademy.bikee.renter.searchresult.filter.FilterActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnCameraChangeListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    final private Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    final private Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private View view;
    private BicycleInfoWindowView bicycleInfoWindowView;
    private String userLatitude = null;
    private String userLongitude = null;
    private String filter;
    private Marker current_marker;

    public static int from = 2;
    private static final String TAG = "SEARCH_R_M_ACTIVITY";

    public SearchResultMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;
        try {
            locationManager.requestLocationUpdates(provider, 1000, 1, mListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        googleMap.clear();
//        mMarkerResolver.clear();
//        mPOIResolver.clear();

        requestData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == FilterActivity.FILTER_ACTIVITY) {
            Log.d(TAG, "onActivityResult");
            // TODO : 필터 적용해서 마커 그릴 것

            List<String> f = new ArrayList<String>();
            userLatitude = data.getStringExtra("LATITUDE");
            userLongitude = data.getStringExtra("LONGITUDE");

            filter = "{";
            if (data.getStringExtra("START_DATE") != null)
                f.add("\"start\":\"" + data.getStringExtra("START_DATE") + "\"");
            if (data.getStringExtra("END_DATE") != null)
                f.add("\"end\":\"" + data.getStringExtra("END_DATE") + "\"");
            if (data.getStringExtra("TYPE") != null)
                f.add("\"type\":\"" + data.getStringExtra("TYPE") + "\"");
            if (data.getStringExtra("HEIGHT") != null)
                f.add("\"height\":\"" + data.getStringExtra("HEIGHT") + "\"");
            f.add("\"smartlock\":" + data.getBooleanExtra("SMART_LOCK", false));
            for (int i = 0; i < f.size(); i++)
                filter += (i == 0 ? "" : ",") + f.get(i);
            filter += "}";
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMyLocationEnabled(true);

        this.googleMap.setIndoorEnabled(true);
        this.googleMap.setTrafficEnabled(true);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setRotateGesturesEnabled(false);
        this.googleMap.getUiSettings().setTiltGesturesEnabled(false);

        this.googleMap.setOnInfoWindowClickListener(this);
        this.googleMap.setInfoWindowAdapter(bicycleInfoWindowView = BicycleInfoWindowView.getInstance(MyApplication.getmContext(), mPOIResolver));
        bicycleInfoWindowView.setOnImageLoadListener(onImageLoadListener);

        this.googleMap.setOnMapClickListener(this);
        this.googleMap.setOnMarkerClickListener(this);

        if ((null == userLatitude) || (null == userLongitude)) {
            userLatitude = PropertyManager.getInstance().getLatitude();
            userLongitude = PropertyManager.getInstance().getLongitude();
        }
        moveMap(Double.parseDouble(userLatitude), Double.parseDouble(userLongitude));
    }

    private void moveMap(double lat, double lng) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(lat, lng));
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        googleMap.moveCamera(update);
    }

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                if ((null == userLatitude) || (null == userLongitude)) {
                    userLatitude = PropertyManager.getInstance().getLatitude();
                    userLongitude = PropertyManager.getInstance().getLongitude();
                } else {
                    userLatitude = "" + location.getLatitude();
                    userLongitude = "" + location.getLongitude();
                    PropertyManager.getInstance().setLatitude(userLatitude);
                    PropertyManager.getInstance().setLongitude(userLongitude);
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
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", mPOIResolver.get(marker).getItem().getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", mPOIResolver.get(marker).getItem().getLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", mPOIResolver.get(marker).getItem().getLongitude());
        getActivity().startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        POI poi = mPOIResolver.get(marker);
        SearchResultItem searchResultItem = poi.getItem();
        current_marker = marker;
        bicycleInfoWindowView.setImageView(getActivity(), searchResultItem.getImageURL());
        return true;
    }

    BicycleInfoWindowView.OnImageLoadListener onImageLoadListener = new BicycleInfoWindowView.OnImageLoadListener() {
        @Override
        public void onImageLoad() {
            current_marker.showInfoWindow();
        }
    };

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
        this.userLatitude = latitude;
        this.userLongitude = longitude;
    }

    private void requestData() {
        if ((null == userLatitude) || (null == userLongitude)) {
            userLatitude = PropertyManager.getInstance().getLatitude();
            userLongitude = PropertyManager.getInstance().getLongitude();
        }
        String lat = userLatitude;
        String lon = userLongitude;
        NetworkManager.getInstance().selectAllMapBicycle(
                lon,
                lat,
                filter,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
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
                                imageURL = result.getImage().getCdnUri() + result.getImage().getFiles().get(0);
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
                            options.anchor(0.5f, 0.5f);

                            POI poi = new POI();
                            poi.setItem(
                                    new SearchResultItem(
                                            result.get_id(),
                                            imageURL,
                                            result.getTitle(),
                                            result.getHeight(),
                                            result.getType(),
                                            "" + result.getPrice().getMonth(),
                                            result.getDistance(),
                                            result.getLoc().getCoordinates().get(1),
                                            result.getLoc().getCoordinates().get(0)
                                    )
                            );

                            poi.setName("자전거 제목");
                            poi.setUpperAddrName("가격|종류|신장");

                            options.title(poi.getName());
                            options.snippet(poi.getAddress());

                            options.draggable(true);

                            Marker m = googleMap.addMarker(options);

                            mMarkerResolver.put(poi, m);
                            mPOIResolver.put(m, poi);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
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
