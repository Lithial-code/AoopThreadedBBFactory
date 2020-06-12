package com.lithial.pathfinding;

import com.lithial.animators.MovingObjectAnimator;
import com.lithial.entities.Coin;
import com.lithial.entities.HomeNode;
import com.lithial.entities.Minion;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import org.graalvm.util.ObjectSizeEstimate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Lithial
 * Here is where i make a 2d grid of nodes as well as initialising all my entities.
 */
public class GameMap {
    //position variables
    private int xSize;
    private int ySize;
    //grid creation variables
    public Node[][] grid;
    public Node startNode;
    public Node endNode;

    public GameMap(int xSize, int ySize) {
        //this is the size of the game map in tiles
        this.ySize = ySize;
        this.xSize = xSize;
        //create node/tile array
        grid = new Node[xSize][ySize];

        genNodes();
        genHome();
        genMinions();
        if (GameInfo.GEN_WALLS){
            genMap("map.png");
        }
        genCoin(10);

    }

    /**
     * Take a small png image i made thats MaxSize* MaxSize pixels wide and scan it
     * for everywhere that a black pixel exists make the node in that position a wall
     * @param path is the path of the map file
     */
    public void genMap(String path){
        //set the color we're looking for
        Color black = new Color(000000);
        try {
            //load in the file
            BufferedImage image = ImageIO.read(new File(path));
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    //loop as if we're looking over a 2d grid
                    //if the pixel at that position is black
                    //set the relative node to a wall
                    if (image.getRGB(x,y) == black.getRGB()){
                        getNode(x,y).setIsWalkable(false);
                    }
                    else
                    {
                        getNode(x,y).setIsWalkable(true);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No maze here is probably an issue");
        }

    }

    /**
     * Use this in conjunction with generating a minion
     * makes the home tiles
     */
    public void genHome(){
        createHome(1,1, "Joe");
        createHome(GameInfo.MAX_SIZE - 4, 1, "Sam");
        //createHome(1, GameInfo.MAX_SIZE - 4, "Arran" );
        //createHome(GameInfo.MAX_SIZE - 4, GameInfo.MAX_SIZE -4, "Finn");
    }

    /**
     *
     * @param x is where it is on the x axis in tiles
     * @param y is where it is on the y axis in tiles
     * @param name is the name of whose home base it is
     */
    public void createHome(int x, int y, String name){
        HomeNode homeNode = new HomeNode(x, y, name);
        CollisionManager.addCollidable(homeNode);
        GameInfo.HOME_NODES.add(homeNode);
    }

    /**
     *generate random numbers and then check that theres no walls there before placing coins there
     * @param j is the number of coins you want to spawn
     */
    public void genCoin(int j){
        for (int i = 0 ; i < j; i ++){
            Random rand = new Random();
            int x = rand.nextInt(GameInfo.MAX_SIZE);
            int y = rand.nextInt(GameInfo.MAX_SIZE);
            while(!getNode(x,y).getIsWalkable()){
                x = rand.nextInt(GameInfo.MAX_SIZE);
                y = rand.nextInt(GameInfo.MAX_SIZE);
            }
            //System.out.println(x);
            //System.out.println(y);
            Coin coin = new Coin(x, y);
            GameInfo.COINS.add(coin);
            CollisionManager.addCollidable(coin);
        }
    }
    /**
     * This is where I'm registering all the minions. there will be four to start with
     * After creation they are added to a list so I can keep track of them easily
     * More than 2 seems to cause issues
     */
    public void genMinions(){
        createMinion("Joe", 4,1, Color.red); // 4 4 1 1
        createMinion("Sam", GameInfo.MAX_SIZE - 4,3, Color.pink); // max - 4 1 max - 4 1
        //createMinion("Arran", 4, GameInfo.MAX_SIZE - 4, this,Color.magenta); // 4, max -4 , 1,  max
        //createMinion("Finn", GameInfo.MAX_SIZE - 4, GameInfo.MAX_SIZE -4,this,Color.yellow);
    }

    /**
     * Used for the creation of minions
     * @param name minions name
     * @param x position on the x axis in tiles
     * @param y position on the y axis in tiles
     * @param color
     */
    public void createMinion(String name, int x, int y, Color color){
        Minion minion = new Minion(name,x, y, this, color);
        GameInfo.MINIONS.add(minion); //todo make these self initialise
        CollisionManager.addCollidable(minion);

        MovingObjectAnimator minion1 = new MovingObjectAnimator(minion);
        minion1.setMovePerSec(1);
        Thread thread = new Thread(minion1);
        GameInfo.THREADS.put(minion.getName(),thread);
    }
    /**
     * Generate all the map nodes. this is used for the pathfinding alogrithm later on
     */
    public void genNodes() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                Node node = new Node(x, y);
                grid[x][y] = node;
                //CollisionManager.addCollidable(node);
            }
        }
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                grid[x][y].setNeighbours(this);
            }
        }
        //System.out.println(grid.toString());
    }

    /**
     * return the node at the x and y coordinates providided
     * @param x
     * @param y
     * @return
     */
    public Node getNode(int x, int y) {
        if (x <= 0 || x >= GameInfo.MAX_SIZE){
            if (y <= 0 || y >= GameInfo.MAX_SIZE){
                if (GameInfo.DEBUG_MODE) {
                    System.out.println("Node at " + x + ":" + y + " doesn't exist");
                }
            }
        }
        try {
            return grid[x][y];
        }
        catch (Exception e){
            if (GameInfo.DEBUG_MODE)
            {
                System.out.println("Node at " + x + ":" + y + " doesn't exist");;
            }
        }
        return null;
    }

    /**
     * This returns all the nodes in list format because sometimes its easier to use them that way
     * @return the array of nodes as a list
     */
    public List<Node> gridAsList(){
     List<Node> nodes = new ArrayList<>();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                nodes.add(grid[x][y]);
            }
        }
        return nodes;
    }
}
