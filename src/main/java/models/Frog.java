package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;

public class Frog extends Character implements Eatable {

    protected long speed = SettingUtil.FROG_SPEED;
//    protected Direction direction = Direction.RIGHT;

    public Frog(Point position) {
        this.position = position;
        notifyDrawObservers();
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public void move() {
        lastPosition = position;
        this.position.moveToDirection(direction);
//        notifyObservers();
        notifyDrawObservers();
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public void effect(Predator predator) {
        isAlive = false;
        this.position = null;
        notifyDrawObservers();
//        predator.growth(this.position);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Frog)) return false;
//        Frog that = (Frog) o;
//
//        return super.equals(o);
//    }


}
