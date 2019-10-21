package models;

import models.contracts.Eatable;
import models.contracts.Movable;
import models.contracts.MovableObserver;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionObserver;
import util.draw.DrawSubject;

import java.util.*;

public class Snake extends Character implements Predator, DirectionObserver {

    private volatile LinkedList<Point> body;
    private short[] color = new short[] {255, 255, 0};
    protected long speed = SettingUtil.SNAKE_SPEED;

    private int experience;

    public Snake(int size) {
        body = new LinkedList<>();
        while (body.size() < size) {
            body.add(new Point((1 + body.size()) * SettingUtil.SCALE, SettingUtil.SCALE, color));
        }
        notifyObservers();
    }

    @Override
    public void move() {
        Point point = body.getLast();
        position = new Point(point.getX(), point.getY(), color);
        position.moveToDirection(direction);
        body.add(position);
        lastPosition = body.getFirst();
        body.removeFirst();
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
    public void addXp(int xp) {
        experience += xp;
    }

    @Override
    public void growth(Point point) {
        Point last = body.get(body.size() - 1);
        position = new Point(last.getX(), last.getY(), color);
        position.moveToDirection(direction);
        body.add(position);
        notifyObservers();
        notifyObservers();
    }

    public Point getPosition() {
        return body.getLast();
    }

    @Override
    public void getDamage() {
        if (body.size() > 3) body.removeFirst();
    }

    @Override
    public void getKilled() {
        super.die();
        notifyObservers();
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }

    public int getXp() {
        return experience;
    }
}
