package com.example.tacademy.bikee.lister.sidemenu.bicycle;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class BicycleItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String id;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date date;

    public BicycleItem(String id, String imageURL, String name, Date date) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.date = date;
    }
}
