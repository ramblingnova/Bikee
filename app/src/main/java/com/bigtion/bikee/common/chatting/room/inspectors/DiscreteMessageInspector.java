package com.bigtion.bikee.common.chatting.room.inspectors;

/**
 * Created by User on 2016-03-28.
 */
public abstract class DiscreteMessageInspector {
    private DiscreteMessageInspector next = null;

    public final boolean isDiscreteMessage(InspectorItem item) {
        if (checkDiscrete(item))
            return true;
        else if (next != null)
            return next.isDiscreteMessage(item);
        return false;
    }

    protected abstract boolean checkDiscrete(InspectorItem item);

    public DiscreteMessageInspector setNext(DiscreteMessageInspector next) {
        this.next = next;
        return next;
    }
}
