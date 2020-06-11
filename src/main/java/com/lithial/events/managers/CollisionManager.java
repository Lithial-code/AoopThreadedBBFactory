package com.lithial.events.managers;

import com.lithial.entities.IColliadable;
import com.lithial.events.CollisionEvent;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private final static List<IColliadable> COLLIABLES = new ArrayList<>();

    public static void addCollidable(IColliadable collidable){
        COLLIABLES.add(collidable);
    }
    public static void handleCollision(IColliadable collidable){
        List<IColliadable> others = new ArrayList<>(COLLIABLES);
        others.remove(collidable);
        //handleFloorCollision(collidable,others);
        handleOtherShapesCollisions(collidable, others);
    }

    protected static void handleOtherShapesCollisions(IColliadable collidable, List<IColliadable> others) {
        for (IColliadable otherCollidable : others) {
            if (otherCollidable.getBounds() != null) {
                if (otherCollidable.getBounds().intersects(collidable.getBounds())) {
                    CollisionEvent event = new CollisionEvent(otherCollidable);
                    double depth = 0;
                    if (collidable.getBounds().getY() < otherCollidable.getBounds().getY() + otherCollidable.getBounds().height) {
                        //Top border
                        event.setImpact("top");
                        depth = otherCollidable.getBounds().getY() + otherCollidable.getBounds().height - collidable.getBounds().getY();
                    }
                    if (collidable.getBounds().getY() + collidable.getBounds().getHeight() > otherCollidable.getBounds().getY()) {
                        //Bottom border
                        double newDepth = collidable.getBounds().getY() + collidable.getBounds().getHeight() - otherCollidable.getBounds().getY();
                        if (event.getImpact() == null || newDepth < depth) {
                            event.setImpact("bottom");
                            depth = newDepth;
                        }
                    }
                    //Detect the vertical borders
                    if (collidable.getBounds().getX() < otherCollidable.getBounds().getX() + otherCollidable.getBounds().width) {
                        //Left border
                        double newDepth = otherCollidable.getBounds().getX() + otherCollidable.getBounds().width - collidable.getBounds().getX();
                        if (event.getImpact() == null || newDepth < depth) {
                            event.setImpact("left");
                            depth = newDepth;
                        }
                    }
                    if (collidable.getBounds().getX() + collidable.getBounds().getWidth() > otherCollidable.getBounds().getX()) {
                        //Right border
                        double newDepth = collidable.getBounds().getX() + collidable.getBounds().getWidth() - otherCollidable.getBounds().getX();
                        if (event.getImpact() == null || newDepth < depth) {
                            event.setImpact("right");
                        }

                    }

                    collidable.handleCollision(event);
                    CollisionEvent oppositeEvent = new CollisionEvent(collidable);
                    switch (event.getImpact()) {
                        case "left":
                            oppositeEvent.setImpact("right");
                            break;
                        case "right":
                            oppositeEvent.setImpact("left");
                            break;
                        case "top":
                            oppositeEvent.setImpact("bottom");
                            break;
                        case "bottom":
                            oppositeEvent.setImpact("top");
                    }
                    otherCollidable.handleCollision(oppositeEvent);
                }
            }
        }
    }
}
