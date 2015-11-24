package com.example.tacademy.bikee.renter.reservationbicycle;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleItem {
    private String imageURL;
    private String bicycleName;
    private String status;
    private String startDate;
    private String endDate;
    private int payment;

    public RenterReservationBicycleItem(String imageURL, String bicycleName, String status, String startDate, String endDate, int payment) {
        this.imageURL = imageURL;
        this.bicycleName = bicycleName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
