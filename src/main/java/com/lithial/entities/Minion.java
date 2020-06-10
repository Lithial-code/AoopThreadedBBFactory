package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.helpers.Vector2;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;
import com.lithial.pathfinding.Pathfinder;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

//todo make understand what tile its standing on with collisions?
//todo make it broadcast its distance to a coin when its dropped
//todo give it a space to carry a coin in
//todo make threaded
public class Minion extends MovingObject implements IDrawable, IColliadable{
    //id variables
    private String name;
    //positioning variables
    private float x, y;

    private Node currentNode;
    private Node targetNode;
    private Node startNode;
    private Node finalDestinationNode;
    private List<Node>path;
    private GameMap map;
    //movement variables
    private float speed;
    //rendering variables
    private Color color;

    public Minion(String name, int x, int y, GameMap map) {
        this.name = name;
        this.x = x;
        this.y = y;
        //System.out.println(currentNode.getSimpleName());
        this.color = GameInfo.MINION_COLOR;
        this.speed = GameInfo.MINION_SPEED;
        this.map = map;

        startNode = map.getNode(4,6);
        targetNode = map.getNode(12,25);
    }


    //todo change this to use collisions to know what tile its on
    /*public Node getNodeFromMap(GameMap map)
    {
        currentNode = map.getNode(currentLocation.getX(),getCurrentLocation().getY());
        return currentNode;
    }*/

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public Node getFinalDestinationNode() {
        return finalDestinationNode;
    }

    public void setFinalDestinationNode(Node finalDestinationNode) {
        this.finalDestinationNode = finalDestinationNode;
    }

    @Override
    public void move() {

        //if (finalDestinationNode == null) {
        System.out.println("Moving");
        if (targetNode != null){
            if (x < targetNode.getX()){
                x+=speed;
            }
            else if (y < targetNode.getY())
            {
                y+=speed;
            }
            else if (x > targetNode.getX()){
                x-=speed;
            }
            else if (y > targetNode.getY()){
                y-=speed;
            }
            System.out.println(x + ":" + y);
        }
        if (targetNode.getX() == x && targetNode.getY() == y){
            System.out.println("we're here!!");
        }

    }


    /**
     * used to draw the token that is used to represent the minion
     * //todo update this to use sprites if time allows
     * @param graphics
     */
    @Override
    public void draw(Graphics graphics) {
        if (targetNode != null){
            targetNode.setColor(Color.magenta);
        }
        if (startNode !=null)
        {
            startNode.setColor(Color.red);
        }
        graphics.setColor(color);
        graphics.fillRect((int)(x * GameInfo.NODE_SIZE), (int)(y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE,GameInfo.MINION_SIZE);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x * GameInfo.NODE_SIZE), (int)(y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE,GameInfo.MINION_SIZE);
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
    IColliadable eventSource = (IColliadable) collisionEvent.getSource();
    if (eventSource instanceof Node){
        //System.out.println(((Node) eventSource).getSimpleName());
        setCurrentNode((Node)eventSource);
        //((Node) eventSource).setColor(Color.red);
        //System.out.println(getCurrentNode().getSimpleName());
    }
    }

}
