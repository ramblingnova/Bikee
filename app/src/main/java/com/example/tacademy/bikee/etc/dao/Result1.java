package com.example.tacademy.bikee.etc.dao;

import java.util.Date;
import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Result1 {
    private int lastIndex = 0;
    private String _id = null;
    private Bike bike = null;
    private Lister lister = null;
    private Renter renter = null;
    private List<Comment> comments = null;
    private Date updatedAt = null;
    private Date createdAt = null;
    private List<Reserve1> reserve = null;
    private User user = null;
    private String type = null;
    private String height = null;
    private String title = null;
    private Boolean delflag = null;
    private Price price = null;
    private Image image = null;
    private String intro = null;
    private Loc loc = null;
    private Boolean smartlock = null;
    private List<String> components = null;
    private Facebook facebook = null;
    private String provider = null;
    private String email = null;
    private String name = null;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

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

    public Lister getLister() {
        return lister;
    }

    public void setLister(Lister lister) {
        this.lister = lister;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
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

    public List<Reserve1> getReserve() {
        return reserve;
    }

    public void setReserve(List<Reserve1> reserve) {
        this.reserve = reserve;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
