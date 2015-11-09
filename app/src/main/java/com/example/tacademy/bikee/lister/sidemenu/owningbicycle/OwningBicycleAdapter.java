package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleAdapter extends BaseAdapter {
    List<OwningBicycleItem> items = new ArrayList<OwningBicycleItem>();

    public void add(String text1, String text2) {
        OwningBicycleItem item = new OwningBicycleItem(text1, text2);
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
        OwningBicycleView v;
        if (convertView != null) {
            v = (OwningBicycleView)convertView;
        } else {
            v = new OwningBicycleView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }

}
