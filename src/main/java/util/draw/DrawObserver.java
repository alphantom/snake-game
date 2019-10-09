package util.draw;

import models.Point;

import java.util.Set;

public interface DrawObserver {
    void update(Set<Point> changes);
}
