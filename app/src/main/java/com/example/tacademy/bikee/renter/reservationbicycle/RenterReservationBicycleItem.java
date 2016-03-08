package com.example.tacademy.bikee.renter.reservationbicycle;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationBicycleItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String status;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date startDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date endDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int payment;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reserveId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double latitude;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double longitude;

    public RenterReservationBicycleItem(
            String bicycleId,
            String imageURL,
            String bicycleName,
            String status,
            Date startDate,
            Date endDate,
            int payment,
            String reserveId,
            double latitude,
            double longitude
    ) {
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
}
