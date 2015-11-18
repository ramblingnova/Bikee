package com.example.tacademy.bikee.renter.searchresult.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.POI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Created by Tacademy on 2015-11-18.
 */
public class BicycleInfoWindowView implements GoogleMap.InfoWindowAdapter {
    View infoWindow;
    TextView titleView, snippetView;

    public BicycleInfoWindowView(Context context) {
        infoWindow = LayoutInflater.from(context).inflate(R.layout.view_bicycle_info_window, null);
        titleView = (TextView)infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_name_text_view);
        snippetView = (TextView)infoWindow.findViewById(R.id.view_bicycle_info_window_bicycle_type_real_text_view);
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
