package com.bigtion.bikee.renter.sidemenu.creditcard.register.popup;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-05-17.
 */
public class SelectDateSpinnerAdapter extends BaseAdapter {
    private List<String> list;

    public SelectDateSpinnerAdapter() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            add("" + i);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = new SelectDateSpinnerItemView(parent.getContext());
        } else {
            view = convertView;
        }
        ((SelectDateSpinnerItemView) view).setView(list.get(position));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = new SelectDateSpinnerItemView(parent.getContext());
        } else {
            view = convertView;
        }
        ((SelectDateSpinnerItemView) view).setView(list.get(position));

        return view;
    }

    public void add(String item) {
        list.add(item);
    }
}
