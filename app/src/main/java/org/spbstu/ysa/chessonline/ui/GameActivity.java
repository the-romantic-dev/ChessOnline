package org.spbstu.ysa.chessonline.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.spbstu.ysa.chessonline.R;


public class GameActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new ChessGame(), config);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, MainActivity.class));
        finish();
    }
}