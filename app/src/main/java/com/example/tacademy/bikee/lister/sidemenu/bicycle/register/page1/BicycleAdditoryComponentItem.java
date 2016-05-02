package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page1;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Created by User on 2016-01-07.
 */
public class BicycleAdditoryComponentItem {
    @Getter (AccessLevel.PUBLIC)
    int componentImage;
    @Getter (AccessLevel.PUBLIC)
    String componentText;
    @Getter (AccessLevel.PUBLIC)
    boolean componentCheckBox;

    public BicycleAdditoryComponentItem(int componentImage, String componentText, boolean componentCheckBox) {
        this.componentImage = componentImage;
        this.componentText = componentText;
        this.componentCheckBox = componentCheckBox;
    }
}
