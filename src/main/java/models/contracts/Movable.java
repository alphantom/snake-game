package models.contracts;

public interface Movable {
    void move();

    void registerObserver(MovableObserver observer);
    void removeObserver(MovableObserver observer);
    void notifyObservers();
}
