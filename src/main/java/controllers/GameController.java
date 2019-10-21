package controllers;

import models.*;
import models.Character;
import models.contracts.MovableObserver;
import settings.SettingUtil;
import util.FrogFactory;
import util.direction.Direction;
import util.direction.DirectionObserver;
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
    private Field field;

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
        field = new Field();
        snake = new Snake(SettingUtil.SNAKE_LENGTH);
        snake.registerObserver(this);
        snake.registerObserver(field);
        field.registerDrawObserver(view.getDrawPanel());
//        snake.registerDrawObserver(view.getDrawPanel());
        directionManager.registerObserver(snake);
        Thread snakeThread = new Thread(snake);
        snakeThread.start();

        spawnFrog();
        gameStarted = true;
    }

    public void spawnFrog() {
        while (frogs.size() < SettingUtil.FROG_COUNT) {
            Frog frog = FrogFactory.createFrog(findEmptyPoint());
            frog.setDirection(snake.getDirection());
            frog.registerObserver(field);
//            frog.registerDrawObserver(view.getDrawPanel());
            frogs.add(frog);
            if (frog instanceof DirectionObserver) // todo
                directionManager.registerObserver((DirectionObserver) frog);
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
    public void update(Character character) { // todo ???synchronized
//        if (character instanceof Snake) {
            if (Collections.frequency(snake.getBody(), snake.getPosition()) > 1) {
                cancel();
                return;
            }

            Optional<Frog> frogOpt = frogs.stream().filter(frog -> snake.getPosition().equals(frog.getPosition())).findFirst();
            if (frogOpt.isPresent()) {
                Frog frog = frogOpt.get();
                frogs.remove(frog);
                snake.eat(frog);
                if (snake.isAlive())
                    spawnFrog();
                else cancel();
            }
//        }
    }

}
