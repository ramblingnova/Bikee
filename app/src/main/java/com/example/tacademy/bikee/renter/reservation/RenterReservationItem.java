package com.example.tacademy.bikee.renter.reservation;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2015-10-31.
 */
public class RenterReservationItem implements Serializable {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleImageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleTitle;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double bicycleLatitude;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double bicycleLongitude;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reservationId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reservationStatus;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date reservationStartDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date reservationEndDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int pricePerMonth;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int pricePerDay;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int pricePerHour;

    public RenterReservationItem(String bicycleId,
                                 String bicycleImageURL,
                                 String bicycleTitle,
                                 double bicycleLatitude,
                                 double bicycleLongitude,
                                 String reservationId,
                                 String reservationStatus,
                                 Date reservationStartDate,
                                 Date reservationEndDate,
                                 int pricePerMonth,
                                 int pricePerDay,
                                 int pricePerHour) {
        this.bicycleId = bicycleId;
        this.bicycleImageURL = bicycleImageURL;
        this.bicycleTitle = bicycleTitle;
        this.bicycleLatitude = bicycleLatitude;
        this.bicycleLongitude = bicycleLongitude;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.pricePerMonth = pricePerMonth;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }
}
