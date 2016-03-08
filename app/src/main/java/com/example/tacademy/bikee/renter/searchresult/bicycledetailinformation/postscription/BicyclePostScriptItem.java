package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptItem {
    private String imageURL;
    private String renterName;
    private int point;
    private String postScript;
    private String createAt;

    public BicyclePostScriptItem(String imageURL, String renterName, int point, String postScript, String createAt) {
        this.imageURL = imageURL;
        this.renterName = renterName;
        this.point = point;
        this.postScript = postScript;
        this.createAt = createAt;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
