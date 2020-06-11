package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.Node;

import java.awt.*;
import java.util.Random;

public class Coin implements IColliadable, IDrawable{

    private int x,y;
    private int xPos, yPos;
    private int value;
    private int radius;
    private Color color;
    Random rand;

    public Coin(int x, int y) {

        this.x = x;
        this.xPos = x * GameInfo.NODE_SIZE;
        this.y = y;
        this.yPos = y * GameInfo.NODE_SIZE;
        value = 20;
        radius = 20;
        color = Color.orange;
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(xPos,yPos, GameInfo.NODE_SIZE,GameInfo.NODE_SIZE);
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(xPos + (radius ),yPos+ (radius),radius,radius);
    }
}
