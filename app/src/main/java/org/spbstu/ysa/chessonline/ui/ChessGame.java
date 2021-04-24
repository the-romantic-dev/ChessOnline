package org.spbstu.ysa.chessonline.ui;

import android.os.Debug;
import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ChessGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Color boardColor = new Color(0,0,0,1);
    Color chessmenColor;
    ShapeRenderer renderer;
    public int x;
    public int y;
    @Override
    public void create () {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        //img = new Texture("badlogic.jpg");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.4f , 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (Gdx.input.isTouched()) {
            x = Gdx.input.getX();
            y = Gdx.input.getY();
            Log.d("Coords:", x + ";" + y);
        }

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(boardColor);
        renderer.rect(0,0, 150, 150);
        renderer.end();
        //batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
       // img.dispose();
    }
}
