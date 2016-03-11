package com.example.tacademy.bikee.etc.dao;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Comment {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Integer point = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Writer writer = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createdAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String body = null;
}
