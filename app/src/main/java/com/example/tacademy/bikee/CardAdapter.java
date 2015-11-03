package com.example.tacademy.bikee;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardAdapter extends BaseAdapter {
    List<CardItem> items = new ArrayList<CardItem>();

    public void add(String text1) {
        CardItem item = new CardItem(text1);
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
        CardView v;
        if (convertView != null) {
            v = (CardView)convertView;
        } else {
            v = new CardView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
