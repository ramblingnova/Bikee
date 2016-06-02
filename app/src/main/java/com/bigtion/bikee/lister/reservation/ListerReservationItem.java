package com.bigtion.bikee.lister.reservation;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class ListerReservationItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleId;
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
    String renterImageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String renterName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String renterPhone;
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

    public ListerReservationItem(String bicycleId,
                                 String bicycleTitle,
                                 double bicycleLatitude,
                                 double bicycleLongitude,
                                 String renterImageURL,
                                 String renterName,
                                 String renterPhone,
                                 String reservationId,
                                 String reservationStatus,
                                 Date reservationStartDate,
                                 Date reservationEndDate,
                                 int pricePerMonth,
                                 int pricePerDay,
                                 int pricePerHour) {
        this.bicycleId = bicycleId;
        this.bicycleTitle = bicycleTitle;
        this.bicycleLatitude = bicycleLatitude;
        this.bicycleLongitude = bicycleLongitude;
        this.renterImageURL = renterImageURL;
        this.renterName = renterName;
        this.renterPhone = renterPhone;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.pricePerMonth = pricePerMonth;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }
}
