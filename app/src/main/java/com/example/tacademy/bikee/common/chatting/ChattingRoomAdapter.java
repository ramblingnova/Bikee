package com.example.tacademy.bikee.common.chatting;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2015-10-31.
 */
public class ChattingRoomAdapter extends BaseAdapter {
    List<ChattingRoomItem> items = new ArrayList<ChattingRoomItem>();

    public void add(String text1, String text2, String text3) {
        ChattingRoomItem item = new ChattingRoomItem(text1, text2, text3);
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
        ChattingRoomView v;
        if (convertView != null) {
            v = (ChattingRoomView)convertView;
        } else {
            v = new ChattingRoomView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
