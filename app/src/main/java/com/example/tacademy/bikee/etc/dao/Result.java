package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Result {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int lastIndex = 0;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Bike bike = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Lister lister = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Renter renter = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<Comment> comments = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date updatedAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createdAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<Reserve> reserve = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    User user = null;
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
    Boolean delflag = null;
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
    Boolean smartlock = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<String> components = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Facebook facebook = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String provider = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String email = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int auth_number = -1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean accepted = false;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double distance = -1.0;
}
