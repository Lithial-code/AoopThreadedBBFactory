package com.lithial.pathfinding;

import com.lithial.entities.IColliadable;
import com.lithial.events.CollisionEvent;
import com.lithial.helpers.GameInfo;
import com.lithial.helpers.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Node implements IColliadable {
    //todo this needs a refactor probably.
    //required for pathfinding
    private int fCost;
    private  int gCost;
    private  int hCost;
    private boolean isWalkable;
    private Node parent;
    //todo this doesnt need to be a hashmap anymore

    private Map<Node, Integer> neighbours = new HashMap<>();

    //required for positioning
    private int x, y; //todo maybe switch this to a vector2 but its a big change and might complicate things more than required
    //required for rendering
    private Color color;
    private int size;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        size = GameInfo.NODE_SIZE;

        color = Color.white;
        isWalkable = true;
    }
    /**
     * Find all the nodes neighbours. This doesnt account for neighbours outside of the screen
     * @param map
     */
    public void setNeighbours(GameMap map){

        for (int i = -1; i < 1; i ++){
            for (int j = -1; j < 1; j++){
                Node node = map.getNode(x,y);
                if (node.getX() < 0 || node.getX() > GameInfo.MAP_SIZE)
                {
                    if (node.getY() < 0 || node.getY() > GameInfo.MAP_SIZE)
                    {
                        neighbours.put(node, 1);
                    }
                }
            }
        }
    }

    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }

    //dont need setters for this i dont think
    public int getxPosition() {
        return x * size;
    }
    public int getyPosition() {
        return y * size;
    }

    /**
     * No setters because the tiles should never move
     * @return
     */
    public int getX() {
        return x;
    }
    /**
     * No setters because the tiles should never move
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * made for debugging mostly. is a nice to string
     * @return
     */
    public String getName() {
        return "Node: " + x + ":" + y;
    }

    /**
     * Made for debugging also. used in the debug rendering of the tile names below.
     * @return
     */
    public String getSimpleName(){return x + ":" + y;}

    /**
     * Used to change the color of a tile. was used in debugging but might also be used in the future
     * //TODO determine if this is still required.
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
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
     * Used to get the point at the center of the tile.
     * //todo make this account for the size of the object calling it.
     * @return
     */
    public Vector2 getCentreOfTile(){
        int x = getxPosition() / 2;
        int y= getyPosition()  / 2;
        return new Vector2(x,y);
    }

    /**
     * used in pathfinding to determine if this tile can be moved across
     * @return
     */
    public boolean getIsWalkable() {
        return isWalkable;
    }

    /**
     * used in pathfinding to set if this tile can be moved across
     * @param isWalkable
     */
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
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
        g.setColor(color);
        g.fillRect(getxPosition(), getyPosition(), size, size);
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
        return new Rectangle(getxPosition() + (size / 2), getyPosition() + (size / 2), (size / 2), (size / 2));
    }

    @Override
    public void handleCollision(CollisionEvent collisionEvent) {

    }
}
