package com.example.tacademy.bikee.lister.requestedbicycle;

import java.util.Date;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleItem {
    private String bikeId;
    private String imageURL;
    private String status;
    private String renterName;
    private String bicycleName;
    private Date startDate;
    private Date endDate;
    private String price;
    private String reserveId;

    public ListerRequestedBicycleItem(String bikeId,
                                      String imageURL,
                                      String status,
                                      String renterName,
                                      String bicycleName,
                                      Date startDate,
                                      Date endDate,
                                      String price,
                                      String reserveId) {
        this.bikeId = bikeId;
        this.imageURL = imageURL;
        this.status = status;
        this.renterName = renterName;
        this.bicycleName = bicycleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.reserveId = reserveId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getBicycleName() {
        return bicycleName;
    }

    public void setBicycleName(String bicycleName) {
        this.bicycleName = bicycleName;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }
}
