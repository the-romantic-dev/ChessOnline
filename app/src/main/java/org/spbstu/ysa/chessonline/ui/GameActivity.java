package org.spbstu.ysa.chessonline.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.spbstu.ysa.chessonline.R;

import java.util.Random;


public class GameActivity extends AndroidApplication {
    DatabaseReference ref;
    //ссыль на дб

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //здесь нужно инициировать дб
        super.onCreate(savedInstanceState);
        boolean isWhite = new Random().nextBoolean();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);
        boolean isCreating = true;
        if (!isOnline) {
            initialize(new ChessGame(isWhite), config);
        } else {
            initialize(new ChessGame(ref, isCreating, isWhite), config);
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, MainActivity.class));
        finish();
    }
}