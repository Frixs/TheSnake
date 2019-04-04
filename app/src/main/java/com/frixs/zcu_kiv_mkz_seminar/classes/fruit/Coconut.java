package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.R;
import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.engine.GameEngine;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

/**
 * Slows the time.
 */
public class Coconut extends Fruit {
    private int modifier = 150;

    public Coconut(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TileType getTileType() {
        return TileType.FruitCoconut;
    }

    @Override
    public float getSpawnWeight() {
        return 0.06f;
    }

    @Override
    protected int getActionDuration() {
        return 20;
    }

    @Override
    public void applyAction(GameEngine gameEngine) {
        gameEngine.setGameTickModifier(
                gameEngine.getGameTickModifier() + modifier
        );
    }

    @Override
    public void removeAction(GameEngine gameEngine) {
        gameEngine.setGameTickModifier(
                gameEngine.getGameTickModifier() - modifier
        );
    }
}
