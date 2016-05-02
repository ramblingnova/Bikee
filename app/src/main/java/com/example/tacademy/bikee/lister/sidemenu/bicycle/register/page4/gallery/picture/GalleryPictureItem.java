package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page4.gallery.picture;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-02-24.
 */
public class GalleryPictureItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String picturePath;
    @Getter(AccessLevel.PUBLIC)
    String folderName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean selected;

    public GalleryPictureItem(String picturePath) {
        this.picturePath = picturePath;
        this.folderName = picturePath.split("/")[picturePath.split("/").length - 2];
        this.selected = false;
    }

    public GalleryPictureItem(String picturePath, boolean selected) {
        this.picturePath = picturePath;
        this.folderName = picturePath.split("/")[picturePath.split("/").length - 2];
        this.selected = selected;
    }
}
