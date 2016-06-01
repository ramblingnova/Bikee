package com.bikee.www.etc.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Loc {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String type = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<Double> coordinates = null;
}
