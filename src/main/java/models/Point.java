package models;

import settings.SettingUtil;
import util.direction.Direction;

public class Point implements Cloneable {

    private int x;
    private int y;

    private int scaledX;
    private int scaledY;

    // Color TODO encapsulate color
    private short r;
    private short g;
    private short b;

    private final int step = SettingUtil.SCALE;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;

        scaledX = x/step;
        scaledY = y/step;

        this.r = 0; // todo delete default color
        this.g = 204;
        this.b = 0;
    }

    public Point(int x, int y, short[] color) {
        this.x = x;
        this.y = y;

        scaledX = x/step;
        scaledY = y/step;

        this.r = color[0];
        this.g = color[1];
        this.b = color[2];

    }

    public Point(Point point) {
        x = point.x;
        y = point.y;

        scaledX = x/step;
        scaledY = y/step;

        r = point.r;
        g = point.g;
        b = point.b;
    }

    public void moveUp() {
        y-=step;
        if (y < 0) {
            y = SettingUtil.MAX_Y * step - step;
        }
        scaledY = y/step;
    }

    public void moveDown() {
        y+=step;
        if (y > SettingUtil.MAX_Y * step  - step) {
            y = 0;
        }
        scaledY = y/step;
    }

    public void moveLeft() {
        x-=step;
        if (x < 0) {
            x = SettingUtil.MAX_X * step - step;
        }
        scaledX = x/step;
    }

    public void moveRight() {
        x+=step;
        if (x > SettingUtil.MAX_X * step - step) {
            x = 0;
        }
        scaledX = x/step;
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
            default:
                System.out.println("no direction");
                break;
        }

    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScaledX() {
        return scaledX;
    }

    public int getScaledY() {
        return scaledY;
    }

    public void setColor(short r, short g, short b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point that = (Point) o;
        return that.x == this.x && that.y == this.y;
    }

}
