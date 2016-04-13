package com.example.tacademy.bikee.common.chatting.room;

import com.sendbird.android.model.MessageModel;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-03-14.
 */
public class ConversationItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    MessageModel messageModel;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date conversationTime;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int messageType;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean singleMessage;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int multiMessageType;

    public ConversationItem(MessageModel messageModel, Date conversationTime, int messageType) {
        this.messageModel = messageModel;
        this.conversationTime = conversationTime;
        this.messageType = messageType;
        this.singleMessage = true;
    }
}
