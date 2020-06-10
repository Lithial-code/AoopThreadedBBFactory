/*
package com.lithial.events.listeners;

import com.lithial.helpers.Debug;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListenerDeprecated implements MouseListener {
    private GameMap gameMap;
    //todo scrap and rebuild
    //todo is supposed to only spawn a coin on left click or a wall on right click and even this might be changed
    //todo make it so if you hold shift and drag you build a wall and control and drag removes a wall

    public ClickListenerDeprecated(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    boolean isCorrectNode(MouseEvent mouseEvent, Node node) {
        if (mouseEvent.getX() >= node.getxPosition() && mouseEvent.getX() <= node.getxPosition() + GameInfo.NODE_SIZE) {
            if (mouseEvent.getY() >= node.getyPosition() && mouseEvent.getY() <= node.getyPosition() + GameInfo.NODE_SIZE) {
                return true;
            }
        }
        return false;
    }

    void clearScreen() {

        for (Node node : gameMap.gridAsList()) {
            if (gameMap.startNode == null) {
                if (node.getIsWalkable()) {
                    node.setColor(Color.white);
                }
            }
        }
    }

    void placeWalls(MouseEvent mouseEvent) {
        for (Node node : gameMap.gridAsList()) {
            if (isCorrectNode(mouseEvent, node)) {
                node.setColor(Color.black);
                node.setIsWalkable(false);
            }
        }
    }

    void drawClickNode(MouseEvent mouseEvent) {
        for (Node node : gameMap.gridAsList()) {

            if (isCorrectNode(mouseEvent, node)) {
                //System.out.println(node.toString());
                node.setColor(Color.red);
                if (gameMap.startNode == null) {
                    gameMap.startNode = node;
                }
                else {
                    gameMap.endNode = node;

                    //List<Node> path = Pathfinder.AStar(gameMap.gridAsList(), gameMap.startNode, gameMap.endNode);
                    //for (Node n : path) {
                    //    n.setColor(Color.green);
                    }
                    //gameMap.startNode = null;
                    //gameMap.endNode = null;
                }
                if (GameInfo.DEBUG_MODE) {
                    Debug.PaintNeighbours(node);
                }
            }
        }


    Node pickNode(MouseEvent mouseEvent) {
        for (Node node : gameMap.gridAsList()) {
            if (isCorrectNode(mouseEvent, node)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //todo click drag probably
        //System.out.println(mouseEvent.getX());
        //System.out.println(mouseEvent.getY());
        if(mouseEvent.getButton() == MouseEvent.BUTTON3){
            placeWalls(mouseEvent);
        }
        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        //Take all the nodes and see if where you clicked is one of them. if it is make it red
        //If debug mode is on paint the neighbours too just for good measure
        {
            clearScreen();
            drawClickNode(mouseEvent);

        }
    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
*/
