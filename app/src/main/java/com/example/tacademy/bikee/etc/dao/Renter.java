package com.example.tacademy.bikee.etc.dao;

/**
 * Created by Tacademy on 2015-11-24.
 */
public class Renter {
    private String _id = null;
    private Image image = null;
    private String email = null;
    private String name = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
