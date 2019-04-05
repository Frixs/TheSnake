package com.frixs.zcu_kiv_mkz_seminar.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.frixs.zcu_kiv_mkz_seminar.R;
import com.frixs.zcu_kiv_mkz_seminar.classes.SharedData;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onClickResetScoreBTN(View view) {
        new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Do you really want to reset your high score?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settigns = getSharedPreferences(SharedData._ROOT, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settigns.edit();
                        editor.putInt(SharedData.BEST_SCORE, 0);
                        editor.apply();

                        Toast.makeText(SettingsActivity.this, "Your high score has been reset!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void onClickBackToMainBTN(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
