package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionObserver;

public class GreenFrog extends Character implements Eatable, DirectionObserver {

    protected long speed = SettingUtil.FROG_SPEED;
    protected Direction direction = Direction.RIGHT;
//    @Override
//    protected void spawn() {
//        this.position = new Point(0,0); //todo
//    }

    public GreenFrog(Point position) {
        this.position = position;
    }


    public Point getPosition() {
        return position;
    }

    @Override
    public void move() {
        System.out.println("frog moves to " + direction.name() +"!");
        lastPosition = position;
        this.position.moveToDirection(direction);
        notifyObservers();
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public void effect(Predator predator) {
        predator.growth(this.position);
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }
}
