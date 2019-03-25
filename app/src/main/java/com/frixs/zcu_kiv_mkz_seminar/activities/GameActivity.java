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
    private final long updateDelay = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        gameView = (GameView) findViewById(R.id.gameView);

        startUpdatehandler();
    }

    private void startUpdatehandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.update();

                if (gameEngine.getCurrentGameState() == GameState.Running) {
                    handler.postDelayed(this, updateDelay);
                }

                if (gameEngine.getCurrentGameState() == GameState.GameOver) {
                    onGameOver();
                }

                gameView.setViewMap(gameEngine.getMap());
                gameView.invalidate();
            }
        }, updateDelay);
    }

    private void onGameOver() {
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
    }
}
