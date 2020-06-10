package com.lithial.events;

import java.util.EventObject;

public class CollisionEvent extends EventObject {
    private String impact;
    public static final String WALLS_EVENT_SOURCE = "WALLS";
    public CollisionEvent(Object source) {
        super(source);
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}
