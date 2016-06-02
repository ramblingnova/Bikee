package com.bigtion.bikee.renter.searchresult.map;

import com.bigtion.bikee.renter.searchresult.SearchResultItem;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class POI {
    private String id;
    private String name;
    private String frontLat;
    private String frontLon;
    private String noorLat;
    private String noorLon;
    private String upperAddrName;
    private String middleAddrName;
    private String lowerAddrName;
    private String detailAddrName;
    private SearchResultItem item;

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

    public String getFrontLat() {
        return frontLat;
    }

    public void setFrontLat(String frontLat) {
        this.frontLat = frontLat;
    }

    public String getFrontLon() {
        return frontLon;
    }

    public void setFrontLon(String frontLon) {
        this.frontLon = frontLon;
    }

    public String getNoorLat() {
        return noorLat;
    }

    public void setNoorLat(String noorLat) {
        this.noorLat = noorLat;
    }

    public String getNoorLon() {
        return noorLon;
    }

    public void setNoorLon(String noorLon) {
        this.noorLon = noorLon;
    }

    public String getUpperAddrName() {
        return upperAddrName;
    }

    public void setUpperAddrName(String upperAddrName) {
        this.upperAddrName = upperAddrName;
    }

    public String getMiddleAddrName() {
        return middleAddrName;
    }

    public void setMiddleAddrName(String middleAddrName) {
        this.middleAddrName = middleAddrName;
    }

    public String getLowerAddrName() {
        return lowerAddrName;
    }

    public void setLowerAddrName(String lowerAddrName) {
        this.lowerAddrName = lowerAddrName;
    }

    public String getDetailAddrName() {
        return detailAddrName;
    }

    public void setDetailAddrName(String detailAddrName) {
        this.detailAddrName = detailAddrName;
    }

    @Override
    public String toString() {
        return name;
    }

    public double getLatitude() {
        return (Double.parseDouble(frontLat) + Double.parseDouble(noorLat)) / 2;
    }

    public double getLongitude() {
        return (Double.parseDouble(frontLon) + Double.parseDouble(noorLon)) / 2;
    }

    public String getAddress() {
        return upperAddrName + " " + middleAddrName + " " + lowerAddrName + " " + detailAddrName;
    }

    public double getLatitudeL1() {
        return 0;
    }

    public SearchResultItem getItem() {
        return item;
    }

    public void setItem(SearchResultItem item) {
        this.item = item;
    }
}
