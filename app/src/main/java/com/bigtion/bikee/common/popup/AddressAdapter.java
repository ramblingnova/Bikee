package com.bigtion.bikee.common.popup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.interfaces.OnViewHolderClickListener;

import java.util.List;

/**
 * Created by User on 2016-06-03.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private List<AddressItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    public AddressAdapter(List<AddressItem> list) {
        this.list = list;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_address, parent, false);

        AddressViewHolder holder = new AddressViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnViewHolderClickListener(
                new OnViewHolderClickListener() {
                    @Override
                    public void onViewHolderClick(View view) {
                        if (onAdapterClickListener != null)
                            onAdapterClickListener.onAdapterClick(view, list.get(position));
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(AddressItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }
}
