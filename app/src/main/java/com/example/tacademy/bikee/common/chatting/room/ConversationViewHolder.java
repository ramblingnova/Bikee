package com.example.tacademy.bikee.common.chatting.room;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.sendbird.android.model.Message;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by User on 2016-03-14.
 */
public class ConversationViewHolder extends RecyclerView.ViewHolder {
    private Map<String, View> map;
    private int viewType;

    public ConversationViewHolder(ViewGroup viewGroup, int layoutId, int viewType) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));

        map = new HashMap<>();
        this.viewType = viewType;
        switch (this.viewType) {
            case ConversationAdapter.RECEIVE:
                map.put("USER_IMAGE", itemView.findViewById(R.id.view_conversation_receive_item_user_image_image_view));
                map.put("RECEIVE_MESSAGE", itemView.findViewById(R.id.view_conversation_receive_item_conversation_text_view));
                map.put("RECEIVE_TIME", itemView.findViewById(R.id.view_conversation_receive_item_conversation_time_text_view));
                break;
            case ConversationAdapter.SEND:
                map.put("SEND_MESSAGE", itemView.findViewById(R.id.view_conversation_send_item_conversation_text_view));
                map.put("SEND_TIME", itemView.findViewById(R.id.view_conversation_send_item_conversation_time_text_view));
                map.put("UNREAD", itemView.findViewById(R.id.view_conversation_send_item_unread_text_view));
                break;
            case ConversationAdapter.DATE:
                map.put("CONVERSATION_DATE", itemView.findViewById(R.id.view_conversation_date_item_conversation_date_text_view));
                break;
        }
    }

    public void setView(ConversationItem item, Hashtable<String, Long> readStatusTable) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("aa HH:mm", java.util.Locale.getDefault());
        switch (viewType) {
            case ConversationAdapter.RECEIVE:
                ImageView userImage = (ImageView) map.get("USER_IMAGE");
                TextView receiveMessage = (TextView) map.get("RECEIVE_MESSAGE");
                TextView receiveMessageTime = (TextView) map.get("RECEIVE_TIME");

                receiveMessage.setText(((Message)item.getMessageModel()).getMessage());

                if (item.isSingleMessage()) {
                    userImage.setVisibility(View.VISIBLE);
                    receiveMessageTime.setVisibility(View.VISIBLE);

                    ImageUtil.setCircleImageFromURL(
                            MyApplication.getmContext(),
                            ((Message) item.getMessageModel()).getSenderImageUrl(),
                            R.drawable.noneimage,
                            userImage
                    );
                    receiveMessageTime.setText(simpleDateFormat.format(item.getConversationTime()));
                } else if (item.getMultiMessageType() == ConversationAdapter.INIT) {
                    userImage.setVisibility(View.VISIBLE);
                    receiveMessageTime.setVisibility(View.GONE);

                    ImageUtil.setCircleImageFromURL(
                            MyApplication.getmContext(),
                            ((Message) item.getMessageModel()).getSenderImageUrl(),
                            R.drawable.noneimage,
                            userImage
                    );
                } else if (item.getMultiMessageType() == ConversationAdapter.MID) {
                    userImage.setVisibility(View.INVISIBLE);
                    receiveMessageTime.setVisibility(View.GONE);
                } else if (item.getMultiMessageType() == ConversationAdapter.FINAL) {
                    userImage.setVisibility(View.INVISIBLE);
                    receiveMessageTime.setVisibility(View.VISIBLE);

                    receiveMessageTime.setText(simpleDateFormat.format(item.getConversationTime()));
                }
                break;
            case ConversationAdapter.SEND:
                TextView sendMessage = (TextView) map.get("SEND_MESSAGE");
                TextView sendMessageTime = (TextView) map.get("SEND_TIME");
                TextView unread = (TextView) map.get("UNREAD");

                sendMessage.setText(((Message) item.getMessageModel()).getMessage());
                if (item.isSingleMessage()
                        || (item.getMultiMessageType() == ConversationAdapter.FINAL)) {
                    sendMessageTime.setVisibility(View.VISIBLE);
                    sendMessageTime.setText(simpleDateFormat.format(item.getConversationTime()));
                } else if ((item.getMultiMessageType() == ConversationAdapter.INIT)
                        || (item.getMultiMessageType() == ConversationAdapter.MID))
                    sendMessageTime.setVisibility(View.GONE);

                int readCount = 0;
                for (String key : readStatusTable.keySet()) {
                    if (key.equals(((Message) item.getMessageModel()).getSenderId())) {
                        readCount += 1;
                        continue;
                    }

                    if (readStatusTable.get(key) >= ((Message) item.getMessageModel()).getTimestamp()) {
                        readCount += 1;
                    }
                }
                if (readCount < readStatusTable.size()) {
                    if (readStatusTable.size() - readCount > 1) {
                        unread.setVisibility(View.VISIBLE);
                    } else {
                        unread.setVisibility(View.VISIBLE);
                    }
                } else {
                    unread.setVisibility(View.GONE);
                }
                break;
            case ConversationAdapter.DATE:
                TextView conversationDate = (TextView) map.get("CONVERSATION_DATE");

                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault());
                conversationDate.setText(simpleDateFormat2.format(item.getConversationTime()));
                break;
        }
    }
}
