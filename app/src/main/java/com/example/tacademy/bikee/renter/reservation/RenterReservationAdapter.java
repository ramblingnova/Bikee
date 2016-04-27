package com.example.tacademy.bikee.renter.reservation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationAdapter extends RecyclerView.Adapter<RenterReservationViewHolder> {
    private List<RenterReservationItem> list;
    private OnRenterReservationAdapterClickListener listener;

    private static final String TAG = "RENTER_RESERVATION_ADAPTER";

    public RenterReservationAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public RenterReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_renter_reservation, parent, false);

        RenterReservationViewHolder holder = new RenterReservationViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RenterReservationViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnRenterReservationViewClickListener(new OnRenterReservationViewClickListener() {
            @Override
            public void onRenterReservationClick(View view) {
                listener.onRenterReservationAdapterClick(view, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRenterReservationAdapterClickListener(OnRenterReservationAdapterClickListener listener) {
        this.listener = listener;
    }

    public void add(RenterReservationItem item) {
        list.add(item);
        notifyDataSetChanged();
    }
}
