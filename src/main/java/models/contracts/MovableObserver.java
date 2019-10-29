package models.contracts;

import models.Character;
import models.Point;

public interface MovableObserver {
    void update(Character character);
//    void update(Movable movable);
}
