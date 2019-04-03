package com.frixs.zcu_kiv_mkz_seminar.classes;

import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Apple;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Blackberry;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Coconut;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.NoSpawn;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Raspberry;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

public abstract class Fruit {
    private Coordinate coordinate;

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

    public Fruit(Coordinate coordinate) {
        this.coordinate = coordinate;
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

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
