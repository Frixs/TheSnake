package com.frixs.zcu_kiv_mkz_seminar.engine;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.enums.Direction;
import com.frixs.zcu_kiv_mkz_seminar.enums.GameState;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    public static final int mapWidth = 26;
    public static final int mapHeight = 26;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    public GameEngine() {
    }

    /**
     * Initialize the game.
     */
    public void initGame() {
        addSnake();
        addWalls();
    }

    /**
     * Call snake update position according to the direction.
     * Check wall collisions.
     */
    public void update() {
        switch (currentDirection) {
            case North:
                updateSnake(0, -1);
                break;
            case East:
                updateSnake(1, 0);
                break;
            case South:
                updateSnake(0, 1);
                break;
            case West:
                updateSnake(-1, 0);
                break;
        }

        // Check Wall collisions.
        if (checkCollisions()) {
            currentGameState = GameState.GameOver;
        }
    }

    /**
     * Set direction for the next move.
     * @param newDirection     The Direction.
     */
    public void setDirection(Direction newDirection) {
        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1) {
            currentDirection = newDirection;
        }
    }

    /**
     * Check if Snake collides with the walls.
     *
     * @return TRUE if Snake collides with the walls, FALSE if not.
     */
    private boolean checkCollisions() {
        for (Coordinate c :
                walls) {
            if (snake.get(0).equals(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get tile type map.
     *
     * @return Tile type map.
     */
    public TileType[][] getTileMap() {
        TileType[][] map = new TileType[mapWidth][mapHeight];

        // Set default TileType to all map fields.
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                map[x][y] = TileType.None;
            }
        }

        // Set Snake tile type.
        for (Coordinate c :
                snake) {
            map[c.getX()][c.getY()] = TileType.SnakeTail;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        // Set Wall tile type.
        for (Coordinate wall :
                walls) {
            map[wall.getX()][wall.getY()] = TileType.Wall;
        }

        return map;
    }

    /**
     * Update snake position.
     *
     * @param x Coord X
     * @param y Coord Y
     */
    private void updateSnake(int x, int y) {
        for (int i = snake.size() - 1; i > 0; --i) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    /**
     * Creates Snake on the default position.
     */
    private void addSnake() {
        int xc = mapWidth / 2;
        int yc = mapHeight / 2;

        snake.clear();

        snake.add(new Coordinate(xc + 2, yc));
        snake.add(new Coordinate(xc + 1, yc));
        snake.add(new Coordinate(xc + 0, yc));
        snake.add(new Coordinate(xc - 1, yc));
    }

    /**
     * Define walls in the structure.
     */
    private void addWalls() {
        // Top & Bottom walls.
        for (int x = 0; x < mapWidth; x++) {
            walls.add(new Coordinate(x, 0));
            walls.add(new Coordinate(x, mapHeight - 1));
        }

        // Left & Right walls.
        for (int y = 0; y < mapHeight; y++) {
            walls.add(new Coordinate(0, y));
            walls.add(new Coordinate(mapWidth - 1, y));
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
