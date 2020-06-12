package com.lithial.entities;

import com.lithial.events.CollisionEvent;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;
import com.lithial.pathfinding.Pathfinder;

import java.awt.*;
import java.util.List;

public class Minion extends MovingObject implements IDrawable, IColliadable {
    //id variables
    private String name;
    //positioning variables
    private float x, y;

    //todo find a better way to do this? // unjank this
    private HomeNode homeNode;
    /// private Node currentNode;
    private Node targetNode;
    private List<Node> targetPath;
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

    public Minion(String name, int x, int y, GameMap map, Color pathColor) {
        this.name = name;
        this.x = x;
        this.y = y;
        //System.out.println(currentNode.getSimpleName());
        this.color = GameInfo.MINION_COLOR;
        this.speed = GameInfo.MINION_SPEED;
        this.map = map;

        totalCoinValue = 0;
        this.pathColor = pathColor;
    }

    public int getIntX() {
        return (int) x;
    }

    public int getIntY() {
        return (int) y;
    }

    public Coin getInventory() {
        return inventory;
    }

    public void setInventory(Coin inventory) {
        this.inventory = inventory;
    }

    public Node getHomeNode() {
        for (HomeNode node : GameInfo.HOME_NODES) {
            if (node.getName().equals(name)) {

                return map.getNode(node.getX(), node.getY());
            }
        }
        return null;
    }

    public void setHomeNode(HomeNode homeNode) {
        this.homeNode = homeNode;
    }

    public Node getCurrentNode(){
        return map.getNode(getIntX(),getIntY());
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

    public List<Node> getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(List<Node> targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public void move() {
            targetNode = targetPath.get(0);

            if (x < targetNode.getX()) {
                x += speed;
            }
            if (y < targetNode.getY()) {
                y += speed;
            }
            if (x > targetNode.getX()) {
                x -= speed;
            }
            if (y > targetNode.getY()) {
                y -= speed;
            }
            targetPath = null;
            targetNode = null;
            CollisionManager.handleCollision(this);

    }
    @Override
    public void pathfind(){
            if (getInventory() == null){
                System.out.println(targetNode);
                System.out.println(targetPath);
                System.out.println("Empty yo pockets");
                findPath("Coin");
            }
            else if (this.getInventory() != null){
                System.out.println("take that loot home");
                findPath("Home");
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

    public void findPath(String type){
        switch (type){
            case "Coin":
            {
                if (GameInfo.COINS.size() > 0){
                    try{
                        Coin coin = GameInfo.COINS.get(GameInfo.COINS.size() -1);
                        Node n = map.getNode(coin.getX(),coin.getY());
                        targetPath = Pathfinder.AStar(getCurrentNode(), n);
                        break;
                    }
                 catch (Exception e){
                     System.out.println(e.getMessage());
                 }
                }
            }
            case "Home":
            {
                targetPath = Pathfinder.AStar(getCurrentNode(), getHomeNode());
                break;
            }
            default: break;
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
            //((Node) eventSource).setIsWalkable(false);
        }
        if (eventSource instanceof Minion) {
        }
        if (eventSource instanceof Coin) {
            if (this.getInventory() == null) {
                System.out.println("Gimme that loot");
                ((Coin) eventSource).setColor(Color.white);
                this.setInventory((Coin) eventSource);
                ((Coin) eventSource).setBounds(null);
                GameInfo.COINS.remove(eventSource);
            }
        }
        if (eventSource instanceof HomeNode) {
            if (((HomeNode) eventSource).getName().equals(this.getName())) {
                System.out.println("touched base");
                if (this.getInventory() != null) {
                    System.out.println(this.getName() + ": coin value " + this.getInventory().getValue());
                    totalCoinValue += this.getInventory().getValue();
                    System.out.println("Total value: " + totalCoinValue);
                    setInventory(null);
                    System.out.println("This is empty");
                    targetPath = null;
                    targetNode = null;
//                    System.out.println(getInventory().getId());
                }
            }
        }
    }

}
