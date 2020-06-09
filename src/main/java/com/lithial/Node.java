package com.lithial;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Node {
    private int x, y;
    private int xPosition;
    private int yPosition;
    private int size;

    private int fCost;
    private  int gCost;
    private  int hCost;

    private boolean walkable;
    private Node parent;

    private Map<Node, Integer> neighbours = new HashMap<>();
    private Color color;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        size = GameInfo.NODE_SIZE;
        this.xPosition = x * size;
        this.yPosition = y * size;
        color = Color.white;
        walkable = true;
    }

    /**
     * Find all the nodes neighbours. This doesnt account for neighbours outside of the screen
     * @param map
     */
    public void setNeighbours(GameMap map){
        neighbours.put(map.getNode(x-1,y-1), 14);    //-1 -1
        neighbours.put(map.getNode(x-1, y), 10);       //-1 0
        neighbours.put(map.getNode(x-1,y+1),14);     //-1 +1
        neighbours.put(map.getNode(x,y-1),10);          // 0 -1
        neighbours.put(map.getNode( x,y+1),10);          //0 +1
        neighbours.put(map.getNode(x+1,y-1),14);     //+1 -1
        neighbours.put(map.getNode(x+1,y),10);          //+1 0
        neighbours.put(map.getNode(x+1,y+1),14);     //+1 +1
    }

    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return "Node: " + x + ":" + y;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public int getfCost() {
        return gCost + hCost;
    }
    public int gethCost(){
        return hCost;
    }
    public int getgCost(){
        return gCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(xPosition, yPosition, size, size);
        g.setColor(Color.gray);
        g.drawRect(xPosition, yPosition, size, size);
    }
}
