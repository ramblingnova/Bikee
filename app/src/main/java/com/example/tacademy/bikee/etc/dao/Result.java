package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Result {
    public String _id = null;
    public Bike bike = null;
    public String lister = null;
    public List<Comment> comments = null;
    public Date updatedAt = null;
    public Date createdAt = null;
    public List<Reserve> reserve = null;
    public User user = null;
//    public String user = null;
    public String type = null;
    public String height = null;
    public String title = null;
    public Boolean delflag = null;
    public Price price = null;
    public Image image = null;
    public String intro = null;
    public Loc loc = null;
    public Boolean smartlock = null;
    public List<String> components = null;
    public Facebook facebook = null;
    public String provider = null;
    public String email = null;
    public String name = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

//    public Lister getLister() {
//        return lister;
//    }
//
//    public void setLister(Lister lister) {
//        this.lister = lister;
//    }

    public String getLister() {
        return lister;
    }

    public void setLister(String lister) {
        this.lister = lister;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Reserve> getReserve() {
        return reserve;
    }

    public void setReserve(List<Reserve> reserve) {
        this.reserve = reserve;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public Boolean getSmartlock() {
        return smartlock;
    }

    public void setSmartlock(Boolean smartlock) {
        this.smartlock = smartlock;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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
