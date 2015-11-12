package com.example.tacademy.bikee.renter.sidemenu.evaluatingbicycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class EvaluatingBicyclePostScriptAdapter extends BaseAdapter {
    List<EvaluatingBicyclePostScriptItem> items = new ArrayList<EvaluatingBicyclePostScriptItem>();

    public void add(String text1, String text2, int point, String text3, String text4) {
        EvaluatingBicyclePostScriptItem item = new EvaluatingBicyclePostScriptItem(text1, text2, point, text3, text4);
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
        v.EvaluatingBicyclePostScriptView(items.get(position));
        return v;
    }


}
