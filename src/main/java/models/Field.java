package models;

import util.direction.DirectionSubject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Field {

    private volatile Snake snake;
    private List<Frog> frogs;

    private int score;

    public Field(Snake snake) {
        score = 0;
        this.snake = snake;
        frogs = new LinkedList<>();
    }

    public void addFrog(Frog frog) {

    }


}
