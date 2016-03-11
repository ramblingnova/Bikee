package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Writer {
    @Getter
    @Setter(AccessLevel.PUBLIC)String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)Image image = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)String email = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)String name = null;
}
