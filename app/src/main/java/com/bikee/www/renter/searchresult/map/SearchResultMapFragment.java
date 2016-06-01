package com.bikee.www.renter.searchresult.map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikee.www.common.content.ContentActivity;
import com.bikee.www.etc.manager.PropertyManager;
import com.bikee.www.renter.searchresult.SearchResultItem;
import com.bikee.www.renter.searchresult.filter.FilterActivity;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;

import com.bikee.www.etc.MyApplication;
import com.bikee.www.etc.dao.ReceiveObject;
import com.bikee.www.etc.dao.Result;
import com.bikee.www.etc.manager.NetworkManager;
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
    // TODO : android api 버전 23이상은 필요한 권한을 체크해야 함
    // READ_EXTERNAL_STORAGE : SharedPreference에서 데이터를 읽기 위함
    // WRITE_EXTERNAL_STORAGE : SharedPreference에서 데이터를 쓰기 위함, Google map을 사용하기 위함
    // INTERNET : Network통신을 하기 위함, Google map을 사용하기 위함
    // READ_GSERVICES : Google map을 사용하기 위함
    // ACCESS_FINE_LOCATION : Google map을 사용하기 위함
    // ACCESS_COARSE_LOCATION : Google map을 사용하기 위함
    private final Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    private final Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private View view;
    private BicycleInfoWindowView bicycleInfoWindowView;
    private String userLatitude = null;
    private String userLongitude = null;
    private String filter;
    private Marker current_marker;

    public static int from = 2;
    private static final int PERMISSION_REQUEST_CODE = 101;
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
                                            intent.setData(Uri.parse("package:" + "com.bikee.wwww"));
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
        }

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
