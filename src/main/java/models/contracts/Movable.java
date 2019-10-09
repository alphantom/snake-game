package models.contracts;

import util.direction.Direction;
import util.direction.DirectionObserver;

public interface Movable {
    void move(Direction direction);

    void registerObserver(MovableObserver observer);
    void removeObserver(MovableObserver observer);
    void notifyObservers();
}
