package models;

import controllers.Controller;
import models.contracts.MovableObserver;
import util.draw.DrawObserver;
import util.draw.DrawSubject;

import static settings.SettingUtil.MAX_X;
import static settings.SettingUtil.MAX_Y;

public class Field implements DrawSubject, MovableObserver {
// characters update field with points. After trigger update, new position showed. Creates rectangle with point matrix and sends to gamepanel

    protected DrawObserver drawObserver;

    private int width = MAX_X + 1;
    private int height = MAX_Y + 1;

    private volatile Point[][] grid;
    private Controller controller;

    public Field(Controller controller) {
        this.controller = controller;
        grid = new Point[height][width];
    }

    public void update(Character character) {
        int minx = width, miny = height, maxx = 0, maxy = 0;

        if (character instanceof Snake) {
            controller.check();
            Snake snake = (Snake) character;
            for (Point point : snake.getBody()) {
                grid[point.getScaledY()][point.getScaledX()] = point;

                if (point.getScaledX() > maxx) maxx = point.getScaledX();
                if (point.getScaledX() < minx) minx = point.getScaledX();
                if (point.getScaledY() > maxy) maxy = point.getScaledY();
                if (point.getScaledY() < miny) miny = point.getScaledY();
            }
        } else if (character instanceof Frog) {
            Point point = character.getPosition();
            grid[point.getScaledY()][point.getScaledX()] = point;
            minx = maxx = point.getScaledX();
            miny = maxy = point.getScaledY();
        }

        Point empty = character.getLastPosition();
        if (null != empty) {
            grid[empty.getScaledY()][empty.getScaledX()] = null;

            if (empty.getScaledX() > maxx) maxx = empty.getScaledX();
            if (empty.getScaledX() < minx) minx = empty.getScaledX();
            if (empty.getScaledY() > maxy) maxy = empty.getScaledY();
            if (empty.getScaledY() < miny) miny = empty.getScaledY();
        }

        notifyDrawObservers(grid, minx, maxx, miny, maxy);
    }

    public void registerDrawObserver(DrawObserver observer) {
        drawObserver = observer;
    }

    public void removeDrawObserver(DrawObserver observer) {
        drawObserver = null;
    }

    public void notifyDrawObservers(Point[][] changes, int minx, int maxx, int miny, int maxy) {
        if (null != drawObserver) drawObserver.update(changes, minx, maxx, miny, maxy);
    }

    public Point[][] getGrid() {
        return grid;
    }
}
