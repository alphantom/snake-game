package models;

import models.contracts.Movable;
import util.Direction;

public abstract class Character implements Runnable, Movable {

    protected Direction direction;
    protected int speed;
    protected boolean isAlive = true;

    @Override
    public void run() {
        while(isAlive) {
            move(direction);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    protected abstract void spawn();

    public void die() {
        isAlive = false;
    }
}
