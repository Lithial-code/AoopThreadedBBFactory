package com.lithial;

import com.lithial.helpers.GameInfo;
import com.lithial.pathfinding.GameMap;
import com.lithial.rendering.MainWindow;

public class Main {
    /**
     * Nice and tidy main function
     * @param args
     */
    public static void main(String[] args) {
        //Create our game map which is a game manager of sorts
        GameMap gameMap = new GameMap(GameInfo.MAX_SIZE, GameInfo.MAX_SIZE);
        //create our main window and feed it our game map so it knows what its drawing
        MainWindow mainWindow = new MainWindow(gameMap);



    }
}
