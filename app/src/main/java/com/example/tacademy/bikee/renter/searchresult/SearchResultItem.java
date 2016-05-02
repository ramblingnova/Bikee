package com.example.tacademy.bikee.renter.searchresult;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-05-02.
 */
public class SearchResultItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycle_name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String height;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String type;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String payment;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double distance;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double latitude;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double longitude;

    public SearchResultItem(String bicycleId,
                                String imageURL,
                                String bicycle_name,
                                String height,
                                String type,
                                String payment,
                                double distance,
                                double latitude,
                                double longitude) {
        this.bicycleId = bicycleId;
        this.imageURL = imageURL;
        this.bicycle_name = bicycle_name;
        this.height = height;
        this.type = type;
        this.payment = payment;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
