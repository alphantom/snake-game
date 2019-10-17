package controllers;

import models.Character;
import models.Frog;
import models.GreenFrog;
import models.Point;
import models.Snake;
import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionSubject;
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
    private volatile List<Frog> frogs = new LinkedList<>();

    private boolean paused = false;
    private boolean gameStarted = false;

    private DirectionSubject directionManager;

    private View view;

    public GameController(View view, DirectionSubject subject) {
        this.view = view;
        directionManager = subject;
    }

    @Override
    public void startGame() {
        if (gameStarted) {
            view.getDrawPanel().clear();
            cancel();
        }
        snake = new Snake(SettingUtil.SNAKE_LENGTH);
        snake.registerObserver(this);
        snake.registerDrawObserver(view.getDrawPanel());
        directionManager.registerObserver(snake);
        Thread snakeThread = new Thread(snake);
        snakeThread.start();

        spawnFrog();
        gameStarted = true;
    }

    public void spawnFrog() {
        while (frogs.size() < SettingUtil.FROG_COUNT) {
            GreenFrog frog = new GreenFrog(findEmptyPoint());
            frog.setDirection(snake.getDirection());
            frog.registerObserver(this);
            frog.registerDrawObserver(view.getDrawPanel());
            frogs.add(frog);
            directionManager.registerObserver(frog);
            Thread frogThread = new Thread(frog);
            frogThread.start();
        }

    }

    private Point findEmptyPoint() {
        Point point = new Point(random.nextInt(MAX_X) * SCALE,random.nextInt(MAX_X) * SCALE);
        while(snake.getBody().contains(point)) {
            point.moveToDirection(Direction.randomDirection(random));
        }
        return point;
    }

    @Override
    public void pause() {
        if (gameStarted) {
            if (paused) restore();
            else {
                snake.stop();
                frogs.forEach(Character::stop);
            }
            paused = !paused;
        }
    }

    public void restore() {
        paused = false;
        snake.resume();
        frogs.forEach(Character::resume);
    }

    @Override
    public void cancel() {
        if (gameStarted) {
            snake.die();
            frogs.forEach(Character::die);
            frogs = new LinkedList<>(); // todo
            gameStarted = false;
        }
    }

    @Override
    public void update() { // todo ???synchronized
        if (Collections.frequency(snake.getBody(), snake.getPosition()) > 1) {
            snake.getBody().forEach(point -> System.out.println(point.getX() + " " + point.getY()));
            cancel();
            return;
        }

        Optional<Frog> frogOpt = frogs.stream().filter(frog -> snake.getPosition().equals(frog.getPosition())).findFirst();
        if (frogOpt.isPresent()) {
            Frog frog = frogOpt.get();
            frogs.remove(frog);
            snake.eat(frog);
            spawnFrog();
        }
//        for (ListIterator<Frog> iterator = frogs.listIterator(); iterator.hasNext(); ) {
//            Frog frog = iterator.next(); //todo ccmodex (like if snake eats several frogs)
//            if (snake.getPosition().equals(frog.getPosition())) {
//                snake.eat(frog);
//                iterator.remove();
//                spawnFrog();
//                break;
//            }
//        }
    }

}
