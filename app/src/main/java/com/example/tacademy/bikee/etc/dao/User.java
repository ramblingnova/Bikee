package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class User {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String phone = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String password = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Image image = null;
}
