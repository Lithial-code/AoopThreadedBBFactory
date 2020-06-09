package com.lithial;

import java.util.*;

public class Pathfinder {

    public static List<Node> AStar(List<Node> graph, Node start, Node target){
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(start);

        while(!openList.isEmpty()){
            Node node = openList.get(0);
            for (int i = 1; i < openList.size(); i++){
                if (openList.get(i).getfCost() < node.getfCost() || openList.get(i).getfCost() == node.getfCost()){
                    if (openList.get(i).gethCost() < node.gethCost()){
                        node = openList.get(i);
                    }
                }
            }
            openList.remove(node);
            closedList.add(node);
            if (node == target){
                return retracepath(start,target);

            }
            for (Map.Entry<Node, Integer> neighbour: node.getNeighbours().entrySet()) {
                Node neigh = neighbour.getKey();
                if (!neigh.isWalkable() || closedList.contains(neigh))
                {
                    continue;
                }
                int newCostToNeighbour = node.getgCost() + getDistance(node, neigh);
                if (newCostToNeighbour < neigh.getgCost() || !openList.contains(neigh)){
                    neigh.setgCost(newCostToNeighbour);
                    neigh.sethCost(getDistance(neigh, target));
                    neigh.setfCost(neigh.getgCost()+neigh.gethCost());
                    neigh.setParent(node);
                    if (!openList.contains(neigh)){
                        openList.add(neigh);
                    }
                }
            }

        }
        return null;
    }
    static List<Node> retracepath(Node start, Node end){
        List<Node> path = new ArrayList<>();
        Node currentNode = end;
        while(currentNode != start){
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        //flip list
        Collections.reverse(path);
        return path;
        //return list somehow
    }
    static int getDistance(Node nodeA, Node nodeB)
    {
        int dstX = Math.abs(nodeA.getX() - nodeB.getX());
        int dstY = Math.abs(nodeA.getY() - nodeB.getY());
        if(dstX > dstY)
        {
            return 14 * dstY + 10 * (dstX - dstY);
        }
        else {
            return 14*dstX + 10 * (dstY-dstX);
        }
    }
}
