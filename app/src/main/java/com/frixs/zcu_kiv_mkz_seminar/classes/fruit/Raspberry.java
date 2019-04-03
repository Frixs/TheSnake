package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

/**
 * Possibility to go through Snake's tail for a few game ticks.
 */
public class Raspberry extends Fruit {
    public Raspberry(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TileType getTileType() {
        return TileType.FruitRaspberry;
    }

    @Override
    public float getSpawnWeight() {
        return 0.01f;
    }
}
