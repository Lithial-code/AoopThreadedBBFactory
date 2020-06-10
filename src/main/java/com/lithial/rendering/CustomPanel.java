package com.lithial.rendering;

import com.lithial.entities.Minion;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.pathfinding.Node;

import javax.swing.*;
import java.awt.*;

//todo check this for refactor at the end but its looking pretty good at what it does atm for now at least
public class CustomPanel extends JPanel {
    GameMap gameMap;
    public CustomPanel(GameMap gameMap){
        this.gameMap = gameMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Node node: gameMap.gridAsList()){
            node.draw(g);
        }
        for (Minion minion: GameInfo.MINIONS){
            minion.draw(g);
        }
    }
}
