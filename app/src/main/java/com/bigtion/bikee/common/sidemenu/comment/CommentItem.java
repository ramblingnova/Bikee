package com.bigtion.bikee.common.sidemenu.comment;

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
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String title1;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String title2;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date date;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String comment;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int point;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int type;

    public CommentItem(String imageURL,
                       String title1,
                       Date date,
                       String comment,
                       int point,
                       int type) {
        this.imageURL = imageURL;
        this.title1 = title1;
        this.title2 = null;
        this.date = date;
        this.comment = comment;
        this.point = point;
        this.type = type;
    }

    public CommentItem(String imageURL,
                       String title1,
                       String title2,
                       Date date,
                       String comment,
                       int point,
                       int type) {
        this.imageURL = imageURL;
        this.title1 = title1;
        this.title2 = title2;
        this.date = date;
        this.comment = comment;
        this.point = point;
        this.type = type;
    }
}
