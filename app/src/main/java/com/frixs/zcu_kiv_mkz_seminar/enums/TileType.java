package com.frixs.zcu_kiv_mkz_seminar.enums;

import com.frixs.zcu_kiv_mkz_seminar.R;

public enum TileType {
    None(R.color.colorTileNone),
    Wall(R.color.colorTileWall),
    SnakeHead(R.color.colorSnakeHead),
    SnakeTail(R.color.colorSnakeTail),

    FruitApple(R.color.colorFruitApple),
    FruitRaspberry(R.color.colorFruitRaspberry),
    FruitBlackberry(R.color.colorFruitBlackberry),
    FruitCoconut(R.color.colorFruitCoconut);

    private final int color;

    private TileType(final int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
