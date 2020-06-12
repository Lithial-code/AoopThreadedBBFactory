package com.lithial.animators;

import com.lithial.entities.Minion;
import com.lithial.helpers.GameInfo;

public class PathfindingManager implements Runnable {


    @Override
    public void run() {

        while(true){
            for (Minion minion: GameInfo.MINIONS){
                if (minion.getTargetPath() == null || minion.getTargetPath().isEmpty()){
                    minion.pathfind();
                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
