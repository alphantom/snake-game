package models.contracts;

import models.Character;

public interface CharacterObserver {

    void update(Character character);
}
