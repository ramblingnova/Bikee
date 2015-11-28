package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class EvaluatedBicyclePostScriptAdapter extends BaseAdapter {
    List<EvaluatedBicyclePostScriptItem> items = new ArrayList<EvaluatedBicyclePostScriptItem>();

    public void add(String imageURL, String renterName, String bicycleName, String createDate, String description, int point) {
        EvaluatedBicyclePostScriptItem item = new EvaluatedBicyclePostScriptItem(imageURL, renterName, bicycleName, createDate, description, point);
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
        EvaluatedBicyclePostScriptView v;
        if (convertView != null) {
            v = (EvaluatedBicyclePostScriptView)convertView;
        } else {
            v = new EvaluatedBicyclePostScriptView(parent.getContext());
        }
        v.setView(items.get(position));
        return v;
    }
}
