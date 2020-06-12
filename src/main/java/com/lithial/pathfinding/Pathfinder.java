package com.lithial.pathfinding;

import java.util.*;

public class Pathfinder {
    //TODO comment this but otherwise it should be ok.  also maybe add heaps and stuff

    /**
     * https://en.wikipedia.org/wiki/A*_search_algorithm
     * This is complicated. But basically you can give this a start and end node and it will give you a list of nodes
     * to go through to get there
     * @param start start of the path
     * @param target end of the path
     * @return list of nodes in the path
     */
    public static List<Node> AStar(Node start, Node target){

        //create and open and closed list. Would have really liked to have use a heap here for performance but didnt
        // have enough time
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        //add the start node to the open list
        openList.add(start);

        //while the list has something in it
        while(!openList.isEmpty()){
            Node node = openList.get(0);
            for (int i = 1; i < openList.size(); i++){
                //sort the list based on fcost
                if (openList.get(i).getfCost() < node.getfCost() || openList.get(i).getfCost() == node.getfCost()){
                    //if fcost is the same go off hcost
                    if (openList.get(i).gethCost() < node.gethCost()){
                        node = openList.get(i);
                    }
                }
            }
            //add the selected node to the closed list.  remove it from the open one
            openList.remove(node);
            closedList.add(node);
            //if the node selected is the target then this is the path take
            if (node == target){
                return retracepath(start,target);

            }
            //if not check all its neighbours
            for (Node neigh : node.getNeighbours()){
                //skip them if theyre a wall
                if (!neigh.getIsWalkable() || closedList.contains(neigh))
                {
                    continue;
                }
                //check  neighbours distances and calculate costs
                int newCostToNeighbour = node.getgCost() + getDistance(node, neigh);
                //compare nodes
                if (newCostToNeighbour < neigh.getgCost() || !openList.contains(neigh)){
                    neigh.setgCost(newCostToNeighbour);
                    neigh.sethCost(getDistance(neigh, target));
                    neigh.setfCost(neigh.getgCost()+neigh.gethCost());
                    neigh.setParent(node);
                    //if the neighbour is a good fit move it into the open list
                    if (!openList.contains(neigh)){
                        openList.add(neigh);
                    }
                }
            }

        }
        System.out.println("Path not found");
        return null;
    }

    /**
     * This is used effetively to flip the list because we work backwards to find the best option
     * @param start start node
     * @param end end node
     * @return returns the new flipped list
     */
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

    /**
     * Distance helper for doing math between nodes
     * @param nodeA
     * @param nodeB
     * @return
     */
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
