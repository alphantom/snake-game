package util.direction;

public interface DirectionSubject {

    void registerObserver(DirectionObserver observer);
    void removeObserver(DirectionObserver observer);
    void notifyObservers();

}
