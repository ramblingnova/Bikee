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

    public RenterReservationBicycleItem(String bicycleId, String imageURL, String bicycleName, String status, Date startDate, Date endDate, int payment) {
        this.bicycleId = bicycleId;
        this.imageURL = imageURL;
        this.bicycleName = bicycleName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;

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
}
