package com.bigtion.bikee.renter.searchresult.map;

/**
 * Created by User on 2016-06-07.
 */
public interface OnSearchResultMapFragmentListener {
    void setAddress(String address, double latitude, double longitude);
    void setHasLatLng(boolean hasLatLng);
}
