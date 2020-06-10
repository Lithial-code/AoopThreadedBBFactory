package com.lithial.entities;

import com.lithial.events.CollisionEvent;

import java.awt.*;

/**
 *
 * @author jnesis
 */
public interface IColliadable {
    Rectangle getBounds();
    void handleCollision(CollisionEvent collisionEvent);
}
