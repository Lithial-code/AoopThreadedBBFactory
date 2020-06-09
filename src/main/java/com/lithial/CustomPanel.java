package com.lithial;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    GameMap gameMap;
    public CustomPanel(GameMap gameMap){
        this.gameMap = gameMap;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Node node: gameMap.nodes){
            node.draw(g);
        }
    }
}
