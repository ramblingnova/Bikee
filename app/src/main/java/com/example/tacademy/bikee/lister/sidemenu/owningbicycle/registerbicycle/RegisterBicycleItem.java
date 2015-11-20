package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import java.io.File;
import java.io.Serializable;

public class RegisterBicycleItem implements Serializable {
    private String type;
    private String height;
    private String name;
    private File file1;
    private File file2;
    private int hour;
    private int Day;
    private int month;
//    TypedFile typedFile2 = new TypedFile("image/png", file2);
//    Bike bike = new Bike();
//    bike.setType("03");
//    bike.setHeight("A");
//    bike.setTitle("자전거 제목2");
//    Price price = new Price();
//    price.setMonth(10000);
//    price.setDay(1000);
//    price.setHour(200);
//    bike.setPrice(price);
//    NetworkManager.getInstance().insertBicycle(typedFile1, typedFile2, bike, new Callback<ReceiveObject>() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
