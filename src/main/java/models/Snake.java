package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import util.Direction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Snake extends Character implements Predator {

    private Deque<Point> body;


    @Override
    protected void spawn() {
        this.body = new ArrayDeque<>(); //todo
    }

    @Override
    public void move(Direction direction) {
        this.body.getFirst().moveToDirection(direction);
        this.body.removeLast();
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
}
