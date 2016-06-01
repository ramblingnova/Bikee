package com.bikee.www.common.content.popup;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-04-27.
 */
public class CardAdapter extends BaseAdapter {
    private List<CardItem> list;
    private OnCardAdapterCheckedChangeListener onCardAdapterCheckedChangeListener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyCardView myCardVIew;
        if (convertView != null) {
            myCardVIew = (MyCardView)convertView;
        } else {
            myCardVIew = new MyCardView(parent.getContext());
        }
        myCardVIew.setView(list.get(position));
        myCardVIew.setOnMyCardViewCheckedChangeListener(new OnMyCardViewCheckedChangeListener() {
            @Override
            public void onMyCardViewCheckedChange(CompoundButton buttonView) {
                onCardAdapterCheckedChangeListener.onCardAdapterCheckedChanged(list.get(position));
            }
        });

        return myCardVIew;
    }

    public void add(CardItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void setOnCardAdapterCheckedChangeListener(OnCardAdapterCheckedChangeListener onCardAdapterCheckedChangeListener) {
        this.onCardAdapterCheckedChangeListener = onCardAdapterCheckedChangeListener;
    }
}
