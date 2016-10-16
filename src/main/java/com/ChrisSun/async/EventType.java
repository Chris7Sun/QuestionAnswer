package com.ChrisSun.async;

/**
 * Created by Chris on 2016/9/6.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    private EventType(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

}
