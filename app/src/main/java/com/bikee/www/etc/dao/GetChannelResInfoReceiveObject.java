package com.bikee.www.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-19.
 */
public class GetChannelResInfoReceiveObject {
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
        Bike bike = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String lister = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Date createdAt = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Reserve reserve;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        int __v = -1;

        public class Reserve {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date rentStart;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date rentEnd;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String renter;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String _id;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date updatedAt;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date createdAt;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String status;
        }
    }
}
