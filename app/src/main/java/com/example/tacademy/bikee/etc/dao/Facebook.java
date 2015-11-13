package com.example.tacademy.bikee.etc.dao;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Facebook {
    public String id = null;
    public String name = null;
    public Picture picture = null;
    public String email = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
