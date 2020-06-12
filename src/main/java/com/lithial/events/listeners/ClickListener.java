package com.lithial.events.listeners;

import com.lithial.entities.Minion;
import com.lithial.helpers.Debug;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;
import com.lithial.pathfinding.Pathfinder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Used to register clicks on the screen for debugging purposes
 */
public class ClickListener implements MouseListener {
    private GameMap gameMap;
    //todo scrap and rebuild
    //todo is supposed to only spawn a coin on left click or a wall on right click and even this might be changed
    //todo make it so if you hold shift and drag you build a wall and control and drag removes a wall

    public ClickListener(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    void clearScreen() {

    }


    /**
     * Was used in debugging origionally so that I could see my pathfinding
     * Not going to change it for now
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //todo click drag probably

        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            //Take all the nodes and see if where you clicked is one of them. if it is make it red
            //If debug mode is on paint the neighbours too just for good measure
            for (Node node : gameMap.gridAsList()) {
                if (mouseEvent.getX() >= node.getxPosition() && mouseEvent.getX() <= node.getxPosition() + GameInfo.NODE_SIZE) {
                    if (mouseEvent.getY() >= node.getyPosition() && mouseEvent.getY() <= node.getyPosition() + GameInfo.NODE_SIZE) {

                        for (Minion minion: GameInfo.MINIONS) {
                            if (minion.getTargetNode() != null){
                                System.out.println("On my way");
                                minion.pathFind();
                                // GameInfo.MINIONS.get(0).setPreviousNode(GameInfo.MINIONS.get(0).getTargetNode());
                            }

                        }
                        System.out.println("target node set to: " + node.getSimpleName());
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
