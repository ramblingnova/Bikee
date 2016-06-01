package com.bikee.www.lister.reservation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikee.www.common.interfaces.OnAdapterClickListener;
import com.bikee.www.common.interfaces.OnViewHolderClickListener;
import com.bikee.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerReservationAdapter extends RecyclerView.Adapter<ListerReservationViewHolder> {
    private List<ListerReservationItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    public ListerReservationAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ListerReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_lister_reservation, parent, false);

        ListerReservationViewHolder holder = new ListerReservationViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ListerReservationViewHolder holder, final int position) {
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

    public void add(ListerReservationItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void addFirst(ListerReservationItem item) {
        list.add(0, item);
        notifyDataSetChanged();
    }
}
