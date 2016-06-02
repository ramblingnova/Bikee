package com.bigtion.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class CardTokenReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int code = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean success = false;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String token = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Err err = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String msg = null;
}
