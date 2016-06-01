package com.bikee.www.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Inquires {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String body = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String title = null;
}
