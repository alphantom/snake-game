package util.draw;

import models.Character;
import models.Point;

import java.util.Set;

public interface DrawObserver {
    void update(Character character);
}
