package com.example.tacademy.bikee.common.content.postscription;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String renterName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int point;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String postScript;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date createAt;

    public BicyclePostScriptItem(String imageURL, String renterName, int point, String postScript, Date createAt) {
        this.imageURL = imageURL;
        this.renterName = renterName;
        this.point = point;
        this.postScript = postScript;
        this.createAt = createAt;
    }
}
