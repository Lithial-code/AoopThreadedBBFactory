package com.lithial;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(GameInfo.MAX_SIZE, GameInfo.MAX_SIZE);
        MainWindow mainWindow = new MainWindow(gameMap);
    }
}
