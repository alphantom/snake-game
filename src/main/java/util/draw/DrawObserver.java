package util.draw;

import models.Character;
import models.Point;

public interface DrawObserver {
//    void update(Character character);

    void clear();

    void update(Point[][] changes, int minx, int maxx, int miny, int maxy);
}
