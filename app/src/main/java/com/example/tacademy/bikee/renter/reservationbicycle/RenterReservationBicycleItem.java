package com.example.tacademy.bikee.renter.reservationbicycle;

import java.util.Date;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleItem {
    private String bicycleId;
    private String imageURL;
    private String bicycleName;
    private String status;
    private Date startDate;
    private Date endDate;
    private int payment;
    private String reserveId;
    private double latitude;
    private double longitude;

    public RenterReservationBicycleItem(String bicycleId, String imageURL, String bicycleName, String status, Date startDate, Date endDate, int payment, String reserveId, double latitude, double longitude) {
        this.bicycleId = bicycleId;
        this.imageURL = imageURL;
        this.bicycleName = bicycleName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
        this.reserveId = reserveId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(String bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBicycleName() {
        return bicycleName;
    }

    public void setBicycleName(String bicycleName) {
        this.bicycleName = bicycleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDaete) {
        this.endDate = endDaete;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
