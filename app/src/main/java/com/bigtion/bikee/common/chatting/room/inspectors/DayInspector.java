package com.bigtion.bikee.common.chatting.room.inspectors;

import java.text.SimpleDateFormat;

/**
 * Created by User on 2016-03-28.
 */
public class DayInspector extends DiscreteMessageInspector {
    @Override
    public boolean checkDiscrete(InspectorItem item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", java.util.Locale.getDefault());
        int current = Integer.parseInt(simpleDateFormat.format(item.getCurrent()));
        int target = Integer.parseInt(simpleDateFormat.format(item.getTarget()));

        return current != target;
    }
}
