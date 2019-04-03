package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

/**
 * Slows the time.
 */
public class Coconut extends Fruit {
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
}
