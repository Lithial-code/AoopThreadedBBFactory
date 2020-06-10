package com.lithial.helpers;

import com.lithial.entities.Minion;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    public static int MAX_SIZE = 40;
    public static int NODE_SIZE = 20;
    public static int MAP_SIZE = MAX_SIZE * NODE_SIZE; // ive been making this equal 800 for good results

    public static boolean DEBUG_MODE = true;

    public static List<Minion> MINIONS = new ArrayList<>();
    public static int MINION_SPEED = 1;
    public static int MINION_SIZE = 20;
    public static Color MINION_COLOR = Color.green;

    public static List<Thread> THREADS = new ArrayList<>();
}
