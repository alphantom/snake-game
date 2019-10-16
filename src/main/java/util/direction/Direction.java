package util.direction;

import java.util.Random;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction randomDirection(Random random){
        int x = random.nextInt(Direction.class.getEnumConstants().length);
        return Direction.class.getEnumConstants()[x];
    }
}
