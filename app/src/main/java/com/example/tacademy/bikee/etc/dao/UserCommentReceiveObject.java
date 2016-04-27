package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-28.
 */
public class UserCommentReceiveObject {
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
    Object err = null;

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
        @Getter
        @Setter(AccessLevel.PUBLIC)
        List<Comment> comments = null;

        public class Comment {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            int point = -1;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String writer = null;
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
    }
}
