package models;

import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;
import util.direction.DirectionObserver;

public class GreenFrog extends Frog implements DirectionObserver {

    protected long speed = SettingUtil.FROG_SPEED;
    protected Direction direction = Direction.RIGHT;

    public GreenFrog(Point position) {
        super(position);
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public void move() {
        lastPosition = position;
        this.position.moveToDirection(direction);
        notifyDrawObservers();
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public void effect(Predator predator) {
        super.effect(predator);
        predator.growth(this.position);
        predator.addXp(1);
        notifyDrawObservers();
    }

    @Override
    public void update(Direction direction) {
        this.direction = direction;
    }
}
