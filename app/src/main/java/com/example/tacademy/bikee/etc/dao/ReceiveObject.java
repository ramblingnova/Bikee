package com.example.tacademy.bikee.etc.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class ReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int code = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean success = false;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<Result> result = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Err err = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String msg = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int lastindex = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<String> stack = null;
}
