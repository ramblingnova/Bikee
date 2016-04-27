package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class PaymentSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String merchant_uid = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int amount = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String lister = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bike = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String buyer_name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String buyer_email = null;
}
