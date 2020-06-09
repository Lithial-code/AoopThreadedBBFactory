package com.lithial;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int xSize;
    private int ySize;
    public List<Node> nodes = new ArrayList<>();

    public GameMap(int xSize, int ySize) {
        this.ySize = ySize;
        this.xSize = xSize;
        genNodes();
    }

    /**
     * Generate all the map nodes. this is used for the pathfinding alogrithm later on
     */
    public void genNodes() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Node node = new Node(i, j);
                nodes.add(node);
            }
        }
        for (Node node :
                nodes) {
            node.setNeighbours(this);
        }
    }

    /**
     * return the node at the x and y coordinates providided
     * @param x
     * @param y
     * @return
     */
    public Node getNode(int x, int y) {
        for (Node node : nodes) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
}
