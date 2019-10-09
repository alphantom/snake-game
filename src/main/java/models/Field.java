package models;

import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.draw.DrawObserver;
import util.draw.DrawSubject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Field implements DrawSubject, MovableObserver {
    private Snake snake;
    private final List<GreenFrog> frogs;

    private int width = SettingUtil.WIDTH / SettingUtil.SCALE;
    private int height = SettingUtil.HEIGHT / SettingUtil.SCALE;

//    private Object[][] grid = new Object[height][width];
    private final Set<Point> changes = new HashSet<>();
    private final Set<DrawObserver> observers = new HashSet<>();

    public Field() { // todo make startGame method
        snake = new Snake(SettingUtil.SNAKE_LENGTH);
        snake.registerObserver(this);
        changes.addAll(snake.getBody());
//        Thread snakeThread = new Thread(snake);
//        snakeThread.start();

        frogs = new ArrayList<>();
        spawnFrog();
        notifyDrawObservers();
    }

    public void spawnFrog() {
        while (frogs.size() < SettingUtil.FROG_COUNT) {
            GreenFrog frog = new GreenFrog(new Point(3 * SettingUtil.SCALE,3 * SettingUtil.SCALE));
            frog.registerObserver(this);
            frogs.add(frog);
            changes.add(frog.getPosition());
            Thread frogThread = new Thread(frog);
            frogThread.start();
        }
        // trim array
    }

    @Override
    public void registerDrawObserver(DrawObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeDrawObserver(DrawObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyDrawObservers() {
        for (DrawObserver observer : observers) {
            observer.update(changes);
        }
    }

    @Override
    public void update(Point from, Point to) {
        if (changes != null) changes.remove(from);
        changes.add(to);
        notifyDrawObservers();
    }
}
