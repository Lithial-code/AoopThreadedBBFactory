package com.lithial;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Node {
    private int x, y;
    private int xPosition;
    private int yPosition;
    private int size;
    private Map<Node, Double> neighbours = new HashMap<>();
    private Color color;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        size = GameInfo.NODE_SIZE;
        this.xPosition = x * size;
        this.yPosition = y * size;
        color = Color.white;
    }

    /**
     * Find all the nodes neighbours. This doesnt account for neighbours outside of the screen
     * @param map
     */
    public void setNeighbours(GameMap map){
        neighbours.put(map.getNode(x-1,y-1), 1.4);    //-1 -1
        neighbours.put(map.getNode(x-1, y), 1d);       //-1 0
        neighbours.put(map.getNode(x-1,y+1),1.4);     //-1 +1
        neighbours.put(map.getNode(x,y-1),1d);          // 0 -1
        neighbours.put(map.getNode( x,y+1),1d);          //0 +1
        neighbours.put(map.getNode(x+1,y-1),1.4);     //+1 -1
        neighbours.put(map.getNode(x+1,y),1d);          //+1 0
        neighbours.put(map.getNode(x+1,y+1),1.4);     //+1 +1
    }

    public Map<Node, Double> getNeighbours() {
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

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(xPosition, yPosition, size, size);
        g.setColor(Color.gray);
        g.drawRect(xPosition, yPosition, size, size);
    }
}
