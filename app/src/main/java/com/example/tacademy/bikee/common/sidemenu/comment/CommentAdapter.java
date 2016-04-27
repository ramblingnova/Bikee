package com.example.tacademy.bikee.common.sidemenu.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<CommentItem> list;

    public CommentAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_comment, parent, false);

        CommentViewHolder holder = new CommentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.setView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(CommentItem item) {
        list.add(item);
        notifyDataSetChanged();
    }
}
