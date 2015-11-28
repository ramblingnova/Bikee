package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardAdapter extends BaseAdapter {

    List<CardItem> items = new ArrayList<CardItem>();
    public OnItemClickListener onItemClickListener;

    public void add(String card_type, String card_number, String expiration_date, String birth_date, String password) {
        CardItem item = new CardItem(card_type, card_number, expiration_date, birth_date, password);
        items.add(item);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        items.remove(location);
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
        final Context context = parent.getContext();
        CardView cardView;
        if (convertView == null) {
            cardView = new CardView(context);
        } else {
            cardView = (CardView) convertView;
        }
        cardView.setView(items.get(position));
        cardView.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
                Toast.makeText(context, "index:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cardView index:" + position, Toast.LENGTH_SHORT).show();
                if(onItemClickListener!=null)
                    onItemClickListener.onItemClick(items.get(position));
            }
        });
        return cardView;
    }

    public interface OnItemClickListener {
        void onItemClick(CardItem item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
