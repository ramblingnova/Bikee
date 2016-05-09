package com.example.tacademy.bikee.common.content.postscription;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptAdapter extends RecyclerView.Adapter<BicyclePostScriptView> {
    private List<BicyclePostScriptItem> list;

    public BicyclePostScriptAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public BicyclePostScriptView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_bicycle_post_script, parent, false);

        BicyclePostScriptView holder = new BicyclePostScriptView(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BicyclePostScriptView holder, int position) {
        holder.setView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(BicyclePostScriptItem item) {
        list.add(item);
        notifyDataSetChanged();
    }
}
