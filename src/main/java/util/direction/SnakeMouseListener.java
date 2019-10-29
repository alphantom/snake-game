package util.direction;

import util.draw.DrawObserver;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class SnakeMouseListener implements MouseListener, DirectionSubject {

    private final Set<DirectionObserver> observers = new HashSet<>();
    private Direction currentDirection = Direction.RIGHT;
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case MouseEvent.BUTTON1:
                System.out.println("Click left");
                turnLeft();
                break;
            case MouseEvent.BUTTON3:
                System.out.println("Click right");
                turnRight();
                break;
        }
    }

    private void turnLeft() {
        switch(currentDirection) {
            case UP:
                currentDirection = Direction.LEFT;
                break;
            case DOWN:
                currentDirection = Direction.RIGHT;
                break;
            case LEFT:
                currentDirection = Direction.DOWN;
                break;
            case RIGHT:
                currentDirection = Direction.UP;
                break;
        }
        notifyObservers();
    }

    private void turnRight() {
        switch(currentDirection) {
            case UP:
                currentDirection = Direction.RIGHT;
                break;
            case DOWN:
                currentDirection = Direction.LEFT;
                break;
            case LEFT:
                currentDirection = Direction.UP;
                break;
            case RIGHT:
                currentDirection = Direction.DOWN;
                break;
        }
        notifyObservers();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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
            observer.update(currentDirection);
        }
    }
}
