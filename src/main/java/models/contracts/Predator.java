package models.contracts;

import models.Point;

public interface Predator {
    void eat(Eatable eatable);

    void growth(Point point);

    void getDamage();
}
