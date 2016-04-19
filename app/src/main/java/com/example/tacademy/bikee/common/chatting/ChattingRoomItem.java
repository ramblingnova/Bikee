package com.example.tacademy.bikee.common.chatting;

import com.sendbird.android.model.MessagingChannel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-03-11.
 */
public class ChattingRoomItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    MessagingChannel messagingChannel;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reservationState;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;

    public ChattingRoomItem() {

    }

    public ChattingRoomItem(
            MessagingChannel messagingChannel,
            String reservationState,
            String bicycleName,
            String bicycleId
    ) {
        this.messagingChannel = messagingChannel;
        this.reservationState = reservationState;
        this.bicycleName = bicycleName;
        this.bicycleId = bicycleId;
    }
}
