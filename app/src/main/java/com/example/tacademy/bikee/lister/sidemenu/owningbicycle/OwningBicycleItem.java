package com.example.tacademy.bikee.lister.sidemenu.owningbicycle;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tacademy.bikee.etc.dao.Price;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class OwningBicycleItem {
    private String id;
    private String name;
    private String date;

    private String type;
    private String height;
    private Price price;
    private File file1;
    private File file2;

//    File file1 = new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2015-11-01-13-38-15.png");
//    TypedFile typedFile1 = new TypedFile("image/png", file1);
//    File file2 = new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2015-11-01-13-38-17.png");
//    TypedFile typedFile2 = new TypedFile("image/png", file2);
//    Bike bike = new Bike();
//    bike.setTitle("자전거 제목2");
//    Price price = new Price();
//    price.setMonth(10000);
//    price.setDay(1000);
//    price.setHour(200);
//    bike.setPrice(price);
//    NetworkManager.getInstance().insertBicycle(typedFile1, typedFile2, bike, new Callback<ReceiveObject>() {

    public OwningBicycleItem(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public OwningBicycleItem() {

    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }
}
