package controllers;

import models.Frog;
import models.GreenFrog;
import models.Point;
import models.Snake;
import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.direction.DirectionSubject;
import util.direction.KeyListener;
import util.draw.DrawObserver;
import view.MainWindow;
import view.View;

import java.util.*;

/**
 * This is the controller, which determines and handles game threads
 */
public class GameController implements Controller, MovableObserver {

    private volatile Snake snake;
    private Thread snakeThread;
    private List<Frog> frogs;
    private List<Thread> frogThreads;

    private DirectionSubject directionManager;
//    private DrawObserver drawer;

    private final Set<Point> changes = new HashSet<>();
//    private final Set<DrawObserver> observers = new HashSet<>();

    private View view;

    public GameController(View view, DirectionSubject subject) {
        this.view = view;
//        view.launch();
        directionManager = subject;
    }

    @Override
    public void startGame() {
//        directionManager = directionSubject;
//        this.drawer = drawer;
        snake = new Snake(SettingUtil.SNAKE_LENGTH);
        snake.registerObserver(this);
        snake.registerDrawObserver(view.getDrawPanel());
        directionManager.registerObserver(snake);
        changes.addAll(snake.getBody());
        snakeThread = new Thread(snake);
        snakeThread.start();

        frogs = new ArrayList<>();
        frogThreads = new ArrayList<>();
        spawnFrog();
    }

    public void spawnFrog() {
        while (frogs.size() < SettingUtil.FROG_COUNT) {
            GreenFrog frog = new GreenFrog(new Point(3 * SettingUtil.SCALE,3 * SettingUtil.SCALE));
            frog.registerObserver(this);
            frog.registerDrawObserver(view.getDrawPanel());
            frogs.add(frog);
            changes.add(frog.getPosition());
            directionManager.registerObserver(frog);
            Thread frogThread = new Thread(frog);
            frogThread.start();
            frogThreads.add(frogThread);
        }
        // trim array todo
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
    public void update() {
        // itself
        // frogs
        System.out.println("snake moved");
        for (Iterator<Frog> iterator = frogs.iterator(); iterator.hasNext(); ) {
            Frog frog = iterator.next();
            if (frog.getPosition().equals(snake.getPosition())) {
                iterator.remove();
                snake.eat(frog);
                System.out.println("FROG has been EATEN");
                spawnFrog();

            }
        }
//            if (iterator.next() > 10)
//                iterator.remove();
//        frogs.forEach(frog -> {
//
//        });
    }

}
