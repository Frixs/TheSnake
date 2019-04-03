package com.frixs.zcu_kiv_mkz_seminar.engine;

import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.enums.Direction;
import com.frixs.zcu_kiv_mkz_seminar.enums.GameState;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final int mapWidth = 26;
    public static final int mapHeight = 26;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> fruit = new ArrayList<>();

    private Direction currentDirection = Direction.East;
    private Direction desiredDirection = currentDirection;

    private GameState currentGameState = GameState.Running;

    /** says if I can expand Snake's tail in the next update tick. */
    private boolean expandSnakeTail = false;

    public GameEngine() {
    }

    /**
     * Initialize the game.
     */
    public void initGame() {
        addSnake();
        addWalls();

        addFruit();
    }

    /**
     * Call snake update position according to the direction.
     * Check wall collisions.
     */
    public void update() {
        if (checkDirection()) {
            currentDirection = desiredDirection;
        }

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

        // Check collisions.
        if (checkCollisions()) {
            currentGameState = GameState.GameOver;
        }

        solveFruitCollisions();
    }

    /**
     * Set direction for the next move.
     * @param newDirection     The Direction.
     */
    public void setDesiredDirection(Direction newDirection) {
        desiredDirection = newDirection;
    }

    /**
     * Check if desired direction is valid.
     * @return  TRU if yes, NO else.
     */
    private boolean checkDirection() {
        if (Math.abs(desiredDirection.ordinal() - currentDirection.ordinal()) % 2 == 1) {
            return true;
        }

        return false;
    }

    /**
     * Check if Snake collides with the walls or itself.
     *
     * @return TRUE if Snake collides, FALSE if not.
     */
    private boolean checkCollisions() {
        return checkWallCollisions(getSnakeHead()) || checkSnakeCollisions(getSnakeHead());
    }

    /**
     * Check Wall collisions.
     * @return  TRUE if collides, NO else.
     */
    private boolean checkWallCollisions(Coordinate c) {
        for (Coordinate coord :
                walls) {
            if (coord.equals(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check Snake collisions.
     * @return  TRUE if collides, NO else
     */
    private boolean checkSnakeCollisions(Coordinate c) {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check Fruit collisions.
     * @return  Collider or NUL if not collides.
     */
    private Coordinate checkFruitCollisions(Coordinate c) {
        for (Coordinate f :
                fruit) {
            if (f.equals(c)) {
                return f;
            }
        }

        return null;
    }

    /**
     * Check fruit collisions.
     */
    private void solveFruitCollisions() {
        Coordinate fruitToRemove = null;
        expandSnakeTail = false;

        if ((fruitToRemove = checkFruitCollisions(getSnakeHead())) != null) {
            expandSnakeTail = true;
        }

        if (fruitToRemove != null) {
            fruit.remove(fruitToRemove);
            addFruit();
        }
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
//TODO
        for (Coordinate c :
                fruit) {
            map[c.getX()][c.getY()] = TileType.Apple;
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
        int newX = snake.get(snake.size() - 1).getX();
        int newY = snake.get(snake.size() - 1).getY();

        for (int i = snake.size() - 1; i > 0; --i) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        if (expandSnakeTail) {
            snake.add(new Coordinate(newX, newY));
            expandSnakeTail = false;
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

    /**
     * Add fruit to the game.
     */
    private void addFruit() {
        Coordinate coord = null;
        boolean unableToSpawn;
        Random rn = new Random();

        do {
            unableToSpawn = false;

            int x = rn.nextInt(mapWidth - 2) + 1;
            int y = rn.nextInt(mapHeight - 2) + 1;

            coord = new Coordinate(x, y);

            if (checkSnakeCollisions(coord)) {
                unableToSpawn = true;
            }

            if (checkFruitCollisions(coord) != null) {
                unableToSpawn = true;
            }
        } while (unableToSpawn);

        fruit.add(coord);
    }

    private Coordinate getSnakeHead() {
        return snake.get(0);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
