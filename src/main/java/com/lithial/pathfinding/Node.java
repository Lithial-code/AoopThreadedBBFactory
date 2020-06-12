package com.lithial.pathfinding;

import com.lithial.entities.interfaces.IColliadable;
import com.lithial.entities.NodeBase;
import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node extends NodeBase implements IColliadable {

    //required for pathfinding
    private int fCost;
    private  int gCost;
    private  int hCost;

    private Node parent;

    //private Map<Integer, Node> neighbours = new HashMap<>();
    List<Node> neighbours = new ArrayList<>();

    public Node(int x, int y) {
        super(x,y);
        this.setIsWalkable(true);
    }

    /**
     * Find all the nodes neighbours. This is required for pathfinding
     */
    public void setNeighbours(GameMap map) {
        addLoneNeighbour(map,getX()-1,getY()-1);
        addLoneNeighbour(map,getX()-1, getY());
        addLoneNeighbour(map,getX()-1,getY()+1);
        addLoneNeighbour(map,getX(),getY()-1);
        addLoneNeighbour(map,getX(),getY()+1);
        addLoneNeighbour(map,getX()+1,getY()-1);
        addLoneNeighbour(map,getX()-1,getY()-1);
        addLoneNeighbour(map,getX()+1,getY());
        addLoneNeighbour(map,getX()+1,getY()+1);
    }

    /**
     * Small function for checking that neighbours dont appear off screen because we dont count those
     * @param map access to the game map for access to the node list
     * @param x x tile location
     * @param y y tile location
     */
    private void addLoneNeighbour(GameMap map, int x, int y){
        if ((x >= 1) && x <= GameInfo.MAX_SIZE){
            if ((y >= 1) && y <= GameInfo.MAX_SIZE){
                neighbours.add(map.getNode(x,y));
            }
        }
    }
    public List<Node> getNeighbours() {
        return neighbours;
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Used to draw the nodes. Currently only used to draw the walls which are just recoloured unwalkable nodes.
     * has a nifty debug function used to write on the nodes what tile they are
     * @param g
     */
    @Override
    public void draw(Graphics g){
        //draw filled box of the set color
        if (this.getIsWalkable())
        {
            g.setColor(this.getColor());
            g.fillRect(getxPosition(), getyPosition(), getSize(), getSize());
        }
       else{
            g.setColor(Color.BLACK);
            g.fillRect(getxPosition(), getyPosition(), getSize(), getSize());
        }

        //draw gray outline around said box for ease of seeing

        //debug option to render what node is what on the grid
        if (GameInfo.DEBUG_MODE)
        {
            g.setColor(Color.gray);
            g.drawRect(getxPosition(), getyPosition(), this.getSize(), this.getSize());
            g.setColor(Color.cyan);
            g.drawString(getSimpleName(), getxPosition() + 15, getyPosition() + 15 );
        }
    }

    /**
     * Used in collisions for bounding box logic
     * @return
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getxPosition() + (getSize()/ 2), getyPosition() + (getSize()/ 2), (getSize()/ 2), (getSize()/ 2));
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }
}
