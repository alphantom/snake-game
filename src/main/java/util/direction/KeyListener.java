package util.direction;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyListener implements java.awt.event.KeyListener, DirectionSubject {

    private final Set<DirectionObserver> observers = new HashSet<>();
    private Direction direction = Direction.RIGHT;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
        }
        notifyObservers();
    }

    @Override
    public void registerObserver(DirectionObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(DirectionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DirectionObserver observer : observers) {
            observer.update(direction);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
