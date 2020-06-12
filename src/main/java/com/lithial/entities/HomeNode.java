package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.Node;

import java.awt.*;

public class HomeNode extends NodeBase {
    private String name;

    public HomeNode(int x, int y, String name) {
        super(x, y);
        setIsWalkable(true);
        this.name = name;
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

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(getxPosition(),getyPosition(),GameInfo.NODE_SIZE,GameInfo.NODE_SIZE);
    }
}
