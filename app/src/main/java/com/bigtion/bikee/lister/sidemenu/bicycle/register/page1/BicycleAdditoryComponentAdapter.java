package com.bigtion.bikee.lister.sidemenu.bicycle.register.page1;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-01-07.
 */
public class BicycleAdditoryComponentAdapter extends BaseAdapter {
    private List<BicycleAdditoryComponentItem> items;
    private OnItemClickListener onItemClickListener;

    public BicycleAdditoryComponentAdapter() {
        items = new ArrayList<>();
    }

    public void add(int componentImage, String componentText, boolean componentCheckBox) {
        BicycleAdditoryComponentItem item = new BicycleAdditoryComponentItem(componentImage, componentText, componentCheckBox);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        BicycleAdditoryComponentView view;
        if (convertView != null) {
            view = (BicycleAdditoryComponentView)convertView;
        } else {
            view = new BicycleAdditoryComponentView(parent.getContext());
        }
        view.setView(items.get(position));
        view.setOnItemClickListener(new BicycleAdditoryComponentView.OnItemClickListener() {
            @Override
            public void onItemClick() {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position);
            }
        });

        return view;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
