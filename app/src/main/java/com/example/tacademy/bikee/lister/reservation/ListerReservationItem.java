package com.example.tacademy.bikee.lister.reservation;

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
    String bikeId;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imageURL;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String status;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String renterName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String bicycleName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date startDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Date endDate;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String price;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String reserveId;

    public ListerReservationItem(String bikeId,
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
}
