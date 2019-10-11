package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionObserver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Snake extends Character implements Predator, DirectionObserver {

    private List<Point> body;
    protected long speed = SettingUtil.SNAKE_SPEED;
    protected Direction direction = Direction.RIGHT;

    public Snake(int size) {
        body = new LinkedList<>();
        while (body.size() < size) {
            System.out.println("add point to body");
            body.add(new Point(SettingUtil.SCALE, body.size() * SettingUtil.SCALE));
        }
        System.out.println(this.body.size());
    }
//    @Override
//    protected void spawn() {
//        this.body = new ArrayDeque<>(); //todo
//    }

    @Override
    public void move() {
        System.out.println("snake moves!");
        Point last = body.get(body.size() - 1);
        position = new Point(last.getX(), last.getY());
        position.moveToDirection(direction);
        body.add(position);
        lastPosition = body.get(0);
        if (true) {
            body.remove(0);
        }
        notifyObservers();
    }

    public List<Point> getBody() {
        return body;
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public void eat(Eatable eatable) {
        eatable.effect(this);
    }

    @Override
    public void growth(Point point) {
        //
    }

    @Override
    public void getDamage() {
        body.remove(0);
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }

}
