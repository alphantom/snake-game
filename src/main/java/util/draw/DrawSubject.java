package util.draw;

import models.Point;

public interface DrawSubject {
    void registerDrawObserver(DrawObserver observer);
    void removeDrawObserver(DrawObserver observer);
    void notifyDrawObservers(Point[][] changes, int minx, int maxx, int miny, int maxy);
}
