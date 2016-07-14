package com.bigtion.bikee.renter.searchresult.map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.common.content.ContentActivity;
import com.bigtion.bikee.etc.dao.GetAddressReceiveObject;
import com.bigtion.bikee.etc.manager.DaumNetworkManager;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.renter.searchresult.SearchResultItem;
import com.bigtion.bikee.renter.searchresult.filter.FilterActivity;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;

import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.bigtion.bikee.etc.dao.Result;
import com.bigtion.bikee.etc.manager.NetworkManager;
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
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {
    private Stack<Call> callStack;
    private View view;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private Marker bicycleMarker;
    private MarkerOptions mMarkerOptions;
    private MarkerOptions bicycleMarkerOptions;
    private final Map<Marker, SearchResultItem> bicycleMarkers = new HashMap<>();
    private BicycleInfoWindowView bicycleInfoWindowView;
    private String userLatitude = null;
    private String userLongitude = null;
    private double mLatitude;
    private double mLongitude;
    private String filter;
    private OnSearchResultMapFragmentListener onSearchResultMapFragmentListener;

    public static final int from = 2;
    private static final int PERMISSION_REQUEST_CODE = 2303;
    private static final String TAG = "SEARCH_R_M_ACTIVITY";

    public SearchResultMapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (Marker marker : bicycleMarkers.keySet())
            marker.remove();
        bicycleMarkers.clear();

        if ((userLatitude == null)
                || (userLongitude == null)) {
            userLatitude = PropertyManager.getInstance().getLatitude();
            userLongitude = PropertyManager.getInstance().getLongitude();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }

        try {
            view = inflater.inflate(R.layout.fragment_search_result_map, container, false);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_search_result_map_small_map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        callStack = new Stack<>();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == FilterActivity.FILTER_ACTIVITY) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "onActivityResult");

            for (Marker marker : bicycleMarkers.keySet())
                marker.remove();
            bicycleMarkers.clear();

            // TODO : 필터 결과가 적용되려면 changeUserPosition을 더 이상 호출하지 못하도록 막아야 함
            if ((data.getStringExtra("LATITUDE") != null)
                    && (data.getStringExtra("LONGITUDE") != null)) {
                userLatitude = data.getStringExtra("LATITUDE");
                userLongitude = data.getStringExtra("LONGITUDE");
            }

            List<String> f = new ArrayList<>();
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

        requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!callStack.isEmpty())
            for (Call call : callStack)
                if (!call.isCanceled())
                    call.cancel();

        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
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

        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMarkerDragListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(
                bicycleInfoWindowView
                        = BicycleInfoWindowView.getInstance(MyApplication.getmContext(), bicycleMarkers)
        );

        bicycleInfoWindowView.setOnImageLoadListener(
                new BicycleInfoWindowView.OnImageLoadListener() {
                    @Override
                    public void onImageLoad() {
                        bicycleMarker.showInfoWindow();
                    }
                }
        );

        mMarkerOptions = new MarkerOptions();
        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_bicon));
        mMarkerOptions.anchor(0.5f, 1.0f);
        mMarkerOptions.draggable(true);

        bicycleMarkerOptions = new MarkerOptions();
        bicycleMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        bicycleMarkerOptions.anchor(0.5f, 1.0f);
        bicycleMarkerOptions.draggable(false);

        moveMap(
                Double.parseDouble(userLatitude),
                Double.parseDouble(userLongitude),
                false
        );
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        DaumNetworkManager.getInstance().getAddress(
                latLng.longitude,
                latLng.latitude,
                callStack,
                new Callback<GetAddressReceiveObject>() {
                    @Override
                    public void onResponse(Call<GetAddressReceiveObject> call, Response<GetAddressReceiveObject> response) {
                        GetAddressReceiveObject receiveObject = response.body();

                        if (receiveObject != null) {
                            if (onSearchResultMapFragmentListener != null) {
                                onSearchResultMapFragmentListener.setAddress(
                                        receiveObject.getFullName(),
                                        latLng.latitude,
                                        latLng.longitude
                                );
                                onSearchResultMapFragmentListener.setHasLatLng(true);
                            }

                            mLatitude = latLng.latitude;
                            mLongitude = latLng.longitude;

                            mMarkerOptions.position(latLng);
                            if (mMarker != null)
                                mMarker.remove();
                            mMarker = mGoogleMap.addMarker(mMarkerOptions);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAddressReceiveObject> call, Throwable t) {
                        if (call.isCanceled()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "getAddress isCanceled");
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "getAddress onFailure", t);
                        }
                    }
                }
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.isDraggable()) {
            SearchResultItem searchResultItem = bicycleMarkers.get(marker);
            bicycleMarker = marker;
            bicycleInfoWindowView.setImageView(getActivity(), searchResultItem.getImageURL());

            moveMap(
                    searchResultItem.getLatitude(),
                    searchResultItem.getLongitude(),
                    true
            );
        } else {
            mMarker.remove();
            if (onSearchResultMapFragmentListener != null)
                onSearchResultMapFragmentListener.setHasLatLng(false);
        }

        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        final LatLng latLng = marker.getPosition();
        DaumNetworkManager.getInstance().getAddress(
                latLng.longitude,
                latLng.latitude,
                callStack,
                new Callback<GetAddressReceiveObject>() {
                    @Override
                    public void onResponse(Call<GetAddressReceiveObject> call, Response<GetAddressReceiveObject> response) {
                        GetAddressReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "getAddress onResponse Code : " + receiveObject.getCode());

                        if (receiveObject != null) {
                            if (onSearchResultMapFragmentListener != null) {
                                onSearchResultMapFragmentListener.setAddress(
                                        receiveObject.getFullName(),
                                        latLng.latitude,
                                        latLng.longitude
                                );
                                onSearchResultMapFragmentListener.setHasLatLng(true);
                            }

                            mLatitude = latLng.latitude;
                            mLongitude = latLng.longitude;

                            mMarkerOptions.position(latLng);
                            if (mMarker != null)
                                mMarker.remove();
                            mMarker = mGoogleMap.addMarker(mMarkerOptions);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAddressReceiveObject> call, Throwable t) {
                        if (call.isCanceled()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "getAddress isCanceled");
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "getAddress onFailure", t);
                        }
                    }
                }
        );
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", bicycleMarkers.get(marker).getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", bicycleMarkers.get(marker).getLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", bicycleMarkers.get(marker).getLongitude());
        getActivity().startActivity(intent);
    }

    public void changeMarkerPosition(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
        moveMap(mLatitude, mLongitude, true);

        mMarkerOptions.position(new LatLng(mLatitude, mLongitude));
        if (mMarker != null)
            mMarker.remove();
        mMarker = mGoogleMap.addMarker(mMarkerOptions);
    }

    public void removeMarker() {
        if (mMarker != null)
            mMarker.remove();
    }

    public void changeUserPosition(String latitude, String longitude) {
        this.userLatitude = latitude;
        this.userLongitude = longitude;
    }

    private void requestData() {
        String lat = userLatitude;
        String lon = userLongitude;
        NetworkManager.getInstance().selectAllMapBicycle(
                lon,
                lat,
                filter,
                callStack,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "selectAllMapBicycle onResponse"
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Success : " + receiveObject.isSuccess()
                                            + ", Msg : " + receiveObject.getMsg()
                            );
                        List<Result> results = receiveObject.getResult();
                        String imageURL;
                        for (Result result : results) {
                            if ((null == result.getImage().getCdnUri()) || (null == result.getImage().getFiles())) {
                                imageURL = "";
                            } else {
                                imageURL = result.getImage().getCdnUri() + result.getImage().getFiles().get(0);
                            }
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectAllMapBicycle _id : " + result.get_id()
                                                + ", imageURL : " + imageURL
                                                + ", title : " + result.getTitle()
                                                + ", type : " + result.getType()
                                                + ", height : " + result.getHeight()
                                                + ", price.hour : " + result.getPrice().getHour()
                                                + ", price.day : " + result.getPrice().getDay()
                                                + ", price.month : " + result.getPrice().getMonth()
                                                + ", latitude : " + result.getLoc().getCoordinates().get(1)
                                                + ", longitude : " + result.getLoc().getCoordinates().get(0)
                                );

                            SearchResultItem item = new SearchResultItem(
                                    result.get_id(),
                                    imageURL,
                                    result.getTitle(),
                                    result.getHeight(),
                                    result.getType(),
                                    "" + result.getPrice().getMonth(),
                                    result.getDistance(),
                                    result.getLoc().getCoordinates().get(1),
                                    result.getLoc().getCoordinates().get(0)
                            );

                            bicycleMarkerOptions.position(
                                    new LatLng(
                                            result.getLoc().getCoordinates().get(1),
                                            result.getLoc().getCoordinates().get(0)
                                    )
                            );

                            Marker mMarker = mGoogleMap.addMarker(bicycleMarkerOptions);

                            bicycleMarkers.put(mMarker, item);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (call.isCanceled()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectAllMapBicycle isCanceled");
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectAllMapBicycle onFailure", t);
                        }
                    }
                });
    }

    private void moveMap(double lat, double lng, boolean smooth) {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder
                .target(new LatLng(lat, lng))
                .zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        if (mGoogleMap != null) {
            if (smooth)
                mGoogleMap.animateCamera(update);
            else
                mGoogleMap.moveCamera(update);
        }
    }

    public void setOnSearchResultMapFragmentListener(OnSearchResultMapFragmentListener onSearchResultMapFragmentListener) {
        this.onSearchResultMapFragmentListener = onSearchResultMapFragmentListener;
    }
}
