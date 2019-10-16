package controllers;

import models.Frog;
import models.GreenFrog;
import models.Point;
import models.Snake;
import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionSubject;
import util.direction.KeyListener;
import util.draw.DrawObserver;
import view.MainWindow;
import view.View;

import java.util.*;

import static settings.SettingUtil.MAX_X;
import static settings.SettingUtil.SCALE;

/**
 * This is the controller, which determines and handles game threads
 */
public class GameController implements Controller, MovableObserver {

    private final Random random = new Random();

    private volatile Snake snake;
//    private Thread snakeThread;
    private List<Frog> frogs;
//    private List<Thread> frogThreads;

    private int score;

    private DirectionSubject directionManager;
//    private DrawObserver drawer;

//    private final Set<Point> changes = new HashSet<>();
//    private final Set<DrawObserver> observers = new HashSet<>();

    private View view;

    public GameController(View view, DirectionSubject subject) {
        this.view = view;
//        view.launchGameField();
        directionManager = subject;
    }

    @Override
    public void startGame() {
        score = 0;

        snake = new Snake(SettingUtil.SNAKE_LENGTH);
        snake.registerObserver(this);
        snake.registerDrawObserver(view.getDrawPanel());
        directionManager.registerObserver(snake);
//        changes.addAll(snake.getBody());
        Thread snakeThread = new Thread(snake);
        snakeThread.start();

        frogs = new LinkedList<>();
//        List<Thread> frogThreads = new ArrayList<>();
        spawnFrog();
    }

    public void spawnFrog() {
        while (frogs.size() < SettingUtil.FROG_COUNT) {
            GreenFrog frog = new GreenFrog(findEmptyPoint());
            frog.setDirection(snake.getDirection());
            frog.registerObserver(this);
            frog.registerDrawObserver(view.getDrawPanel());
            frogs.add(frog);
//            changes.add(frog.getPosition());
            directionManager.registerObserver(frog);
            Thread frogThread = new Thread(frog);
            frogThread.start();
//            frogThreads.add(frogThread);
        }
        // trim array todo
    }

    private Point findEmptyPoint() {
        boolean isEmpty = true;
        Point point = new Point(random.nextInt(MAX_X) * SCALE,random.nextInt(MAX_X) * SCALE);
        while(snake.getBody().contains(point)) {
            point.moveToDirection(Direction.randomDirection(random));
        }
        return point;
    }

    @Override
    public void pause() {
        try {
            snake.wait();
            frogs.forEach(frog -> {
                try {
                    frog.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restore() {
        snake.notify();
        frogs.forEach(Object::notifyAll);
    }

    @Override
    public void cancel() {
        snake.setAlive(false);
        frogs.forEach(frog -> frog.setAlive(false));
    }

    @Override
    public synchronized void update() {
        // itself
//        if (snake.getBody().stream().anyMatch(point -> Collections.frequency(snake.getBody(), point) > 1)) {
//            cancel();
//        }
//        Set<Point> points = new HashSet<>();
//        if (snake.getBody().stream().anyMatch(points::add)) {
//            cancel();
//            return;
//        }
        if (Collections.frequency(snake.getBody(), snake.getPosition()) > 1) {
            snake.getBody().forEach(point -> System.out.println(point.getX() + " " + point.getY()));
            cancel();
            return;
        }

        // frogs
        for (ListIterator<Frog> iterator = frogs.listIterator(); iterator.hasNext(); ) {
            Frog frog = iterator.next();
            if (snake.getPosition().equals(frog.getPosition())) {
                snake.eat(frog);
                iterator.remove();
                spawnFrog();
                break;
            }
        }
//        frogs.forEach(frog -> {
//            if (frog.getPosition().equals(snake.getPosition())) {
//                snake.eat(frog);
//                System.out.println("FROG has been EATEN");
////                spawnFrog();
//            }
//        });
    }

}
