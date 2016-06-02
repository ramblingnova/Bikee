package com.bigtion.bikee.etc.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-06-02.
 */
public class GetGeoLocationReceiveObject {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Channel channel = null;

    public class Channel {
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String result = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String pageCount = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String title = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String totalCount = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String description = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        List<Item> item = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String lastBuildDate = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String link = null;
        @Getter
        @Setter(AccessLevel.PUBLIC)
        String generator = null;

        public class Item {
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String newAddress = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String mountain = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String buildingAddress = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            double lng = -1.0;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String placeName = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String mainAddress = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String id = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            double point_x = -1.0;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            double point_y = -1.0;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String title = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String isNewAddress = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String point_wx = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String point_wy = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String subAddress = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String localName_1 = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String localName_2 = null;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            double lat = -1.0;
            @Getter
            @Setter(AccessLevel.PUBLIC)
            String localName_3 = null;
        }
    }
}
