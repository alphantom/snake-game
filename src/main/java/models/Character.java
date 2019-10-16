package models;

import models.contracts.Movable;
import models.contracts.MovableObserver;
import util.direction.Direction;
import util.draw.DrawObserver;
import util.draw.DrawSubject;

import java.util.HashSet;
import java.util.Set;

public abstract class Character implements Runnable, Movable, DrawSubject {

    protected volatile Direction direction = Direction.RIGHT;
    protected long speed;
    protected volatile boolean isAlive = true;

    protected Point position;
    protected Point lastPosition;
    protected boolean stopped = false;

    protected Set<MovableObserver> observers = new HashSet<>();
    protected Set<DrawObserver> drawObservers = new HashSet<>();

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
        observers.forEach(MovableObserver::update);
    }

    public void registerDrawObserver(DrawObserver observer) {
        drawObservers.add(observer);
    }

    public void removeDrawObserver(DrawObserver observer) {
        drawObservers.remove(observer);
    }

    public void notifyDrawObservers(){
        drawObservers.forEach(item -> item.update(this));
    }

    @Override
    public void run() {
        while(isAlive) {
            synchronized (this) {
                try {
                    if (stopped) wait();
                    move();
                    Thread.sleep(getSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getPosition() {
        return position;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public void die() {
        isAlive = false;
    }

    public void stop() {
        stopped = true;
    }

    public synchronized void resume() {
        stopped = false;
        notifyAll();
    }
}
