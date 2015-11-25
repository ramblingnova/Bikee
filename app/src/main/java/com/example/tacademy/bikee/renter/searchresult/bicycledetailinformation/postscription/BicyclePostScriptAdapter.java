package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptAdapter extends BaseAdapter {
    List<BicyclePostScriptItem> items = new ArrayList<BicyclePostScriptItem>();

    public void add(String imageURL, String renterName, int point, String postScript, String createAt) {
        BicyclePostScriptItem item = new BicyclePostScriptItem(imageURL, renterName, point, postScript, createAt);
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
        BicyclePostScriptView v;
        if (convertView != null) {
            v = (BicyclePostScriptView)convertView;
        } else {
            v = new BicyclePostScriptView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
