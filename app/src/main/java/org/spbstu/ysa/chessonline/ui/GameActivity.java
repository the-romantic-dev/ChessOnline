package org.spbstu.ysa.chessonline.ui;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


public class GameActivity extends AndroidApplication {
    DatabaseReference ref;
    //ссыль на бд

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ref = FirebaseDatabase.getInstance().getReference("games");
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);

        if (!isOnline) {
            initialize(new ChessGame(true, GameActivity.this), config);
        } else {
            boolean isHost = getIntent().getBooleanExtra("isHost", false);
            boolean creatorIsWhite = getIntent().getBooleanExtra("creatorIsWhite", false);
            String roomKey = getIntent().getStringExtra("roomKey");
            if (isHost)
                initialize(new ChessGame(ref.child(roomKey), isHost, creatorIsWhite), config);
            else {
                initialize(new ChessGame(ref.child(roomKey), isHost, !creatorIsWhite), config);
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, MainActivity.class));
        finish();
    }

    public void backToMenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}