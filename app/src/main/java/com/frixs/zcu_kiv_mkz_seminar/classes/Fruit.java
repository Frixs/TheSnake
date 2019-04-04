package com.frixs.zcu_kiv_mkz_seminar.classes;

import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Apple;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Blackberry;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Coconut;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.NoSpawn;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Raspberry;
import com.frixs.zcu_kiv_mkz_seminar.engine.GameEngine;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

public abstract class Fruit {
    /** Coordinate in the game map. */
    private Coordinate coordinate;
    /** Says if another item should spawn after consuming this one. */
    private boolean isReproductive = false;
    /** GameTick counter of the action. */
    private int actionDurationCounter;

    /**
     * Get TileType of the fruit.
     * @return  TileType of the fruit.
     */
    public abstract TileType getTileType();

    /**
     * Spawn chance of the fruit in range 0-1 where 1 is 100%.
     * @return  Float value representing spawn chance.
     */
    public abstract float getSpawnWeight();

    /** Get Duration of an action. */
    protected abstract int getActionDuration();

    /**
     * Event representing the fruit.
     * @param gameEngine    GameEngine of the game.
     */
    public abstract void applyAction(GameEngine gameEngine);

    /**
     * Revert the event representing the fruit.
     * @param gameEngine    GameEngine of the game.
     */
    public abstract void removeAction(GameEngine gameEngine);

    public Fruit(Coordinate coordinate) {
        this.coordinate = coordinate;
        actionDurationCounter = getActionDuration();
    }

    /**
     * Simply, list of special spawnable fruit.
     * @return  list of all types of fruit.
     */
    public static Fruit[] getFruitList() {
        return new Fruit[] {
                new NoSpawn(new Coordinate(-1, -1)),
                new Apple(new Coordinate(-1, -1)),
                new Blackberry(new Coordinate(-1, -1)),
                new Coconut(new Coordinate(-1, -1)),
                new Raspberry(new Coordinate(-1, -1))
        };
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isReproductive() {
        return isReproductive;
    }

    public void setReproductive(boolean reproductive) {
        isReproductive = reproductive;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getActionDurationCounter() {
        return actionDurationCounter;
    }

    public void setActionDurationCounter(int actionDurationCounter) {
        this.actionDurationCounter = actionDurationCounter;
    }
}
