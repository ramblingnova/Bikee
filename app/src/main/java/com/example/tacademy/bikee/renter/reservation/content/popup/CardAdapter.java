package com.example.tacademy.bikee.renter.reservation.content.popup;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-04-27.
 */
public class CardAdapter extends BaseAdapter {
    List<CardItem> list;

    public CardAdapter() {
        list = new ArrayList<>();
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
        MyCardView myCardVIew;
        if (convertView != null) {
            myCardVIew = (MyCardView)convertView;
        } else {
            myCardVIew = new MyCardView(parent.getContext());
        }
        myCardVIew.setView(list.get(position));

        return myCardVIew;
    }

    public void add(CardItem item) {
        list.add(item);
        notifyDataSetChanged();
    }
}
