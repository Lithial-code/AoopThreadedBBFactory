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
    private Rectangle bounds;

    public Coin(int x, int y) {
        rand = new Random();
        this.x = x;
        this.xPos = x * GameInfo.NODE_SIZE;
        this.y = y;
        this.yPos = y * GameInfo.NODE_SIZE;
        value = rand.nextInt(20);
        radius = GameInfo.COIN_SIZE;
        color = Color.orange;
        bounds = new Rectangle(xPos + (GameInfo.NODE_SIZE /2),yPos + (GameInfo.NODE_SIZE /2), GameInfo.NODE_SIZE/2,GameInfo.NODE_SIZE/2);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        IColliadable eventSource = (IColliadable) collisionEvent.getSource();
        if (eventSource instanceof Minion){
            for (Minion minion: GameInfo.MINIONS){
               if (minion != eventSource){
                   minion.setTargetPath(null);
                   minion.setTargetNode(null);
               }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(xPos + (radius ),yPos+ (radius),radius,radius);
    }
}
