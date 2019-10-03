package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import util.Direction;

public class Frog extends Character implements Eatable {

    private Point position;

    @Override
    protected void spawn() {
        this.position = new Point(0,0); //todo
    }

    @Override
    public void move(Direction direction) {
        this.position.moveToDirection(direction);
    }

    @Override
    public void effect(Predator predator) {
        predator.growth(this.position);
    }
}
