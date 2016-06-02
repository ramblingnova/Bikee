package com.bigtion.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-06-02.
 */
public class FindPasswordSendObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
}
