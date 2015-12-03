package com.example.tacademy.bikee.lister.requestedbicycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleAdapter extends BaseAdapter {
    List<ListerRequestedBicycleItem> items = new ArrayList<ListerRequestedBicycleItem>();

    public void add(String bikeId, String imageURL, String status, String renterName, String bicycleName, Date startDate, Date endDate, String price, String reserveId) {
        ListerRequestedBicycleItem item = new ListerRequestedBicycleItem(bikeId, imageURL, status, renterName, bicycleName, startDate, endDate, price, reserveId);
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
        ListerRequestedBicycleView v;
        if (convertView != null) {
            v = (ListerRequestedBicycleView)convertView;
        } else {
            v = new ListerRequestedBicycleView(parent.getContext());
        }
        v.setView(items.get(position));
        return v;
    }
}
