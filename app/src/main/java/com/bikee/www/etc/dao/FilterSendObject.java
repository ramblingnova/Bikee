package com.bikee.www.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-22.
 */
public class FilterSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String start = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String end = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String type = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String height;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean  smartlock;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String sort;
}
