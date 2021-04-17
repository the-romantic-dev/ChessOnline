package org.spbstu.ysa.chessonline.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.spbstu.ysa.chessonline.R;

public class MainActivity extends AppCompatActivity {

    Button startGameButton;
    Button settingsButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGameActivity);
                finish();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setViews () {
        startGameButton = findViewById(R.id.start_game);
        settingsButton = findViewById(R.id.settings);
        exitButton = findViewById(R.id.exit);
    }
}