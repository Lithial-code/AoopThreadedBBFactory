package com.lithial;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickListener implements MouseListener {
    private GameMap gameMap;
    public ClickListener(GameMap gameMap){
        this.gameMap = gameMap;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //todo click drag probably
        //System.out.println(mouseEvent.getX());
        //System.out.println(mouseEvent.getY());

        //Take all the nodes and see if where you clicked is one of them. if it is make it red
        //If debug mode is on paint the neighbours too just for good measure
        for (Node node : gameMap.nodes) {
            if(mouseEvent.getX() >= node.getxPosition() && mouseEvent.getX() <= node.getxPosition() + GameInfo.NODE_SIZE)
            {
                if(mouseEvent.getY() >= node.getyPosition() && mouseEvent.getY() <= node.getyPosition() + GameInfo.NODE_SIZE)
                {
                    System.out.println(node.toString());
                    node.setColor(Color.red);
                    if(GameInfo.DEBUG_MODE)
                    {
                        Debug.PaintNeighbours(node);
                    }
                }
            }

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
