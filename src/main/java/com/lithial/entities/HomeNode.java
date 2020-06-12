package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;

import java.awt.*;

/**
 * This class is used to create the home nodes that a minion will return to drop off a coin.
 */
public class HomeNode extends NodeBase {

    private String name;
    public HomeNode(int x, int y, String name) {
        super(x, y);
        this.name = name;
        this.setIsWalkable(false);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getxPosition(),getyPosition(), GameInfo.NODE_SIZE,GameInfo.NODE_SIZE);
    }

    /**
     * Unused Collision method.Does nothing
     * @param collisionEvent
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }

    /**
     * draws a square in one of the tile positions in the world.
     * getXPosition/getYPosition is getXPos * GameInfo.NodeSize
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(getxPosition(),getyPosition(),GameInfo.NODE_SIZE,GameInfo.NODE_SIZE);
    }
}
