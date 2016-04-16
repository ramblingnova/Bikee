package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-14.
 */
public class SendbirdReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Bike bike;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String lister;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Reserve reserve;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String __v;
}
