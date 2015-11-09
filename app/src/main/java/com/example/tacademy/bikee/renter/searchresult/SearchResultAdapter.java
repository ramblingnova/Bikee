package com.example.tacademy.bikee.renter.searchresult;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultAdapter extends BaseAdapter {
    List<SearchResultItem> items = new ArrayList<SearchResultItem>();

    public void add(String text1, String text2, String text3) {
        SearchResultItem item = new SearchResultItem(text1, text2, text3);
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
        SearchResultView v;
        if (convertView != null) {
            v = (SearchResultView)convertView;
        } else {
            v = new SearchResultView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
