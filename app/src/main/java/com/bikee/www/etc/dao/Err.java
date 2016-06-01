package com.bikee.www.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2015-11-30.
 */
public class Err {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String stack = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String message = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
}
