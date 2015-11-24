package com.example.tacademy.bikee.lister.sidemenu.evaluatedbicycle;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class EvaluatedBicyclePostScriptItem {
    private String imageURL;
    private String renterName;
    private String bicycleName;
    private String createDate;
    private String description;
    private int point;

    public EvaluatedBicyclePostScriptItem(String imageURL, String renterName, String bicycleName, String createDate, String description, int point) {
        this.imageURL = imageURL;
        this.renterName = renterName;
        this.bicycleName = bicycleName;
        this.createDate = createDate;
        this.description = description;
        this.point = point;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
