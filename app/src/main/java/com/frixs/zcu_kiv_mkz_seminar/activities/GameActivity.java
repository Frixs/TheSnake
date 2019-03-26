package com.frixs.zcu_kiv_mkz_seminar.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.frixs.zcu_kiv_mkz_seminar.R;
import com.frixs.zcu_kiv_mkz_seminar.engine.GameEngine;
import com.frixs.zcu_kiv_mkz_seminar.enums.GameState;
import com.frixs.zcu_kiv_mkz_seminar.views.GameView;

public class GameActivity extends AppCompatActivity {

    private GameEngine gameEngine;
    private GameView gameView;

    private final Handler handler = new Handler();

    /** Game tick delay */
    private long gameTick = 600;

    /** Countdown tick. */
    private final long countdownTick = 1000;
    /** Delay after loading the activity. */
    private final long activityLoadingTick = 1000;
    /** Countdown iteration. */
    private final int countdownIter = 3;
    /** Countdown iteration recorded. */
    private int countdownIterCounter = countdownIter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize the game activity.
        initialize();

        // Start the game.
        start();
    }

    /**
     * Initialize the game activity.
     */
    private void initialize() {
        // Engine initialization.
        gameEngine = new GameEngine();
        gameEngine.initGame();

        gameView = (GameView) findViewById(R.id.gameView);
        // The game view should has set the alpha already in xml file. Set it programmatically too.
        gameView.setAlpha(0.0f);

        // Update map and redraw. Show the map before starting the main handler update method.
        gameView.setViewMap(gameEngine.getTileMap());
        gameView.invalidate();

        // FadeIn the game map after loading.
        gameView.animate().alpha(1.0f);
    }

    /**
     * Start the game activity.
     */
    private void start() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startGameCountdown();
            }
        }, activityLoadingTick);
    }

    /**
     * Start the game with countdown.
     */
    private void startGameCountdown() {
        // Countdown to start of the game.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countdownIterCounter + 1 <= 0) {
                    startUpdateGameHandler();
                    countdownIterCounter = countdownIter;

                } else {
                    if (countdownIterCounter > 0) {
                        onGameCountdown("" + countdownIterCounter);
                    } else {
                        onGameCountdown("START");
                    }

                    --countdownIterCounter;

                    startGameCountdown();
                }
            }
        }, countdownTick);
    }

    /**
     * Main handler update stream.
     */
    private void startUpdateGameHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update game (1 step/tick in the game).
                gameEngine.update();

                // Keep updating if the game is running.
                if (gameEngine.getCurrentGameState() == GameState.Running) {
                    handler.postDelayed(this, gameTick);
                }

                // GameOver things to do...
                if (gameEngine.getCurrentGameState() == GameState.GameOver) {
                    onGameOver();
                }

                // Update map and redraw.
                gameView.setViewMap(gameEngine.getTileMap());
                gameView.invalidate();
            }
        }, gameTick);
    }

    /**
     * GameOver event handler.
     */
    private void onGameOver() {
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
    }

    /**
     * Game countdown event handler.
     */
    private void onGameCountdown(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void setGameTick(long gameTick) {
        this.gameTick = gameTick;
    }
}
