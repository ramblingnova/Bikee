package com.example.tacademy.bikee.renter.searchresult;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class SearchResultItem {
    public SearchResultItem(String bicycle_name, String height, String type, String payment, String distance, double latitude, double longitude) {
        this.bicycle_name = bicycle_name;
        this.height = height;
        this.type = type;
        this.payment = payment;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private String bicycle_name;
    private String height;
    private String type;
    private String payment;
    private String distance;
    private double latitude;
    private double longitude;

    public String getBicycle_name() {
        return bicycle_name;
    }

    public void setBicycle_name(String bicycle_name) {
        this.bicycle_name = bicycle_name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
