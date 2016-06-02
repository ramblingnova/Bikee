package com.bigtion.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Facebook {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String access_token = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String phone = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String username = null;
}
