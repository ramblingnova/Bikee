package com.example.tacademy.bikee.common.chatting.room;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-03-14.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationViewHolder> {
    private List<ConversationItem> list;
    public static final int RECEIVE = 1;
    public static final int SEND = 2;
    public static final int DATE = 3;
    public static final int INIT = 4;
    public static final int MID = 5;
    public static final int FINAL = 6;

    public ConversationAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConversationViewHolder conversationViewHolder = null;
        switch (viewType) {
            case RECEIVE:
                conversationViewHolder = new ConversationViewHolder(parent, R.layout.view_conversation_receive_item, RECEIVE);
                break;
            case SEND:
                conversationViewHolder = new ConversationViewHolder(parent, R.layout.view_conversation_send_item, SEND);
                break;
            case DATE:
                conversationViewHolder = new ConversationViewHolder(parent, R.layout.view_conversation_date_item, DATE);
                break;
        }
        return conversationViewHolder;
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        holder.setView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(ConversationItem item) {
        list.add(item);
        item.setInnerType(INIT);
        int position = list.size() - 1;
        if (position > 0) {
            int currentType = item.getType();
            int beforeType = list.get(position - 1).getType();

            if (currentType == beforeType) {
                list.get(position - 1).setSingle(false);
                item.setSingle(false);
                item.setInnerType(FINAL);
                if (list.get(position - 1).getInnerType() == FINAL)
                    list.get(position - 1).setInnerType(MID);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ConversationItem conversationItem = list.get(position);
        return conversationItem.getType();
    }

    public List<ConversationItem> getList() {
        return list;
    }
}
