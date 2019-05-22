package com.frixs.zcu_kiv_mkz_seminar.engine;

import com.frixs.zcu_kiv_mkz_seminar.activities.GameActivity;
import com.frixs.zcu_kiv_mkz_seminar.classes.Coordinate;
import com.frixs.zcu_kiv_mkz_seminar.classes.Fruit;
import com.frixs.zcu_kiv_mkz_seminar.classes.fruit.Apple;
import com.frixs.zcu_kiv_mkz_seminar.enums.Direction;
import com.frixs.zcu_kiv_mkz_seminar.enums.GameState;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    public static final int mapWidth = 26;
    public static final int mapHeight = 26;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Fruit> fruit = new ArrayList<>();

    private Direction currentDirection = Direction.East;
    private Direction desiredDirection = currentDirection;

    private GameState currentGameState = GameState.Running;

    /**
     * Says if I can expand Snake's tail in the next update tick.
     */
    private boolean isSnakeTailExpandable = false;
    /**
     * Game score counter.
     */
    private int score = 0;
    /**
     * Game tick in milliseconds.
     */
    private int gameTick = 0;
    /**
     * Range in which the spawn chance is calculated.
     */
    private float fruitSpawnWeight = 0;
    /**
     * Spawnable fruit - useless dummy fruit.
     */
    private Fruit[] spawnableFruit = null;
    /**
     * Fruit actions which are executed during game ticks.
     */
    private List<Fruit> activeActions = null;

    /**
     * GameTick modifier.
     */
    private final int gameTickModifierDefVal = 0;
    private int gameTickModifier = gameTickModifierDefVal;
    /**
     * Multiply the chance of fruit spawn. Default val.: 1
     */
    private final int spawnChanceMultiplierDefVal = 1;
    private int spawnChanceMultiplier = spawnChanceMultiplierDefVal;
    /**
     * If tha snake can go over its tail or not. TRUE: Snake cannot go through, FALSE else.
     */
    private final boolean isSnakeCollidableDefVal = true;
    private boolean isSnakeCollidable = isSnakeCollidableDefVal;

    public GameEngine() {
        calculateDifficulty();
        activeActions = new ArrayList<>();
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

        // Solve actions.
        for (int i = activeActions.size() - 1; i >= 0; i--) {
            if (activeActions.get(i).getActionDurationCounter() == 0) {
                activeActions.get(i).removeAction(this);
                activeActions.remove(activeActions.get(i));
                continue;
            }

            activeActions.get(i).setActionDurationCounter(
                    activeActions.get(i).getActionDurationCounter() - 1
            );
        }

        // Check collisions.
        if (checkCollisions()) {
            currentGameState = GameState.GameOver;

        }

        solveFruitCollisions();

        calculateDifficulty();
    }

    private void calculateDifficulty() {
        switch (score) {
            case 0:
                gameTick = 600;
                break;
            case 2:
                gameTick = 550;
                break;
            case 4:
                gameTick = 500;
                break;
            case 8:
                gameTick = 450;
                break;
            case 16:
                gameTick = 400;
                break;
            case 32:
                gameTick = 366;
                break;
            case 64:
                gameTick = 333;
                break;
            case 128:
                gameTick = 300;
                break;
            case 256:
                gameTick = 200;
                break;
        }
    }

    /**
     * Set direction for the next move.
     *
     * @param newDirection The Direction.
     */
    public void setDesiredDirection(Direction newDirection) {
        desiredDirection = newDirection;
    }

    /**
     * Check if desired direction is valid.
     *
     * @return TRU if yes, NO else.
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
     *
     * @return TRUE if collides, NO else.
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
     *
     * @return TRUE if collides, NO else
     */
    private boolean checkSnakeCollisions(Coordinate c) {
        if (!isSnakeCollidable) {
            return false;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check Fruit collisions.
     *
     * @return Collider or NUL if not collides.
     */
    private Fruit checkFruitCollisions(Coordinate c) {
        for (Fruit f :
                fruit) {
            if (f.getCoordinate().equals(c)) {
                return f;
            }
        }

        return null;
    }

    /**
     * Check fruit collisions.
     */
    private void solveFruitCollisions() {
        Fruit triggeredFruit = null;
        isSnakeTailExpandable = false;

        if ((triggeredFruit = checkFruitCollisions(getSnakeHead())) != null) {
            triggeredFruit.applyAction(this);
            if (triggeredFruit.getTileType() != TileType.FruitApple) {
                activeActions.add(triggeredFruit);
            }
        }

        if (triggeredFruit != null) {
            fruit.remove(triggeredFruit);
            if (triggeredFruit.isReproductive()) {
                addFruit();
            }
            GameActivity.fruitSFX.start();
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

        for (Fruit f :
                fruit) {
            try {
                map[f.getCoordinate().getX()][f.getCoordinate().getY()] = f.getTileType();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
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

        if (isSnakeTailExpandable) {
            snake.add(new Coordinate(newX, newY));
            isSnakeTailExpandable = false;
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
        Fruit newFruit = new Apple(getFreeCoordination());
        newFruit.setReproductive(true);
        fruit.add(newFruit);

        spawnSpecialFruit();
    }

    /**
     * Spawn special fruit.
     */
    private void spawnSpecialFruit() {
        Random rn = new Random();

        if (fruitSpawnWeight <= 0f) {
            calculateSpawnChanceValues();
        }

        float chance = rn.nextFloat() * fruitSpawnWeight;
        float top = 0f;

        for (int i = 0; i < spawnableFruit.length; i++) {
            top += spawnableFruit[i].getSpawnWeight();
            if (chance < top) {
                // Spawn the item.
                Fruit newFruit = null;

                if (spawnableFruit[i].getTileType() == TileType.None) {
                    return;
                }

                try {
                    Class clazz = Class.forName(spawnableFruit[i].getClass().getName());
                    Constructor constructor = clazz.getConstructor(Coordinate.class);
                    newFruit = (Fruit) constructor.newInstance(getFreeCoordination());
                    fruit.add(newFruit);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
    }

    /**
     * Get free coordination in the game map.
     *
     * @return Free Coordination.
     */
    private Coordinate getFreeCoordination() {
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

        return coord;
    }

    /**
     * Set spawn chance range and spawnable fruit.
     */
    private void calculateSpawnChanceValues() {
        Fruit[] list = Fruit.getFruitList();
        float total = 0;

        for (int i = 0; i < list.length; i++) {
            if (list[i].getTileType() == TileType.None) {
                total += list[i].getSpawnWeight();
            } else {
                total += list[i].getSpawnWeight() * spawnChanceMultiplier;
            }
        }

        fruitSpawnWeight = total;
        spawnableFruit = list;
    }

    private Coordinate getSnakeHead() {
        return snake.get(0);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public int getGameTick() {
        return gameTick + gameTickModifier;
    }

    public int getSpawnChanceMultiplier() {
        return spawnChanceMultiplier;
    }

    public void setSpawnChanceMultiplier(int spawnChanceMultiplier) {
        this.spawnChanceMultiplier = spawnChanceMultiplier;
    }

    public boolean isSnakeCollidable() {
        return isSnakeCollidable;
    }

    public void setSnakeCollidable(boolean snakeCollidable) {
        isSnakeCollidable = snakeCollidable;
    }

    public int getGameTickModifier() {
        return gameTickModifier;
    }

    public void setGameTickModifier(int gameTickModifier) {
        this.gameTickModifier = gameTickModifier;
    }

    public boolean isSnakeTailExpandable() {
        return isSnakeTailExpandable;
    }

    public void setSnakeTailExpandable(boolean snakeTailExpandable) {
        isSnakeTailExpandable = snakeTailExpandable;
    }

    public List<Fruit> getActiveActions() {
        return activeActions;
    }
}
