package com.example.tacademy.bikee.common.chatting;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String messageChannelUrl;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String userImage;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reservationState;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String userName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date lastConversationTime;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String lastConversation;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int numOfStackedConversation;

    public ChattingRoomItem(
            String messageChannelUrl,
            String userImage,
            String reservationState,
            String userName,
            Date lastConversationTime,
            String bicycleName,
            String lastConversation,
            int numOfStackedConversation) {
        this.messageChannelUrl = messageChannelUrl;
        this.userImage = userImage;
        this.reservationState = reservationState;
        this.userName = userName;
        this.lastConversationTime = lastConversationTime;
        this.bicycleName = bicycleName;
        this.lastConversation = lastConversation;
        this.numOfStackedConversation = numOfStackedConversation;
    }
}
