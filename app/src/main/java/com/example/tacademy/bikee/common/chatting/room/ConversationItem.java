package com.example.tacademy.bikee.common.chatting.room;

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
    String userImage;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String conversation;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date conversationTime;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int type;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean single;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int innerType;

    public ConversationItem(String userImage, String conversation, Date conversationTime, int type) {
        this.userImage = userImage;
        this.conversation = conversation;
        this.conversationTime = conversationTime;
        this.type = type;
        this.single = true;
    }

    public boolean isPast() {
        return conversationTime.getTime() < new Date().getTime();
    }
}
