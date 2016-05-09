package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-05-09.
 */
public class SelectReservationReceiveObject {
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
        Lister lister = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Bike bike = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Date createdAt = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        Reserve reserve = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        int __v = -1;

        public class Reserve {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date rentStart = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date rentEnd = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Renter renter = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String _id = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date updatedAt = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date createdAt = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String status = null;

            public class Renter {
                @Getter
                @Setter(AccessLevel.PUBLIC)
                String _id = null;
                @Getter
                @Setter(AccessLevel.PUBLIC)
                String phone = null;
                @Getter
                @Setter(AccessLevel.PUBLIC)
                Image image = null;
                @Getter
                @Setter(AccessLevel.PUBLIC)
                String email = null;
                @Getter
                @Setter(AccessLevel.PUBLIC)
                String name = null;
            }
        }

        public class Lister {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String _id = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String phone = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Image image = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String email = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String name = null;
        }

        public class Bike {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String _id = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String user = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String height = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String title = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String type = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String lockdeviceId = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            int __v = -1;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date updatedAt = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            Date createdAt = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            boolean active = false;
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
            boolean smartlock = false;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            List<String> components = null;
        }
    }
}