package util;

import models.*;

import java.util.Random;

public abstract class FrogFactory {

    public enum FrogType {
        GREEN,
        BLUE,
        RED
    }

    public static Frog createFrog(Point position) {
        switch (randomType()) {
            case GREEN: return new GreenFrog(position);
            case BLUE: return new BlueFrog(position);
            case RED: return new RedFrog(position);
            default:
                System.out.println("There are no frogs for this type ???");
                return null;
        }
    }

    public static FrogType randomType() {
        Random random = new Random();
        int x = random.nextInt(FrogType.class.getEnumConstants().length);
        return FrogType.class.getEnumConstants()[x];
    }
}
