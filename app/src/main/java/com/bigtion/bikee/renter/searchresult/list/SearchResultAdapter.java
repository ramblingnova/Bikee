package com.bigtion.bikee.renter.searchresult.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.etc.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.interfaces.OnViewHolderClickListener;
import com.bigtion.bikee.renter.searchresult.SearchResultItem;
import com.bigtion.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
    private List<SearchResultItem> list;
    private OnAdapterClickListener onAdapterClickListener;

    public SearchResultAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_search_result, parent, false);

        SearchResultViewHolder holder = new SearchResultViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, final int position) {
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

    public void add(SearchResultItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }
}
