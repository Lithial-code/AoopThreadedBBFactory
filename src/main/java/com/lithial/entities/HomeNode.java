package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.Node;

import java.awt.*;

public class HomeNode extends NodeBase {

    public HomeNode(int x, int y) {
        super(x, y);
        CollisionManager.addCollidable(this);
        GameInfo.HOME_NODES.add(this);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(),getY(), GameInfo.NODE_SIZE,GameInfo.NODE_SIZE);
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
