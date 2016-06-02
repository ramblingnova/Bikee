package com.bigtion.bikee.common.popup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-06-03.
 */
public class AddressItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String address;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double latitude;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    double longitude;

    public AddressItem(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
