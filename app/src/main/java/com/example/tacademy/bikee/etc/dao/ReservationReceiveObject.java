package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class ReservationReceiveObject {
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

    public class Result {
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String _id = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Bike bike = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Lister lister = null;
        /*@Getter
        @Setter(AccessLevel.PUBLIC)
        Renter renter = null;*/
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Reserve reserve = null;
        /*@Getter
        @Setter(AccessLevel.PUBLIC)
        double distance = -1.0;*/

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
            String renter = null;
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
    }
}
