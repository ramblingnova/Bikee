package com.example.tacademy.bikee.lister.requestedbicycle;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerRequestedBicycleItem {
    private String imageURL;
    private String status;
    private String renterName;
    private String bicycleName;
    private String startDate;
    private String endDate;
    private String price;

    public ListerRequestedBicycleItem(String imageURL, String status, String renterName, String bicycleName, String startDate, String endDate, String price) {
        this.imageURL = imageURL;
        this.status = status;
        this.renterName = renterName;
        this.bicycleName = bicycleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
