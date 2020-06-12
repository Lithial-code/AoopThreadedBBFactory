package com.lithial.entities;

import com.lithial.entities.interfaces.IColliadable;
import com.lithial.entities.interfaces.IDrawable;
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

    //pathfinding variables
    private Node targetNode;
    private List<Node> targetPath;
    private GameMap map;

    //movement variables
    private float speed;

    //rendering variables
    private Color color;
    private Color pathColor;

    //inventory variables
    private Coin inventory;
    private int totalCoinValue;

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

    /**
     * finds the home node assosiated with this minion. this is decided by name
     * @return the home node for this minion
     */
    public Node getHomeNode() {
        for (HomeNode node : GameInfo.HOME_NODES) {
            if (node.getName().equals(name)) {

                return map.getNode(node.getX(), node.getY());
            }
        }
        return null;
    }

    public void setHomeNode(HomeNode homeNode) {
    }

    /**
     * @return the node the minion is on right now
     */
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

    public int getTotalCoinValue() {
        return totalCoinValue;
    }

    public void setTotalCoinValue(int totalCoinValue) {
        this.totalCoinValue = totalCoinValue;
    }

    /**
     * this controlls the minions movement
     * is called exclusively through the MovingObjectManager
     * so it can run on its own thread
     * This runs in conjunction with the pathfind function
     */
    @Override
    public void move() {
        try {
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
        }
        catch (Exception e){
            System.out.println("Needs more pathfinding. try again");
            pathFind();
        }
        CollisionManager.handleCollision(this);

    }

    /**
     * this controlls the minions path
     * is called exclusively through the MovingObjectManager
     * so it can run on its own thread
     * This runs in conjunction with the move function
     */
    @Override
    public void pathFind(){
            if (getInventory() == null){
                //System.out.println(targetNode);
                //System.out.println(targetPath);
                //System.out.println("Empty yo pockets");
                findPath("Coin");
            }
            else if (this.getInventory() != null){
                //System.out.println("take that loot home");
                findPath("Home");
            }
    }
    /**
     * used to draw the token that is used to represent the minion
     * at this point its just a little square
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) (x * GameInfo.NODE_SIZE), (int) (y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE, GameInfo.MINION_SIZE);
    }

    /**
     * Bounding box used for collisions
     * is a rectangle in the middle of a tile
     * @return
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) (x * GameInfo.NODE_SIZE), (int) (y * GameInfo.NODE_SIZE), GameInfo.MINION_SIZE, GameInfo.MINION_SIZE);
    }

    /**
     * Function for working out if its time to call pathfinding and if so what kind.
     * @param type
     */
    public void findPath(String type){
        switch (type){
            case "Coin":
            {
                System.out.println("Go find a coin");
                //if its time to look for coins
                if (GameInfo.COINS.size() > 0){
                    try{
                        //try looking for a coin in the array
                        Coin coin = GameInfo.COINS.get(GameInfo.COINS.size() -1);
                        Node n = map.getNode(coin.getX(),coin.getY());
                        //used this to force minions to find a path when they get lost
                        while (targetPath == null){
                            targetPath = Pathfinder.AStar(getCurrentNode(), n);
                        }
                        break;
                    }
                 catch (Exception e){
                     System.out.println(e.getMessage());
                 }
                }
            }
            case "Home":
            {
                System.out.println("Go home");
                //force it to find home if it gets stuck
                while (targetPath == null){
                    targetPath = Pathfinder.AStar(getCurrentNode(), getHomeNode());
                }
                break;
            }
            default: break;
        }
    }

    /**
     * Handles the collisions for everything,
     * used by the collision manager called in move to make sure it interacts with things properly
     * This needs some more work because things keep getting stuck but It kind of works for now
     * @param collisionEvent
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        IColliadable eventSource = (IColliadable) collisionEvent.getSource();
        if (eventSource instanceof Node) {
            //((Node) eventSource).setIsWalkable(false);
        }
        if (eventSource instanceof Minion) {
        }
        if (eventSource instanceof Coin) {
            if (this.getInventory() == null) {
                //System.out.println("Gimme that loot");
                ((Coin) eventSource).setColor(Color.white);
                this.setInventory((Coin) eventSource);
                //((Coin) eventSource).setBounds(null);
                GameInfo.COINS.remove(eventSource);
            }
        }
        if (eventSource instanceof HomeNode) {
            if (((HomeNode) eventSource).getName().equals(this.getName())) {
                //System.out.println("touched base");
                if (this.getInventory() != null) {
                    //System.out.println(this.getName() + ": coin value " + this.getInventory().getValue());
                    totalCoinValue += this.getInventory().getValue();
                    //System.out.println("Total value: " + totalCoinValue);
                    setInventory(null);
                    //System.out.println("This is empty");
                    targetPath = null;
                    targetNode = null;
                }
                if (this.getInventory() == null){
                    targetPath = null;
                    targetNode = null;
                    pathFind();
                }
            }
        }
    }

}
