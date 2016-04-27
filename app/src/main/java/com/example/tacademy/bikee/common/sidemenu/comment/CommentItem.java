package com.example.tacademy.bikee.common.sidemenu.comment;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CommentItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String userImageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String userName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date date;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String comment;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int point;

    public CommentItem(String userImageURL,
                       String userName,
                       Date date,
                       String comment,
                       int point) {
        this.userImageURL = userImageURL;
        this.userName = userName;
        this.bicycleName = null;
        this.date = date;
        this.comment = comment;
        this.point = point;
    }

    public CommentItem(String userImageURL,
                       String userName,
                       String bicycleName,
                       Date date,
                       String comment,
                       int point) {
        this.userImageURL = userImageURL;
        this.userName = userName;
        this.bicycleName = bicycleName;
        this.date = date;
        this.comment = comment;
        this.point = point;
    }
}
