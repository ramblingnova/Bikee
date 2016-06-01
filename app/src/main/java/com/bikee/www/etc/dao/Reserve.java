package com.bikee.www.etc.dao;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Reserve {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date rentStart = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date rentEnd = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Renter renter = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date updatedAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createdAt = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String status = null;
}
