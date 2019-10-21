package models;

import models.contracts.Predator;

public class RedFrog extends Frog{

    public RedFrog(Point position) {
        super(position);
        position.setColor((short)155, (short)0, (short)0);
    }

    @Override
    public void effect(Predator predator) {
        super.effect(predator);
        predator.getKilled();
    }
}
