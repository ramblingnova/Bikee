package com.bigtion.bikee.common.chatting;

import com.sendbird.android.model.MessagingChannel;

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
    MessagingChannel messagingChannel;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date rentStart;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date rentEnd;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reservationState;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean amILister;

    public ChattingRoomItem() {

    }

    public ChattingRoomItem(
            MessagingChannel messagingChannel,
            Date rentStart,
            Date rentEnd,
            String reservationState,
            String bicycleName,
            String bicycleId,
            boolean amILister
    ) {
        this.messagingChannel = messagingChannel;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.reservationState = reservationState;
        this.bicycleName = bicycleName;
        this.bicycleId = bicycleId;
        this.amILister = amILister;
    }
}
