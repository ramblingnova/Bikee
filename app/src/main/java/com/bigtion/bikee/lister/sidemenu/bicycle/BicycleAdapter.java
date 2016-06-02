package com.bigtion.bikee.lister.sidemenu.bicycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.common.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.common.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class BicycleAdapter extends RecyclerView.Adapter<BicycleViewHolder> {
    private List<BicycleItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    public BicycleAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public BicycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_owning_bicycle, parent, false);

        BicycleViewHolder holder = new BicycleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BicycleViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnViewHolderClickListener(new OnViewHolderClickListener() {
            @Override
            public void onViewHolderClick(View view) {
                if (onAdapterClickListener != null)
                    onAdapterClickListener.onAdapterClick(view, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }

    public void add(BicycleItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
