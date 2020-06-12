package com.lithial.helpers;

import com.lithial.entities.Coin;
import com.lithial.entities.HomeNode;
import com.lithial.entities.Minion;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameInfo {

    int MAX_SIZE = 40;
    int NODE_SIZE = 20;
    int MAP_SIZE = MAX_SIZE * NODE_SIZE; // ive been making this equal 800 for good results

    boolean DEBUG_MODE = false;

    List<Minion> MINIONS = new ArrayList<>();
    List<Coin> COINS = new ArrayList<>();
    List<HomeNode> HOME_NODES = new ArrayList<>();

    int COIN_SIZE = 10;

    float MINION_SPEED = .25f;
    int MINION_SIZE = 20;
    Color MINION_COLOR = Color.green;

    Map<String, Thread> THREADS = new HashMap<>();
}
