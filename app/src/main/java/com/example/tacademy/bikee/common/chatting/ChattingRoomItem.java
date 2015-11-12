package com.example.tacademy.bikee.common.chatting;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by User on 2015-10-31.
 */
public class ChattingRoomItem implements Serializable {
    public ChattingRoomItem(String bicycle_name, String payment, String type, String height, String distance) {
        this.bicycle_name = bicycle_name;
        this.payment = payment;
        this.type = type;
        this.height = height;
        this.distance = distance;
    }

    private String bicycle_name;
    private String payment;
    private String type;
    private String height;
    private String distance;

    public String getBicycle_name() {
        return bicycle_name;
    }

    public void setBicycle_name(String bicycle_name) {
        this.bicycle_name = bicycle_name;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
