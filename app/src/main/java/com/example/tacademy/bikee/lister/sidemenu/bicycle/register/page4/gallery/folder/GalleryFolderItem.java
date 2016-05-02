package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page4.gallery.folder;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Created by User on 2016-02-26.
 */
public class GalleryFolderItem {
    @Getter (AccessLevel.PUBLIC)
    String representativeFilePath;
    @Getter (AccessLevel.PUBLIC)
    String folderName;
    @Getter (AccessLevel.PUBLIC)
    int numberOfPicture;

    public GalleryFolderItem(String representativeFilePath) {
        this.representativeFilePath = representativeFilePath;
        this.folderName = representativeFilePath.split("/")[representativeFilePath.split("/").length - 2];
        this.numberOfPicture = 1;
    }

    public void addNumber() {
        this.numberOfPicture++;
    }
}
