package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Facebook {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Picture picture = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
}
