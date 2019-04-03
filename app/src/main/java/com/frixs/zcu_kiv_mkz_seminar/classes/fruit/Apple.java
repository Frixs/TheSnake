package com.frixs.zcu_kiv_mkz_seminar.classes.fruit;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
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
}
