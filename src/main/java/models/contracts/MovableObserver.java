package models.contracts;

import models.Point;

public interface MovableObserver {
    void update(Point lastPoint, Point newPoint);
}
