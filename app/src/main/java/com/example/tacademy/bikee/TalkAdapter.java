package com.example.tacademy.bikee;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class TalkAdapter extends BaseAdapter {
    List<SendTalkItem> items = new ArrayList<SendTalkItem>();

    public void add(String text1, String text2, String text3) {
        SendTalkItem item = new SendTalkItem(text1, text2);
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
        SendTalkView v;
        if (convertView != null) {
            v = (SendTalkView)convertView;
        } else {
            v = new SendTalkView(parent.getContext());
        }
        v.setText(items.get(position));
        return v;
    }
}
