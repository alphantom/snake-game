package models;

import settings.SettingUtil;
import util.Direction;

public class Point {

    private int x;
    private int y;
// todo color???
    private final int step = SettingUtil.STEP;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        x+=step;
    }

    public void moveDown() {
        x-=step;
    }

    public void moveLeft() {
        y-=step;
    }

    public void moveRight() {
        y+=step;
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
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point that = (Point) o;
        return that.x == this.x && that.y == this.y;
    }
}
