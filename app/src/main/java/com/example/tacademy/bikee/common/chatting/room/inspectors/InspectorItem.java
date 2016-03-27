package com.example.tacademy.bikee.common.chatting.room.inspectors;

import java.util.Date;

/**
 * Created by User on 2016-03-28.
 */
public class InspectorItem {
    private Date current;
    private Date target;

    public InspectorItem(Date current, Date target) {
        this.current = current;
        this.target = target;
    }

    public Date getCurrent() {
        return current;
    }

    public Date getTarget() {
        return target;
    }
}
