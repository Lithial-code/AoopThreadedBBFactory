package com.lithial.pathfinding;

import com.lithial.entities.IColliadable;
import com.lithial.entities.NodeBase;
import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;
import com.lithial.helpers.Vector2;
import jdk.nashorn.internal.ir.BaseNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node extends NodeBase implements IColliadable {

    //todo this needs a refactor probably.
    //required for pathfinding
    private int fCost;
    private  int gCost;
    private  int hCost;

    private Node parent;
    //todo this doesnt need to be a hashmap anymore

    //private Map<Integer, Node> neighbours = new HashMap<>();
    List<Node> neighbours = new ArrayList<>();


    public Node(int x, int y) {
        super(x,y);
        this.setIsWalkable(true);
        //todo make pathfinding work
    }

    /**
     * Find all the nodes neighbours. This doesnt account for neighbours outside of the screen
     *
     */
    public void setNeighbours(GameMap map) {
        neighbours.add(map.getNode(getX()-1,getY()-1));    //-1 -1
        neighbours.add(map.getNode(getX()-1, getY()));       //-1 0
        neighbours.add(map.getNode(getX()-1,getY()+1));     //-1 +1
        neighbours.add(map.getNode(getX(),getY()-1));          // 0 -1
        neighbours.add(map.getNode( getX(),getY()+1));          //0 +1
        neighbours.add(map.getNode(getX()+1,getY()-1));     //+1 -1
        neighbours.add(map.getNode(getX()+1,getY()));          //+1 0
        neighbours.add(map.getNode(getX()+1,getY()+1));
    }
    public List<Node> getNeighbours() {
        return neighbours;
    }

    /** //todo write good descriptions of what these are for
     * @return
     */
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

    /**
     * used in pathfinding to determine what node this connects to
     * @return
     */
    public Node getParent() {
        return parent;
    }
    /**
     * used in pathfinding to set which node this one connects to in the tree
     * @param parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * This is required for rendering. Is called by the custom panel via a node list loop
     * @param g
     */
    public void draw(Graphics g){
        //draw filled box of the set color
        g.setColor(this.getColor());
        g.fillRect(getxPosition(), getyPosition(), getSize(), getSize());
        //draw gray outline around said box for ease of seeing
        //g.setColor(Color.gray);
        //g.drawRect(getxPosition(), getyPosition(), size, size);
        //debug option to render what node is what on the grid
        if (GameInfo.DEBUG_MODE)
        {
            g.drawString(getSimpleName(), getxPosition() + 15, getyPosition() + 15 );
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getxPosition() + (getSize()/ 2), getyPosition() + (getSize()/ 2), (getSize()/ 2), (getSize()/ 2));
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }
}
