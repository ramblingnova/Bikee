package com.example.tacademy.bikee.renter.searchresult.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tacademy.bikee.renter.searchresult.SearchResultListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultListAdapter extends BaseAdapter {
    List<SearchResultListItem> items;

    public SearchResultListAdapter() {
        items = new ArrayList<>();
    }

    public void add(String bicycleID, String imageURL, String bicycle_name, String payment, String type, String height, String distance, double latitude, double longitude) {
        SearchResultListItem item = new SearchResultListItem(bicycleID, imageURL, bicycle_name, payment, type, height, distance, latitude, longitude);
        items.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
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
        SearchResultView v;
        if (convertView != null) {
            v = (SearchResultView)convertView;
        } else {
            v = new SearchResultView(parent.getContext());
        }
        v.setView(items.get(position));
        return v;
    }
}
