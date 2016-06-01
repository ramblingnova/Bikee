package com.bikee.www.renter.sidemenu.creditcard.register.popup;

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
 * Created by User on 2016-05-12.
 */
public class SelectDateAdapter extends RecyclerView.Adapter<SelectDateViewHolder> {
    private List<String> list;
    private OnAdapterClickListener onAdapterClickListener;

    public SelectDateAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public SelectDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_select_date, parent, false);

        SelectDateViewHolder holder = new SelectDateViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SelectDateViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnViewHolderClickListener(new OnViewHolderClickListener() {
            @Override
            public void onViewHolderClick(View view) {
                onAdapterClickListener.onAdapterClick(view, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(String item) {
        list.add(item);
    }

    public void addAll(List<String> list) {
        this.list = list;
    }

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }
}
