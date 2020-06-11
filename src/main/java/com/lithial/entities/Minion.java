package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.Debug;
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
public class Minion extends MovingObject implements IDrawable, IColliadable {
    //id variables
    private String name;
    //positioning variables
    private float x, y;

    //todo find a better way to do this?
    private HomeNode homeNode;
    /// private Node currentNode;
    private Node targetNode;
    //private Node finalDestinationNode;
    private Node previousNode;

    private List<Node> path;
    private GameMap map;
    //movement variables
    private float speed;
    //rendering variables
    private Color color;
    private Coin inventory;
    private int totalCoinValue;
    private Color pathColor;
    public Minion(String name, int x, int y, int homeX, int homeY, GameMap map, Color pathColor) {
        this.name = name;
        this.x = x;
        this.y = y;
        //System.out.println(currentNode.getSimpleName());
        this.color = GameInfo.MINION_COLOR;
        this.speed = GameInfo.MINION_SPEED;
        this.map = map;

        //startNode = map.getNode(4,6);
        //targetNode = map.getNode(4,6);
        homeNode = new HomeNode(homeX, homeY);
        totalCoinValue = 0;
        this.pathColor = pathColor;
        
    }
    public int getIntX(){
        return (int)x;
    }
    public int getIntY(){
        return (int)y;
    }
    public Coin getInventory() {
        return inventory;
    }

    public void setInventory(Coin inventory) {
        this.inventory = inventory;
    }

    public HomeNode getHomeNode() {
        return homeNode;
    }

    public void setHomeNode(HomeNode homeNode) {
        this.homeNode = homeNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    @Override
    public void move() {


        if (targetNode != null) {
            //Debug.PaintNeighbours(targetNode);
            //System.out.println(targetNode.getSimpleName());
            if (x < targetNode.getX()) {
                x += speed;
            } else if (y < targetNode.getY()) {
                y += speed;
            } else if (x > targetNode.getX()) {
                x -= speed;
            } else if (y > targetNode.getY()) {
                y -= speed;
            }
            CollisionManager.handleCollision(this);

        } else if (targetNode == null) {
            System.out.println("Stop moving");
        }

    }


    /**
     * used to draw the token that is used to represent the minion
     * //todo update this to use sprites if time allows
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {

        if (previousNode != null) {
            previousNode.setColor(color.white);
        }
        g.setColor(color);
        g.fillRect((int) (x * GameInfo.NODE_SIZE), (int) (y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE, GameInfo.MINION_SIZE);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (x * GameInfo.NODE_SIZE), (int) (y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE, GameInfo.MINION_SIZE);
    }

    @Override
    public void pathfind() {
        int count = 0;
        if (count <= GameInfo.COINS.size()){
        if (inventory == null) {
            while (GameInfo.COINS.size() > 0) {
                try {
                    //System.out.println(getName() + " is searching for coin");
                    Coin coin = GameInfo.COINS.get(count);
                    targetNode = map.getNode(coin.getX(), coin.getY());
                    break;
                } catch (Exception e) {
                    System.out.println("couldnt find coin");
                }
                count++;
            }
        }
        }
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        if (collisionEvent.getSource().equals(CollisionEvent.WALLS_EVENT_SOURCE)) {
            switch (collisionEvent.getImpact()) {
                case "left":
                    x += GameInfo.NODE_SIZE;
                    break;
                case "right":
                    x -= GameInfo.NODE_SIZE;
                    break;
                case "top":
                    y += GameInfo.NODE_SIZE;
                    break;
                case "bottom":
                    y -= GameInfo.NODE_SIZE;
                    break;
            }
        }
        IColliadable eventSource = (IColliadable) collisionEvent.getSource();
        if (eventSource instanceof Node) {

        }
        if (eventSource instanceof Minion) {
            if (collisionEvent.getImpact().equals("top")) {
                x += speed * GameInfo.NODE_SIZE;
                y += speed * GameInfo.NODE_SIZE;
            } else if (collisionEvent.getImpact().equals("bottom")) {
                x -= speed * GameInfo.NODE_SIZE;
                y += speed * GameInfo.NODE_SIZE;
            } else if (collisionEvent.getImpact().equals("left")) {
                y += speed * GameInfo.NODE_SIZE;
                x += speed * GameInfo.NODE_SIZE;

            } else if (collisionEvent.getImpact().equals("right")) {
                y -= speed * GameInfo.NODE_SIZE;
                x += speed * GameInfo.NODE_SIZE;
            }
        }
        if (eventSource instanceof Coin) {
            if (this.getInventory() == null) {
                GameInfo.COINS.remove(eventSource);
                System.out.println("Gimme that loot");
                ((Coin) eventSource).setColor(Color.white);
                this.setInventory((Coin) eventSource);
                ((Coin) eventSource).setBounds(null);
                this.targetNode = map.getNode(homeNode.getX(), homeNode.getY());
                List<Node> path = Pathfinder.AStar(map.getNode(getIntX(),getIntY()),targetNode);
                for (Node n : path) {
                    n.setColor(pathColor);
                }

            }
        }
        if (eventSource instanceof HomeNode){
            if (eventSource.equals(this.homeNode)){
            if (this.getInventory() != null){
                System.out.println(this.getName() + ": coin value " + this.getInventory().getValue());
                totalCoinValue += this.getInventory().getValue();
                System.out.println("Total value: " + totalCoinValue);
                setInventory(null);
            }
            }
        }
    }

}
