package com.bikee.www.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class IAmPortSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String merchant_uid = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int amount = -1;
}
