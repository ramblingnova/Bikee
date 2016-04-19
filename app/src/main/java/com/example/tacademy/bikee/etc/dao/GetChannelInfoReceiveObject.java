package com.example.tacademy.bikee.etc.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-14.
 */
public class GetChannelInfoReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int code = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean success = false;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<SendBirdResult> result = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String msg = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Err err = null;

    public class SendBirdResult {
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String _id = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String bike = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String channel_url = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String lister = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String renter = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        int __v = -1;
    }
}
