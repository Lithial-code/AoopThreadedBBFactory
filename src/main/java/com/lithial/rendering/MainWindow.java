package com.lithial.rendering;

import com.lithial.events.listeners.ClickListener;
import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Window settings for the jpanel. everything is generated in here for a nice clean main
 */
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
              gameMap.genCoin(1);
            }
        });
        Button startButton = new Button("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //start all the minion threads
                for(Map.Entry<String, Thread> entry : GameInfo.THREADS.entrySet()) {
                    entry.getValue().start();
                }
            }
        });
        Button resetButton = new Button("Stop");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(Map.Entry<String, Thread> entry : GameInfo.THREADS.entrySet()) {
                    entry.getValue().interrupt();
                }
            }
        });
        Panel buttonPanel = new Panel();
        Panel textPanel = new Panel();
        Panel bottomPanel = new Panel();
        String lValueString = "Joes Treasure: " + GameInfo.MINIONS.get(0).getTotalCoinValue();
        String rValueString = "Sams Treasure: " + GameInfo.MINIONS.get(1).getTotalCoinValue();

        Label leftLabel = new Label(lValueString);
        Label rightLabel = new Label(rValueString);
        GameInfo.LABELS.add(leftLabel);
        GameInfo.LABELS.add(rightLabel);

        textPanel.add(leftLabel, BorderLayout.EAST);
        textPanel.add(rightLabel, BorderLayout.WEST);

        buttonPanel.add(coinButton, BorderLayout.EAST);
        buttonPanel.add(startButton, BorderLayout.CENTER);
        buttonPanel.add(resetButton, BorderLayout.WEST);
        bottomPanel.add(textPanel,BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * every 50ms redraw the screen.
         */
        while(true){
            lValueString = "Joes Treasure: " + GameInfo.MINIONS.get(0).getTotalCoinValue();
            rValueString = "Sams Treasure: " + GameInfo.MINIONS.get(1).getTotalCoinValue();
            leftLabel.setText(lValueString);
            rightLabel.setText(rValueString);
            panel.repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.println("Something interrupted me while sleeping...");
                return;
            }
        }
    }
}
