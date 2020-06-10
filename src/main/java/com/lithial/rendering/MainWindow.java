package com.lithial.rendering;

import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.events.listeners.ClickListener;

import javax.swing.*;
import java.awt.*;

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

        //basic window setup
        window.setTitle("TreasurePF");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(panel);
        window.setVisible(true);
        window.pack();



        /*-----------------------*/
        //event handler registration
        /*-----------------------*/
        //add mouse listener for keeping track of clicks
        canvasPanel.addMouseListener(new ClickListener(gameMap));

        GameInfo.THREADS.get(0).start();
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
