package com.frixs.zcu_kiv_mkz_seminar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.frixs.zcu_kiv_mkz_seminar.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onClickResetScoreBTN(View view) {
        // TODO;
    }

    public void onClickBackToMainBTN(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
