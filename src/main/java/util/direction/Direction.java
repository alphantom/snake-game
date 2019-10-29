package util.direction;

import java.util.Random;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getNextDirection(Direction direction) {
        switch(direction) {
            case UP: return RIGHT;
            case RIGHT: return DOWN;
            case DOWN: return LEFT;
            case LEFT: return UP;
            default:
                System.out.println("Can't find direction???");
                return direction;
        }
    }

    public static Direction randomDirection(Random random){
        int x = random.nextInt(Direction.class.getEnumConstants().length);
        return Direction.class.getEnumConstants()[x];
    }
}
