package com.example.tacademy.bikee.renter.reservationbicycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleAdapter extends BaseAdapter {
    List<RenterReservationBicycleItem> items = new ArrayList<RenterReservationBicycleItem>();

    public void add(String bicycleId, String imageURL, String bicycleName, String status, Date startDate, Date endDate, int payment) {
        RenterReservationBicycleItem item = new RenterReservationBicycleItem(bicycleId, imageURL, bicycleName, status, startDate, endDate, payment);
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RenterReservationBicycleView v;
        if (convertView != null) {
            v = (RenterReservationBicycleView) convertView;
        } else {
            v = new RenterReservationBicycleView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
