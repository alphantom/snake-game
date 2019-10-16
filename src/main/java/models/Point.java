package models;

import settings.SettingUtil;
import util.direction.Direction;

public class Point {

    private int x;
    private int y;

    // Color TODO encapsulate color
    private int r;
    private int g;
    private int b;

    private final int step = SettingUtil.SCALE;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 0;
        this.g = 204;
        this.b = 0;
    }

    public Point(int x, int y, int[] color) {
        this.x = x;
        this.y = y;
        this.r = color[0];
        this.g = color[1];
        this.b = color[2];

    }
    public void moveUp() {
        y-=step;
        if (y < step) {
            y = SettingUtil.MAX_Y * step - step;
        }
    }

    public void moveDown() {
        y+=step;
        if (y > SettingUtil.MAX_Y * step  - step) {
            y = step;
        }
    }

    public void moveLeft() {
        x-=step;
        if (x < 0) {
            x = SettingUtil.MAX_X * step - step;
        }
    }

    public void moveRight() {
        x+=step;
        if (x > SettingUtil.MAX_X * step - step) {
            x = 0;
        }
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point that = (Point) o;
        return that.x == this.x && that.y == this.y;
    }

}
