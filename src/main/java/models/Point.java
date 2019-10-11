package models;

import settings.SettingUtil;
import util.direction.Direction;
import util.draw.DrawObserver;
import util.draw.DrawSubject;

import java.util.HashSet;
import java.util.Set;

public class Point {

    private int x;
    private int y;

    private final int step = SettingUtil.SCALE;

    private int previousX;
    private int previousY;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
    }

    public void moveUp() {
        previousY = y;
        y-=step;

    }

    public void moveDown() {
        previousY = y;
        y+=step;
    }

    public void moveLeft() {
        previousX = x;
        x-=step;
    }

    public void moveRight() {
        previousX = x;
        x+=step;
    }

    public void moveToDirection(Direction direction) {
        switch(direction) {
            case UP: this.moveUp();
                break;
            case DOWN: this.moveDown();
                break;
            case LEFT: this.moveLeft();
                break;
            case RIGHT: this.moveRight();
                break;
        }

    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Point) return false;
        Point that = (Point) o;
        return that.x == this.x && that.y == this.y;
    }

}
