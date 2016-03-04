package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page1;

import android.graphics.drawable.Drawable;

/**
 * Created by User on 2016-01-07.
 */
public class BicycleAdditoryComponentItem {
    private int componentImage;
    private String componentText;
    private boolean componentCheckBox;

    public BicycleAdditoryComponentItem(int componentImage, String componentText, boolean componentCheckBox) {
        this.componentImage = componentImage;
        this.componentText = componentText;
        this.componentCheckBox = componentCheckBox;
    }

    public int getComponentImage() {
        return componentImage;
    }

    public void setComponentImage(int componentImage) {
        this.componentImage = componentImage;
    }

    public String getComponentText() {
        return componentText;
    }

    public void setComponentText(String componentText) {
        this.componentText = componentText;
    }

    public boolean isComponentCheckBox() {
        return componentCheckBox;
    }

    public void setComponentCheckBox(boolean componentCheckBox) {
        this.componentCheckBox = componentCheckBox;
    }
}
