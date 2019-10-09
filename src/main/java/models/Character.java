package models;

import models.contracts.Movable;
import models.contracts.MovableObserver;
import util.direction.Direction;

import java.util.HashSet;
import java.util.Set;

public abstract class Character implements Runnable, Movable {

    protected Direction direction = Direction.LEFT;
    protected long speed;
    protected boolean isAlive = true;

    protected Point position;
    protected Point lastPosition;

    protected Set<MovableObserver> observers = new HashSet<>();

    @Override
    public void registerObserver(MovableObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MovableObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(item -> item.update(lastPosition, position));
    }

    @Override
    public void run() {
        while(isAlive) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            move(direction);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

//    protected abstract void spawn();

    public void die() {
        isAlive = false;
    }
}
