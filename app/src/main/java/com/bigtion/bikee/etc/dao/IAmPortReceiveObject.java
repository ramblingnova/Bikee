package com.bigtion.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class IAmPortReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String code;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String message;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Response response;

    public class Response {
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String merchant_uid;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        int amount;
    }
}
