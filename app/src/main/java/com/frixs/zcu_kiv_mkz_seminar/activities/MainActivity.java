package com.frixs.zcu_kiv_mkz_seminar.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.frixs.zcu_kiv_mkz_seminar.R;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundMusic = MediaPlayer.create(this, R.raw.menu_music);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    public void startTheGame(View v) {
        backgroundMusic.stop();
        backgroundMusic.release();
        backgroundMusic = null;
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    public void onClickPlayBTN(View view) {
        startTheGame(null);
    }

    public void onClickSettingsBTN(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void onClickQuitBTN(View view) {
        finish();
        moveTaskToBack(true);
        System.exit(0);
    }
}
