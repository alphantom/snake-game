package models;

import models.contracts.CharacterObserver;
import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.direction.DirectionSubject;
import util.draw.DrawObserver;
import util.draw.DrawSubject;

import java.util.*;

import static settings.SettingUtil.MAX_X;
import static settings.SettingUtil.MAX_Y;
import static settings.SettingUtil.SCALE;

public class Field implements DrawSubject, MovableObserver {
// characters update field with points. After trigger update, new position showed. Creates rectangle with point matrix and sends to gamepanel

    protected DrawObserver drawObserver;

    private int width = MAX_X;
    private int height = MAX_Y;
    private int scale = SCALE;
    private int score;
    private Point[][] grid;

    private final Object lockObject = new Object();

    //    private List<List<Point>> updatedGrid = new ;
    public Field() {
        grid = new Point[height][width];
    }
//
//    public void addFrog(Frog frog) {
//
//    }


    public void update(Character character) {
//        synchronized (lockObject) {
            int minx = width, miny = height, maxx = 0, maxy = 0;

            if (character instanceof Snake) {
                //        controller.snakeMoved();
                Snake snake = (Snake) character;

                for (Point point : snake.getBody()) {
                    grid[point.getScaledY()][point.getScaledX()] = point;
//                System.out.println("grid snake" +grid[point.getScaledY()][point.getScaledX()]);
                    if (point.getScaledX() > maxx) maxx = point.getScaledX();
                    if (point.getScaledX() < minx) minx = point.getScaledX();
                    if (point.getScaledY() > maxy) maxy = point.getScaledY();
                    if (point.getScaledY() < miny) miny = point.getScaledY();
                }
            } else if (character instanceof Frog) {
                Point point = character.getPosition();
//            if (character.isAlive()) {
                grid[point.getScaledY()][point.getScaledX()] = point;
//                System.out.println( "grid frog" + grid[point.getScaledY()][point.getScaledX()]);
//            } else {
//                grid[point.getScaledY()][point.getScaledX()] = null;
//            }
                minx = maxx = point.getScaledX();
                miny = maxy = point.getScaledY();
            }

            Point empty = character.getLastPosition();
            if (null != empty) {
                grid[empty.getScaledY()][empty.getScaledX()] = null;
//            System.out.println("grid empty " +grid[empty.getScaledY()][empty.getScaledX()]);

                if (empty.getScaledX() > maxx) maxx = empty.getScaledX();
                if (empty.getScaledX() < minx) minx = empty.getScaledX();
                if (empty.getScaledY() > maxy) maxy = empty.getScaledY();
                if (empty.getScaledY() < miny) miny = empty.getScaledY();
            }

//        System.out.println("grid i" + Arrays.deepToString(grid));
//            Point[][] changes = new Point[maxy + 1][maxx + 1];
//            for (int i = miny, j = 0; i <= maxy; i++, j++) {
//                changes[j] = Arrays.copyOfRange(grid[i], minx, maxx + 1);
////            System.out.println("grid i " + Arrays.deepToString(grid[i]));
////            System.out.println("copy grid i " + Arrays.deepToString(Arrays.copyOfRange(grid[i], minx, maxx + 1)));
//            }
            notifyDrawObservers(grid, minx, maxx, miny, maxy);
//        }
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

}
