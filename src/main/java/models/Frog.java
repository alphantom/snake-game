package models;

import models.contracts.Eatable;
import models.contracts.Predator;
import settings.SettingUtil;
import util.direction.Direction;

public class Frog extends Character implements Eatable {

    private Field field;
    protected long speed = SettingUtil.FROG_SPEED;

    public Frog(Point position) {
        this.position = position;
        notifyObservers();
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public void move() {
        lastPosition = new Point(position);
        if (verifyDirection()) {
            this.position.moveToDirection(direction);
        }
        notifyObservers();
    }

    private boolean verifyDirection() {
        Point tempPoint;
        Direction tempDir = direction;
        do {
            tempPoint = new Point(position);
            tempPoint.moveToDirection(tempDir);
            try {
            if (null == field.getGrid()[tempPoint.getScaledY()][tempPoint.getScaledX()]) {
                direction = tempDir;
                return true;
            }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println(tempPoint);
                System.out.println(field.getGrid().length + " " + field.getGrid()[0].length);
            }
                tempDir = Direction.getNextDirection(tempDir);
        } while(direction != tempDir);

        return false;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public void effect(Predator predator) {
        isAlive = false;
        position.setColor((short)255, (short)255, (short)255);
        notifyObservers();
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
