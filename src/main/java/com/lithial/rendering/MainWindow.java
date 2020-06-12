package com.lithial.rendering;

import com.lithial.entities.Coin;
import com.lithial.entities.Minion;
import com.lithial.events.managers.CollisionManager;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.events.listeners.ClickListener;
import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainWindow {
    public MainWindow(GameMap gameMap){
        //Initalise different panels and frames
        JFrame window = new JFrame();
        CustomPanel canvasPanel = new CustomPanel(gameMap);
        JPanel panel = new JPanel();

        //set panel size from game info config
        canvasPanel.setPreferredSize(new Dimension(GameInfo.MAP_SIZE,GameInfo.MAP_SIZE)); //TODO put this as a variable

        //set panel layout
        panel.setLayout(new BorderLayout());
        panel.add(canvasPanel, BorderLayout.CENTER);
        Button coinButton = new Button("Drop Coins");
        coinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              gameMap.genCoin(11); // todo fix this because its broke for sure
            }
        });
        panel.add(coinButton, BorderLayout.SOUTH);
        //basic window setup
        window.setTitle("TreasurePF");
        window.setLocationRelativeTo(null);
        window.setLocation(600,100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(panel);
        window.setVisible(true);
        window.pack();

        /*-----------------------*/
        //event handler registration
        /*-----------------------*/
        //add mouse listener for keeping track of clicks
        canvasPanel.addMouseListener(new ClickListener(gameMap));

        //start all the minion threads
   /*     for(Map.Entry<String, Thread> entry : GameInfo.THREADS.entrySet()) {
            entry.getValue().start();
        }*/
        /**
         * every 50ms redraw the screen.
         */
        while(true){
            panel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Something interrupted me while sleeping...");
            }
        }
    }
}
