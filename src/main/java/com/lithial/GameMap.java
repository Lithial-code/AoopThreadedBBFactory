package com.lithial;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int xSize;
    private int ySize;
    public Node[][] grid;
    public Node startNode;
    public Node endNode;

    public GameMap(int xSize, int ySize) {
        this.ySize = ySize;
        this.xSize = xSize;
        System.out.println("Got here");
        grid = new Node[xSize][ySize];
        genNodes();
    }

    /**
     * Generate all the map nodes. this is used for the pathfinding alogrithm later on
     */
    public void genNodes() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Node node = new Node(i, j);
                grid[i][j] = node;
            }
        }
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid[i][j].setNeighbours(this);
            }

        }
    }

    /**
     * return the node at the x and y coordinates providided
     * @param x
     * @param y
     * @return
     */
    public Node getNode(int x, int y) {
        for (Node node : gridAsList()) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
    //make the grid a list
    public List<Node> gridAsList(){
     List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                nodes.add(grid[i][j]);
            }
        }
        return nodes;
    }
}
