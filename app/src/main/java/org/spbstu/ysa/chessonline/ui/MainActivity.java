package org.spbstu.ysa.chessonline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.spbstu.ysa.chessonline.R;
import org.spbstu.ysa.chessonline.online.OnlineActivity;

public class MainActivity extends AppCompatActivity {

    Button startGameButton;
    Button exitButton;
    Button onlineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MAIN_ACTIVITY", "IS CREATED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        startGameButton.setOnClickListener(v -> {
            Intent startGameActivity = new Intent(MainActivity.this, GameActivity.class);
            startGameActivity.putExtra("isOnline", false);
            startActivity(startGameActivity);
            finish();
        });

        onlineButton.setOnClickListener(v -> {
            Intent startSettingsActivity = new Intent(MainActivity.this, OnlineActivity.class);
            startActivity(startSettingsActivity);
            finish();
        });


        exitButton.setOnClickListener(v -> finish());
    }

    private void setViews() {
        startGameButton = findViewById(R.id.start_game);
        exitButton = findViewById(R.id.exit);
        onlineButton = findViewById(R.id.online);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MAIN_ACTIVITY", "IS DESTROYED");
    }
}