package com.lithial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow {
    public MainWindow(GameMap gameMap){
        JFrame window = new JFrame();
        window.setTitle("TreasurePF");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CustomPanel canvasPanel = new CustomPanel(gameMap);
        canvasPanel.setPreferredSize(new Dimension(800,800)); //TODO put this as a variable
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(canvasPanel, BorderLayout.CENTER);
        canvasPanel.addMouseListener(new ClickListener(gameMap));
        window.setContentPane(panel);
        window.setVisible(true);
        window.pack();

        while(true){
            panel.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println("Something interrupted me while sleeping...");
            }
        }
    }
}
