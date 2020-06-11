package com.lithial.helpers;

import com.lithial.pathfinding.Node;

import java.awt.*;
import java.util.Map;

public class Debug {
    /**
     * Takes in a node and then paints all its neighbours in different colors depending on their distance value
     * @param node
     */
    public static void PaintNeighbours(Node node) {
        for(Node n : node.getNeighbours()) {
            System.out.println(n.getName());
               // if(n.getIsWalkable()) {
                    if (node.getfCost() == 1) {
                        n.setColor(Color.black);
                    } else {
                        n.setColor(Color.orange);
                    }
               // }

        }
    }
}
