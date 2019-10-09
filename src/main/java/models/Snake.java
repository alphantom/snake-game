package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionObserver;

import java.util.ArrayDeque;
import java.util.Deque;

public class Snake extends Character implements Predator, DirectionObserver {

    private Deque<Point> body = new ArrayDeque<>();
    protected long speed = SettingUtil.SNAKE_SPEED;

    public Snake(int size) {
        while (this.body.size() < size) {
            System.out.println("add point to body");
            this.body.add(new Point(SettingUtil.SCALE, body.size() * SettingUtil.SCALE));
        }
        System.out.println(this.body.size());
    }
//    @Override
//    protected void spawn() {
//        this.body = new ArrayDeque<>(); //todo
//    }

    @Override
    public void move(Direction direction) {
        System.out.println("snake moves!");
        body.getFirst().moveToDirection(direction);
        position = this.body.getFirst();
        lastPosition = this.body.getLast();
        body.removeLast();
        notifyObservers();
    }

    public Deque<Point> getBody() {
        return body;
    }

    @Override
    public void eat(Eatable eatable) {
        eatable.effect(this);
    }

    @Override
    public void growth(Point point) {
        this.body.addFirst(point);
    }

    @Override
    public void getDamage() {
        this.body.removeLast();
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }

}
