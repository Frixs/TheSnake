package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.engine.GameEngine;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

public class Apple extends Fruit {
    public Apple(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TileType getTileType() {
        return TileType.FruitApple;
    }

    @Override
    public float getSpawnWeight() {
        return 0.1f;
    }

    @Override
    protected int getActionDuration() {
        return 0;
    }

    @Override
    public void applyAction(GameEngine gameEngine) {
        gameEngine.setSnakeTailExpandable(true);
        gameEngine.setScore(
                gameEngine.getScore() + 1
        );
    }

    @Override
    public void removeAction(GameEngine gameEngine) {
        // NoSpawn has no action.
    }
}
