package com.bikee.www.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Bike {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String user = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String type = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String height = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String title = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date updatedAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createdAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Boolean active = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Price price = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Image image = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String intro = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Loc loc = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Boolean smartlock = false;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<String> components = null;
}
