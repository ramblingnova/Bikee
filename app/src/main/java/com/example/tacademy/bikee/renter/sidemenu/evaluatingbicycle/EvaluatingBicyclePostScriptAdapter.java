package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptAdapter extends BaseAdapter {
    List<EvaluatingBicyclePostScriptItem> items = new ArrayList<EvaluatingBicyclePostScriptItem>();

    public void add(String imageURL, String name, String date, String desc, int point) {
        EvaluatingBicyclePostScriptItem item = new EvaluatingBicyclePostScriptItem(imageURL, name, date, desc, point);
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
        EvaluatingBicyclePostScriptView v;
        if (convertView != null) {
            v = (EvaluatingBicyclePostScriptView)convertView;
        } else {
            v = new EvaluatingBicyclePostScriptView(parent.getContext());
        }
        v.setView(items.get(position));
        return v;
    }
}
