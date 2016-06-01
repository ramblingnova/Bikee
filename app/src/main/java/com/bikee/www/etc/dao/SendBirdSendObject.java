package com.bikee.www.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-14.
 */
public class SendBirdSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String channel_url = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String renter = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String lister = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bike = null;
}
