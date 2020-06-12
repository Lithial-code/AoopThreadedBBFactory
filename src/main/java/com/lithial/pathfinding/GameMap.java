package com.lithial.pathfinding;

import com.lithial.animators.MovingObjectAnimator;
import com.lithial.entities.Coin;
import com.lithial.entities.HomeNode;
import com.lithial.entities.Minion;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    private int xSize;
    private int ySize;

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
        genMap();
        //todo make this less jank
        for (int i = 0; i < 10; i++){
            genCoin();
        }
    }


    public void genMap(){
        Color black = new Color(000000);

        try {
            BufferedImage image = ImageIO.read(new File("map.png"));
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
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

    public void genHome(){
        createHome(1,1, "Joe");
        createHome(GameInfo.MAX_SIZE - 4, 1, "Sam");
        //createHome(1, GameInfo.MAX_SIZE - 4, "Arran" );
        //createHome(GameInfo.MAX_SIZE - 4, GameInfo.MAX_SIZE -4, "Finn");
    }
    public void createHome(int x, int y, String name){
        HomeNode homeNode = new HomeNode(x, y, name);
        CollisionManager.addCollidable(homeNode);
        GameInfo.HOME_NODES.add(homeNode);
    }
    public void genCoin(){
        Random rand = new Random();
        int x = rand.nextInt(GameInfo.MAX_SIZE);
        int y = rand.nextInt(GameInfo.MAX_SIZE);
        while(!getNode(x,y).getIsWalkable()){
            x = rand.nextInt(GameInfo.MAX_SIZE);
            y = rand.nextInt(GameInfo.MAX_SIZE);
        }
        System.out.println(x);
        System.out.println(y);
        Coin coin = new Coin(x, y);
        GameInfo.COINS.add(coin);
        CollisionManager.addCollidable(coin);
    }
    /**
     * This is where I'm registering all the minions. there will be four to start with
     * After creation they are added to a list so I can keep track of them easily
     */
    public void genMinions(){
        createMinion("Joe", 4,1,this, Color.red); // 4 4 1 1
        createMinion("Sam", GameInfo.MAX_SIZE - 4,1, this, Color.pink); // max - 4 1 max - 4 1
        //createMinion("Arran", 4, GameInfo.MAX_SIZE - 4, this,Color.magenta); // 4, max -4 , 1,  max
        //createMinion("Finn", GameInfo.MAX_SIZE - 4, GameInfo.MAX_SIZE -4,this,Color.yellow);
    }
    public void createMinion(String name, int x, int y, GameMap map, Color color){
        Minion minion = new Minion(name,x, y, map, color);
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
                CollisionManager.addCollidable(node);
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
     * //TODO Collision manager so that tiles know if a minion is on them?
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
            System.out.println("Node at " + x + ":" + y + " doesn't exist");;
        }
        return null;
    }

    /**
     * This returns all the nodes in list format because sometimes its easier to use them that way
     * @return
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
