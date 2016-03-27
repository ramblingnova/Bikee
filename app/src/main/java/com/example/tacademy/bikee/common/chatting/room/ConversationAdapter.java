package com.example.tacademy.bikee.common.chatting.room;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.chatting.room.inspectors.DayInspector;
import com.example.tacademy.bikee.common.chatting.room.inspectors.DiscreteMessageInspector;
import com.example.tacademy.bikee.common.chatting.room.inspectors.HourInspector;
import com.example.tacademy.bikee.common.chatting.room.inspectors.InspectorItem;
import com.example.tacademy.bikee.common.chatting.room.inspectors.MinuteInspector;
import com.example.tacademy.bikee.common.chatting.room.inspectors.MonthInspector;
import com.example.tacademy.bikee.common.chatting.room.inspectors.YearInspector;

import java.util.ArrayList;
import java.util.Date;
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
    private long mMaxMessageTimestamp = Long.MIN_VALUE;
    private long mMinMessageTimestamp = Long.MAX_VALUE;

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
        if (item.isPast()) {
            list.add(0, item);
            item.setInnerType(INIT);
            if (list.size() > 1) {
                int position = 0;
                int currentType = item.getType();
                int afterType = list.get(position + 1).getType();

                DiscreteMessageInspector inspector = new YearInspector()
                        .setNext(new MonthInspector())
                        .setNext(new DayInspector());
                boolean discrete = inspector.isDiscreteMessage(
                        new InspectorItem(
                                item.getConversationTime(),
                                list.get(position + 1).getConversationTime()
                        )
                );
                if (discrete) {
                    if (afterType != DATE) {
                        Date afterConversationTime = list.get(position + 1).getConversationTime();
                        list.add(1, new ConversationItem(null, null, afterConversationTime, DATE));
                    }
                } else if (currentType == afterType) {
                    inspector = new YearInspector()
                            .setNext(new MonthInspector())
                            .setNext(new DayInspector())
                            .setNext(new HourInspector())
                            .setNext(new MinuteInspector());
                    discrete = inspector.isDiscreteMessage(
                            new InspectorItem(
                                    item.getConversationTime(),
                                    list.get(position + 1).getConversationTime()
                            )
                    );

                    if (discrete) {
                        item.setSingle(true);
                    } else {
                        list.get(position + 1).setSingle(false);
                        list.get(position + 1).setInnerType(FINAL);
                        item.setSingle(false);
                        if ((list.size() > 2) && (list.get(position + 2).getInnerType() != INIT))
                            list.get(position + 1).setInnerType(MID);
                    }
                }
            }
        } else {
            list.add(item);
            item.setInnerType(INIT);
            if (list.size() > 1) {
                int position = list.size() - 1;
                int currentType = item.getType();
                int beforeType = list.get(position - 1).getType();

                DiscreteMessageInspector inspector = new YearInspector()
                        .setNext(new MonthInspector())
                        .setNext(new DayInspector());
                boolean discrete = inspector.isDiscreteMessage(
                        new InspectorItem(
                                item.getConversationTime(),
                                list.get(position - 1).getConversationTime()
                        )
                );

                if (discrete) {
                    if (beforeType != DATE) {
                        Date beforeConversationTime = list.get(position - 1).getConversationTime();
                        list.add(position, new ConversationItem(null, null, beforeConversationTime, DATE));
                    }
                } else if (currentType == beforeType) {
                    inspector = new YearInspector()
                            .setNext(new MonthInspector())
                            .setNext(new DayInspector())
                            .setNext(new HourInspector())
                            .setNext(new MinuteInspector());
                    discrete = inspector.isDiscreteMessage(
                            new InspectorItem(
                                    item.getConversationTime(),
                                    list.get(position - 1).getConversationTime()
                            )
                    );

                    if (discrete) {
                        item.setSingle(true);
                    } else {
                        list.get(position - 1).setSingle(false);
                        item.setSingle(false);
                        item.setInnerType(FINAL);
                        if (list.get(position - 1).getInnerType() == FINAL)
                            list.get(position - 1).setInnerType(MID);
                    }
                }
            }
        }

        updateMessageTimestamp(item.getConversationTime().getTime());
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

    private void updateMessageTimestamp(long timestamp) {
        mMaxMessageTimestamp = mMaxMessageTimestamp < timestamp ? timestamp : mMaxMessageTimestamp;
        mMinMessageTimestamp = mMinMessageTimestamp > timestamp ? timestamp : mMinMessageTimestamp;
    }

    public long getMaxMessageTimestamp() {
        return mMaxMessageTimestamp == Long.MIN_VALUE ? Long.MAX_VALUE : mMaxMessageTimestamp;
    }

    public long getMinMessageTimestamp() {
        return mMinMessageTimestamp == Long.MAX_VALUE ? Long.MIN_VALUE : mMinMessageTimestamp;
    }

    public void clear() {
        mMaxMessageTimestamp = Long.MIN_VALUE;
        mMinMessageTimestamp = Long.MAX_VALUE;

        list.clear();
    }
}
