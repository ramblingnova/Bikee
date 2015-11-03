package com.example.tacademy.bikee;

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

    public void add(String text1, String text2, String text3, String text4) {
        EvaluatedBicyclePostScriptItem item = new EvaluatedBicyclePostScriptItem(text1, text2, text3, text4);
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
        v.setText(items.get(position));
        return v;
    }
}
