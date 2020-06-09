package com.lithial;

import java.awt.*;
import java.util.Map;

public class Debug {
    /**
     * Takes in a node and then paints all its neighbours in different colors depending on their distance value
     * @param node
     */
    public static void PaintNeighbours(Node node) {
        for(Map.Entry<Node, Integer> entry : node.getNeighbours().entrySet()) {
            if (entry.getKey() != null) {
                Node n = entry.getKey();
                System.out.println(n.getName());

                if (entry.getValue() == 1) {
                    n.setColor(Color.black);
                } else {
                    n.setColor(Color.orange);
                }

            }
        }
    }
}
