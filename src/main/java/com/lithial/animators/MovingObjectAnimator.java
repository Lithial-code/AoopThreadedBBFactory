package com.lithial.animators;

import com.lithial.entities.Minion;
import com.lithial.entities.MovingObject;

public class MovingObjectAnimator implements Runnable {
    private MovingObject movingObject;
    private int movePerSec = 100;
    public MovingObjectAnimator(MovingObject movingObject){
        this.movingObject = movingObject;
    }

    public int getMovePerSec() {
        return movePerSec;
    }

    public void setMovePerSec(int movePerSec) {
        this.movePerSec = movePerSec;
    }

    @Override
    public void run() {
        while(true){
            movingObject.move();
            //System.out.println("this thread is running");
            //System.out.println(movingObject.getX() + ":" + movingObject.getY());
            System.out.println("Thread test");
            try{
                Thread.sleep(
                        20);
            } catch (InterruptedException e) {
                System.out.println("Something interrupted me while im sleeping");
            }
        }
    }
}
