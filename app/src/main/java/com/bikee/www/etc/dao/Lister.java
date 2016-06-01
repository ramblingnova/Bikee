package com.bikee.www.etc.dao;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Lister {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date updatedAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createdAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String authToken = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String salt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String hashed_password = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String provider = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Image image = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
}
