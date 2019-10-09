package util.draw;

public interface DrawSubject {
    void registerDrawObserver(DrawObserver observer);
    void removeDrawObserver(DrawObserver observer);
    void notifyDrawObservers();
}
