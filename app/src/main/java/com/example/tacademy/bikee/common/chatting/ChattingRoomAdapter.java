package com.example.tacademy.bikee.common.chatting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;
import com.sendbird.android.model.MessagingChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomViewHolder> {
    private List<ChattingRoomItem> list;
    private OnChattingRoomAdapterClickListener onChattingRoomAdapterClickListener;

    public ChattingRoomAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ChattingRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chatting_room_item, parent, false);

        ChattingRoomViewHolder chattingRoomViewHolder = new ChattingRoomViewHolder(view);

        return chattingRoomViewHolder;
    }

    @Override
    public void onBindViewHolder(ChattingRoomViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnChattingRoomClickListener(new OnChattingRoomClickListener() {
            @Override
            public void onChattingRoomClick(View view) {
                notifyDataSetChanged();
                if (onChattingRoomAdapterClickListener != null)
                    onChattingRoomAdapterClickListener.onChattingRoomAdapterClick(view, list.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(ChattingRoomItem item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void replace(MessagingChannel messagingChannel) {
        ChattingRoomItem newItem = null;
        for(ChattingRoomItem item : list) {
            if(item.getMessagingChannel().getId() == messagingChannel.getId()) {
                newItem = new ChattingRoomItem(
                        messagingChannel,
                        item.getReservationState(),
                        item.getBicycleName(),
                        item.getBicycleId()
                );

                list.remove(item);
                break;
            }
        }

        list.add(0, newItem);
        notifyDataSetChanged();
    }

    public ChattingRoomItem get(MessagingChannel channel) {
        for(ChattingRoomItem item : list)
            if(item.getMessagingChannel().getId() == channel.getId())
                return item;
        return null;
    }

    public void setOnChattingRoomAdapterClickListener(OnChattingRoomAdapterClickListener onChattingRoomAdapterClickListener) {
        this.onChattingRoomAdapterClickListener = onChattingRoomAdapterClickListener;
    }
}
