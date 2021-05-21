package org.spbstu.ysa.chessonline.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.spbstu.ysa.chessonline.model.Player;
import org.spbstu.ysa.chessonline.online.Move;
import org.spbstu.ysa.chessonline.online.Room;

public class ChessGame extends ApplicationAdapter {
    private int startX;
    private int startY;

    boolean isCreating;
    boolean isGameFinished = false;
    SpriteBatch batch;
    boolean isOnline = false;

    Player player;
    Chessboard chessboard;
    boolean isThisPlayerWhite;
    int fraps = 0;

    BitmapFont header;
    BitmapFont endText;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    GlyphLayout glyph;

    DatabaseReference ref;
    GameActivity gameActivity;


    @Override
    public void create() {
        Log.d("LIFECYCLE", "CREATE");
        Gdx.graphics.setContinuousRendering(false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!isGameFinished) {
                    chessboard.setCurrentSquare(screenX, Gdx.graphics.getHeight() - screenY);
                    chessboard.tap();
                    //тут нужно написать условие Проверять поле promotedCell у Board с помощью метода
                    //getPromotedCell и если оно НЕ null то тогда запускать механизм превращения
                    // сначала нужно вызвать диол. окно и дать игроку
                    // выбрпть фигуру (Bishop, Knight, Rook, Queen)
                    // затем вызвать метод makePromotion у Board и
                    // перериссовать promotedCell (перед вызовом метода её нужно запомнить)
                    //
                    // похожую операцию нгужно сделать ниже (строка  130)
                    // там после метода makeMove()
                    if (chessboard.isMoveMaked()) {
                        pushToDB();
                    }

                } else {
                    gameActivity.backToMenu();
                }
                return true;
            }
        });

        startX = (Gdx.graphics.getWidth() - ChessboardSquare.sideLength * 8) / 2;
        startY = (Gdx.graphics.getHeight() - ChessboardSquare.sideLength * 8) / 2;
        batch = new SpriteBatch();
        if (!isOnline) {
            isThisPlayerWhite = true;
        }
        player = new Player(isThisPlayerWhite);
        chessboard = new Chessboard(player, batch, startX, startY, isOnline);
        FileHandle fontFile = Gdx.files.internal("font_1.ttf");
        Log.d("TTFTTF", fontFile.toString());
        generator = new FreeTypeFontGenerator(fontFile);
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        parameter.characters = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЧШЩЬЫЪЭЮЯ" + "абвгдеёжзиклмнопрстуфхцчшщьыъэюя";
        parameter.color = Color.RED;
        header = generator.generateFont(parameter);
        parameter.size = 100;
        parameter.color = Color.BLACK;
        endText = generator.generateFont(parameter);
        glyph = new GlyphLayout();


        if (isOnline) {
            if (!isThisPlayerWhite) player.setTurn(false);
            //вобщем нужно здесь инииализировать Listener того, изменились ли данные на бд, т.е. сделал ли ход оппонент
            //и обернуть в него нижележащий код
            //в переменные ниже записать значения с дб
            ChildEventListener listener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Room room = snapshot.getValue(Room.class);
                    int xFrom = room.getMove().getxFrom();
                    int yFrom = room.getMove().getyFrom();
                    int xTo = room.getMove().getxTo();
                    int yTo = room.getMove().getyTo();
                    //превращение пешки
                    String pawnTo = room.getMove().getPawnTo();

                    if (xFrom != 0 && yFrom != 0 && xTo != 0 && yTo != 0) {
                        ChessboardSquare squareFrom = chessboard.getSquare(xFrom, yFrom);
                        ChessboardSquare squareTo = chessboard.getSquare(xTo, yTo);
                        chessboard.setCurrentSquare(squareTo);
                        chessboard.setLastSquare(squareFrom);
                        chessboard.makeMove();
                        Gdx.graphics.requestRendering();
                        player.setTurn(true);
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            ref.addChildEventListener(listener);

        }


    }

    private void pushToDB() {
        //в этом месте надо пушить нижележащие данные на бд
        int xTo = chessboard.getCurrentSquare().getCell().getX();
        int yTo = chessboard.getCurrentSquare().getCell().getY();

        int xFrom = chessboard.getLastSquare().getCell().getX();
        int yFrom = chessboard.getLastSquare().getCell().getY();
        //пока что смена пешки не сделана, такчт пушить нечего
        String pawnTo = "";
        ref.child("move").setValue(new Move(pawnTo, xFrom, yFrom, xTo, yTo));
    }

    @Override
    public void render() {

        Log.d("LIFECYCLE", "RENDER " + fraps);
        fraps++;
        Gdx.gl.glClearColor(0.4f, 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        chessboard.draw();
        if (player.isCheck() && !player.isCheckmate()) {
            glyph.setText(header, "ШАХ");
            header.draw(batch, glyph, Gdx.graphics.getWidth() / 2 - glyph.width / 2, Gdx.graphics.getHeight() - 300);
            Log.d("CHAH", "ШАХ");
        }
        if (player.isCheckmate()) {
            isGameFinished = true;
            Pixmap finishScreenPM = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
            finishScreenPM.setColor(1, 0, 0, 0.4f);
            finishScreenPM.fill();
            glyph.setText(header, "Конец игры");
            header.draw(batch, glyph, Gdx.graphics.getWidth() / 2 - glyph.width / 2, Gdx.graphics.getHeight() - 300);
            glyph.setText(endText, "Нажмите на экран");
            endText.draw(batch, glyph, Gdx.graphics.getWidth() / 2 - glyph.width / 2, 400);
            Texture finishScreen = new Texture(finishScreenPM);
            batch.draw(finishScreen, 0, 0);
        }

        batch.end();
    }

    @Override
    public void resume() {
        Log.d("LIFECYCLE", "RESUME");
        super.resume();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public ChessGame(DatabaseReference ref, boolean isCreating, boolean color) {
        this.ref = ref;
        this.isCreating = isCreating;
        this.isThisPlayerWhite = color;
        isOnline = true;
    }

    public ChessGame(boolean isWhite, GameActivity gameActivity) {
        this.isThisPlayerWhite = isWhite;
        this.gameActivity = gameActivity;
    }
}
