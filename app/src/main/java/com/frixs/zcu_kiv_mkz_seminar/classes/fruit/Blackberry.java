package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

/**
 * Multiply fruit spawn chance.
 */
public class Blackberry extends Fruit {
    public Blackberry(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TileType getTileType() {
        return TileType.FruitBlackberry;
    }

    @Override
    public float getSpawnWeight() {
        return 0.03f;
    }
}
