package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.engine.GameEngine;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

/**
 * This class defines spawn chance of nothing.
 */
public class NoSpawn extends Fruit {
    public NoSpawn(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TileType getTileType() {
        return TileType.None;
    }

    @Override
    public float getSpawnWeight() {
        return 0.9f;
    }

    @Override
    protected int getActionDuration() {
        return 0;
    }

    @Override
    public void applyAction(GameEngine gameEngine) {
        // NoSpawn has no action.
    }

    @Override
    public void removeAction(GameEngine gameEngine) {
        // NoSpawn has no action.
    }
}
