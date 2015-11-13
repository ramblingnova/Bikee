package com.example.tacademy.bikee.etc.dao;

import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Image {
    public String cdnUri = null;
    public List<String> files = null;

    public String getCdnUri() {
        return cdnUri;
    }

    public void setCdnUri(String cdnUri) {
        this.cdnUri = cdnUri;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
