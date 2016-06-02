package com.bigtion.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class CardSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String card_number;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String expiry;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String birth;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String pwd_2digit;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String card_nick;
}
