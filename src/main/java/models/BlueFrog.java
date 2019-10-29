package models;

import models.contracts.Predator;
import util.direction.Direction;
import util.direction.DirectionObserver;

public class BlueFrog extends Frog implements DirectionObserver {

    public BlueFrog(Point position) {
        super(position);
        position.setColor((short)0, (short)0, (short)150);
    }

    @Override
    public void effect(Predator predator) {
        super.effect(predator);
        predator.addXp(2);
        predator.getDamage();
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }

}
