package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;
import com.lithial.pathfinding.Pathfinder;

import java.awt.*;
import java.util.ArrayList;
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

    //todo find a better way to do this?
    private Node startNode;
    private Node currentNode;
    private Node targetNode;
    private Node finalDestinationNode;
    private Node previousNode;

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

        targetNode = map.getNode(4,6);
    }


    //todo change this to use collisions to know what tile its on
    public Node getNodeFromMap(GameMap map)
    {
        currentNode = map.getNode((int)x,(int)y);
        return currentNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    @Override
    public void move() {

        //if (finalDestinationNode == null) {
        //System.out.println("Moving");
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
            CollisionManager.handleCollision(this);
            //System.out.println(x + ":" + y);

        }
        else if (targetNode.getX() == x && targetNode.getY() == y){
            //System.out.println("we're here!!");
        }
        else if (targetNode == null){
            System.out.println("Stop moving");
        }

    }


    /**
     * used to draw the token that is used to represent the minion
     * //todo update this to use sprites if time allows
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (targetNode != null){
            targetNode.setColor(Color.magenta);
        }
        if (startNode !=null)
        {
            startNode.setColor(Color.red);
        }
        if (previousNode != null){
            previousNode.setColor(color.white);
        }
        g.setColor(color);
        g.fillRect((int)(x * GameInfo.NODE_SIZE), (int)(y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE,GameInfo.MINION_SIZE);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x * GameInfo.NODE_SIZE), (int)(y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE,GameInfo.MINION_SIZE);
    }
    @Override
    public void pathfind(){
        Coin coin = GameInfo.COINS.get(0);
        targetNode = map.getNode(coin.getX(),coin.getY());
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
    if (eventSource instanceof Minion){
        if (collisionEvent.getImpact().equals("top")){
            y+=speed*4;
        }
        else if (collisionEvent.getImpact().equals("bottom")){
            y-=speed*4;
        }
        else if (collisionEvent.getImpact().equals("left")){
            x+=speed*4;
        }
        else if (collisionEvent.getImpact().equals("right")){
            x-=speed*4;
        }
        targetNode = null;
    }
    if (eventSource instanceof Coin){
        GameInfo.COINS.remove(eventSource);
        System.out.println("Gimme that loot");
        ((Coin) eventSource).setColor(Color.white);
    }
    }
    //todo unjank this
    public List<Node> findPath() {
       Coin coin = GameInfo.COINS.get(0);
       Node start = map.getNode((int)x, (int)y);
       Node end = map.getNode(coin.getX(),coin.getY());
       start.setColor(Color.blue);
       end.setColor(Color.pink);

       List<Node> path = Pathfinder.AStar(start,end);
/*       for (Node node: path){
           node.setColor(Color.magenta);
       }*/
       return path;
    }
}
