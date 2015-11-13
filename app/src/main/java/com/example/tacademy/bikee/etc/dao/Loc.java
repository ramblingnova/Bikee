package com.example.tacademy.bikee.etc.dao;

import java.util.List;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Loc {
    public String type = null;
    public List<Double> coordinates = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
