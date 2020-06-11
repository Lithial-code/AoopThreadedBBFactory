package com.lithial.pathfinding;

import com.lithial.animators.MovingObjectAnimator;
import com.lithial.entities.Coin;
import com.lithial.entities.Minion;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;

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
        genMinions();
        for (int i = 0; i < 10; i++){
            genCoin();
        }
    }
    public void genCoin(){
        Random rand = new Random();
        int x = rand.nextInt(GameInfo.MAX_SIZE);
        int y = rand.nextInt(GameInfo.MAX_SIZE);
        System.out.println(x);
        System.out.println(y);
        Coin coin = new Coin(x, y);
        GameInfo.COINS.add(coin);
        CollisionManager.addCollidable(coin);

        //todo we'll come back to this?
        //GameInfo.MINIONS.get(0).findPath();
    }
    /**
     * This is where I'm registering all the minions. there will be four to start with
     * After creation they are added to a list so I can keep track of them easily
     */
    public void genMinions(){
        createMinion("Joe", 4,6,this);
        createMinion("Sam", 12,19,this);
        createMinion("Arran", 22,24,this);
        createMinion("Finn", 22,6,this);
    }
    public void createMinion(String name, int x, int y, GameMap map){
        Minion minion = new Minion(name,x, y, map);
        GameInfo.MINIONS.add(minion); //todo make these self initialise
        CollisionManager.addCollidable(minion);

        MovingObjectAnimator minion1 = new MovingObjectAnimator(minion);
        minion1.setMovePerSec(1);
        Thread thread1 = new Thread(minion1);
        ////todo hashmap this
        GameInfo.THREADS.put(minion.getName(),thread1);
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
    }

    /**
     * return the node at the x and y coordinates providided
     * //TODO Collision manager so that tiles know if a minion is on them?
     * @param x
     * @param y
     * @return
     */
    public Node getNode(int x, int y) {

        return grid[x][y];
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
