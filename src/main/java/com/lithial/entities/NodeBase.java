package com.lithial.entities;

import com.lithial.entities.interfaces.IColliadable;
import com.lithial.entities.interfaces.IDrawable;
import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;

import java.awt.*;

/**
 *  Base class for the nodes.
 *  Includes IColliadable and IDrawable so that things work
 */
public class NodeBase implements IColliadable, IDrawable {

    //required for positioning
    private int x, y;
    //required for rendering
    private Color color;
    private int size;
    private boolean isWalkable;
    public NodeBase(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.white;
        this.size = GameInfo.NODE_SIZE;

    }

    /**
     * made for debugging mostly. is a nice to string
     * @return
     */
    public String getName() {
        return "Node: " + x + ":" + y;
    }
    /**
     * Made for debugging also. used in the debug rendering of the tile names below.
     * @return
     */
    public String getSimpleName(){return x + ":" + y;}

    //dont need setters for this i dont think
    public int getxPosition() {
        return x * size;
    }
    public int getyPosition() {
        return y * size;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    /**
     * used in pathfinding to determine if this tile can be moved across
     * @return
     */
    public boolean getIsWalkable() {
        return isWalkable;
    }
    /**
     * used in pathfinding to set if this tile can be moved across
     * @param isWalkable
     */
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }
    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }

    @Override
    public void draw(Graphics g) {


    }
}
