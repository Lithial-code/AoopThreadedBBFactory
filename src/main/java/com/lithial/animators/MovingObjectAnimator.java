package com.lithial.animators;

import com.lithial.entities.MovingObject;

/**
 * created and passed any object that implements moveable if you want it to run on its own thread
 */
public class MovingObjectAnimator implements Runnable {
    private MovingObject movingObject;
    private int movePerSec = 100;

    /**
     * this is the object passed. When you pass this instance of MovingObjectAnimator to a thread it will run it
     * @param movingObject
     */
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
            movingObject.pathFind();
            movingObject.move();
            try{
                Thread.sleep(
                        50);
            } catch (InterruptedException e) {
                System.out.println("Something interrupted me while im sleeping");
                return;
            }
        }
    }
}
