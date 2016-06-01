package com.bikee.www.common.chatting.room;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bikee.www.common.chatting.room.inspectors.DayInspector;
import com.bikee.www.common.chatting.room.inspectors.DiscreteMessageInspector;
import com.bikee.www.common.chatting.room.inspectors.HourInspector;
import com.bikee.www.common.chatting.room.inspectors.InspectorItem;
import com.bikee.www.common.chatting.room.inspectors.MinuteInspector;
import com.bikee.www.common.chatting.room.inspectors.MonthInspector;
import com.bikee.www.common.chatting.room.inspectors.YearInspector;
import com.bikee.www.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by User on 2016-03-14.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationViewHolder> {
    private List<ConversationItem> mItemList;
    private Hashtable<String, Long> mReadStatusTable;
    private long mMaxMessageTimestamp = Long.MIN_VALUE;
    private long mMinMessageTimestamp = Long.MAX_VALUE;

    public static final int RECEIVE = 1;
    public static final int SEND = 2;
    public static final int DATE = 3;
    public static final int INIT = 4;
    public static final int MID = 5;
    public static final int FINAL = 6;
    private static final String TAG = "CONVERSATION_ADAPTER";

    public ConversationAdapter() {
        mItemList = new ArrayList<>();
        mReadStatusTable = new Hashtable<>();
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
        holder.setView(mItemList.get(position), mReadStatusTable);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void add(ConversationItem item) {
        if (mItemList.size() == 0) {
            mItemList.add(0, item);
            item.setMultiMessageType(INIT);
        } else if (item.getConversationTime().getTime() < getMinMessageTimestamp()) {
            mItemList.add(0, item);
            item.setMultiMessageType(INIT);

            if (mItemList.size() > 1) {
                int nextPosition = 1;
                Date nextConversationTime = mItemList.get(nextPosition).getConversationTime();
                int nextType = mItemList.get(nextPosition).getMessageType();

                DiscreteMessageInspector inspector = new YearInspector()
                        .setNext(new MonthInspector())
                        .setNext(new DayInspector());
                boolean discrete = inspector.isDiscreteMessage(
                        new InspectorItem(
                                item.getConversationTime(),
                                nextConversationTime
                        )
                );

                if (discrete && (nextType != DATE))
                    mItemList.add(1, new ConversationItem(null, nextConversationTime, DATE));
                else if (item.getMessageType() == nextType) {
                    inspector = new HourInspector()
                            .setNext(new MinuteInspector());
                    discrete = inspector.isDiscreteMessage(
                            new InspectorItem(
                                    item.getConversationTime(),
                                    mItemList.get(nextPosition).getConversationTime()
                            )
                    );

                    if (!discrete) {
                        mItemList.get(nextPosition).setSingleMessage(false);
                        mItemList.get(nextPosition).setMultiMessageType(FINAL);
                        item.setSingleMessage(false);

                        if ((mItemList.size() > 2)
                                && (mItemList.get(nextPosition + 1).getMultiMessageType() != INIT))
                            mItemList.get(nextPosition).setMultiMessageType(MID);
                    }
                }
            }
        } else if (item.getConversationTime().getTime() > getMaxMessageTimestamp()) {
            mItemList.add(item);
            item.setMultiMessageType(INIT);

            if (mItemList.size() > 1) {
                int prevPosition = mItemList.size() - 2;
                Date prevConversationTime = mItemList.get(prevPosition).getConversationTime();
                int prevType = mItemList.get(prevPosition).getMessageType();

                DiscreteMessageInspector inspector = new YearInspector()
                        .setNext(new MonthInspector())
                        .setNext(new DayInspector());
                boolean discrete = inspector.isDiscreteMessage(
                        new InspectorItem(
                                item.getConversationTime(),
                                prevConversationTime
                        )
                );

                if (discrete && (prevType != DATE))
                    mItemList.add(prevPosition + 1, new ConversationItem(null, prevConversationTime, DATE));
                else if (item.getMessageType() == prevType) {
                    inspector = new HourInspector()
                            .setNext(new MinuteInspector());
                    discrete = inspector.isDiscreteMessage(
                            new InspectorItem(
                                    item.getConversationTime(),
                                    prevConversationTime
                            )
                    );

                    if (!discrete) {
                        mItemList.get(prevPosition).setSingleMessage(false);
                        item.setSingleMessage(false);
                        item.setMultiMessageType(FINAL);

                        if (mItemList.get(prevPosition).getMultiMessageType() == FINAL)
                            mItemList.get(prevPosition).setMultiMessageType(MID);
                    }
                }
            }
        }
        updateMessageTimestamp(item.getConversationTime().getTime());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ConversationItem conversationItem = mItemList.get(position);
        return conversationItem.getMessageType();
    }

    public List<ConversationItem> getItemList() {
        return mItemList;
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

        mItemList.clear();
        mReadStatusTable.clear();
    }

    public void resetReadStatusTable(Hashtable<String, Long> readStatusTable) {
        mReadStatusTable = readStatusTable;
    }

    public Hashtable<String, Long> getReadStatusTable() {
        return mReadStatusTable;
    }

    public void setReadStatus(String userId, long timeStamp) {
        if ((mReadStatusTable.get(userId) == null)
                || (mReadStatusTable.get(userId) < timeStamp))
            mReadStatusTable.put(userId, timeStamp);
    }
}
