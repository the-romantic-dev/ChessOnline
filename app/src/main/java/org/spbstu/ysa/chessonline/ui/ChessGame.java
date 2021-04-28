package org.spbstu.ysa.chessonline.ui;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.Player;

import java.util.Set;

public class ChessGame extends ApplicationAdapter {
    private int startX;
    private int startY = 100;

    SpriteBatch batch;

    Player player;
    Chessboard chessboard;
    boolean isThisPlayerWhite = true;
    int fraps = 0;

    @Override
    public void create () {
        Log.d("LIFECYCLE", "CREATE");
        Gdx.graphics.setContinuousRendering(false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                chessboard.selectPiece(screenX, Gdx.graphics.getHeight() - screenY);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });

        startX = (Gdx.graphics.getWidth() - ChessboardSquare.sideLength * 8) / 2;
        batch = new SpriteBatch();
        player = new Player(!isThisPlayerWhite);
        chessboard = new Chessboard(player, batch, startX, startY);

    }

    @Override
    public void render () {
        Log.d("LIFECYCLE", "RENDER " + fraps);
        fraps++;
        Gdx.gl.glClearColor(0.4f , 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        chessboard.draw();
        batch.end();
    }

    @Override
    public void resume() {
        Log.d("LIFECYCLE", "RESUME");
        super.resume();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }


}
