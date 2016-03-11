package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Price {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int month;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int day ;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int hour;
}
