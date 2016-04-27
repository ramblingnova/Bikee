package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-25.
 */
public class CardResult {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String card_name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String card_nick = null;
}
