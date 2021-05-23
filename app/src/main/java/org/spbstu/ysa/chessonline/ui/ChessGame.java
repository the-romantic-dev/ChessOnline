package org.spbstu.ysa.chessonline.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.Player;
import org.spbstu.ysa.chessonline.model.pieces.Bishop;
import org.spbstu.ysa.chessonline.model.pieces.King;
import org.spbstu.ysa.chessonline.model.pieces.Knight;
import org.spbstu.ysa.chessonline.model.pieces.Piece;
import org.spbstu.ysa.chessonline.model.pieces.Queen;
import org.spbstu.ysa.chessonline.model.pieces.Rook;
import org.spbstu.ysa.chessonline.online.Move;

import java.util.HashSet;
import java.util.Set;

public class ChessGame extends ApplicationAdapter {
    private int startX;
    private int startY;
    private final String RUS_LETTERS = "АБВГДЕЁЖЗИКЛМНОПРСТУФХЧШЩЬЫЪЭЮЯ" + "абвгдеёжзиклмнопрстуфхцчшщьыъэюя";

    private boolean isGameFinished = false;
    private boolean isDialog = false;
    private boolean isOnline = false;
    private boolean isThisPlayerWhite;
    private boolean isPromoting = false;

    private Player player;
    private Chessboard chessboard;
    private DatabaseReference ref;
    private ChildEventListener eventListener;
    private GameActivity gameActivity;
    private PromotingDialog dialog;

    private SpriteBatch batch;
    private BitmapFont check;
    private BitmapFont forBlack;
    private BitmapFont forWhite;
    private BitmapFont infoText;

    int turn = 0;

    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isDialog) {
                    Log.d("CHESS_PROMOTING", "Promoting dialog called");
                    choosePromotingPiece(screenX, screenY);
                } else {
                    if (!isGameFinished) {
                        chessboard.setCurrentSquare(screenX, Gdx.graphics.getHeight() - screenY);
                        chessboard.tap();
                        isDialog = isPromotingDialogCalled();

                    } else {
                        gameActivity.backToMenu();
                    }
                }
                return true;
            }
        });

        startX = (Gdx.graphics.getWidth() - ChessboardSquare.sideLength * 8) / 2;
        startY = (Gdx.graphics.getHeight() - ChessboardSquare.sideLength * 8) / 2;
        batch = new SpriteBatch();
        player = new Player(isThisPlayerWhite);
        if (!isOnline) chessboard = new Chessboard(player, batch, startX, startY, isOnline);
        else chessboard = new Chessboard(player, batch, startX, startY, isOnline, ref);
        check = createTextStyle("font_1.ttf", 200, Color.RED);
        forBlack = createTextStyle("font_1.ttf", 200, Color.BLACK);
        forWhite = createTextStyle("font_1.ttf", 200, Color.WHITE);
        infoText = createTextStyle("font_1.ttf", 100, Color.BLACK);
        dialog = new PromotingDialog(batch, (Gdx.graphics.getWidth() - 128 * 4) / 2, startY + ChessboardSquare.sideLength * 8 + 100, isThisPlayerWhite);
        if (isOnline) {
            eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("DATA_GET", "DATA IS CHANGED AND GETTED");
                    /*if (isPromoting) isPromoting = false;
                    else */
                    player.changeTurn();
                    Move move = snapshot.getValue(Move.class);

                    int xFrom = move.getxFrom();
                    int yFrom = move.getyFrom();
                    int xTo = move.getxTo();
                    int yTo = move.getyTo();
                    String pawnTo = move.getPawnTo();
                    if (player.isThisPlayersTurn() || !pawnTo.equals("")) {
                        Log.d("DATA_GET", "THIS PLAYERS TURN");
                        Log.d("PROMOTE TEST", "Opponent is turned");
                        turn++;

                        Log.d("TOTOTO", xTo + ";" + yTo);


                        Cell cellFrom = player.getBoard().getData()[yFrom][xFrom];
                        Cell cellTo = player.getBoard().getData()[yTo][xTo];

                        Set<Cell> cellSet = new HashSet<>();
                        cellSet.add(cellTo);
                        player.getBoard().setAllowedMoves(cellSet);
                        player.getBoard().setCurrentCell(cellFrom);
                        Set<Cell> changed = player.putPiece(cellTo);
                        if (!pawnTo.equals("")) {
                            Log.d("CHESS_PROMOTING", "Promoting getted from DB");
                            Log.d("PROMOTE TEST", "Promoting getted from DB");
                            player.getBoard().setPromotedCell(cellTo);
                            if (player.isThisPlayersTurn())
                                player.getBoard().makePromotion(createPromotedPiece(pawnTo, player.isWhite()));
                            else
                                player.getBoard().makePromotion(createPromotedPiece(pawnTo, !player.isWhite()));
                            player.changeTurn();
                        }
                        if (changed != null && !changed.isEmpty()) {
                            for (Cell cell :
                                    changed) {
                                chessboard.redrawSquare(chessboard.getSquare(cell.getX(), cell.getY()));
                                //Log.d("REDRAWED_SQUARES", "Turn " + turn + ": " + chessboard.getSquare(cell.getX(), cell.getY()));
                            }
                        }
                        Gdx.graphics.requestRendering();
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
            /*if (!player.isThisPlayersTurn())*/
            ref.addChildEventListener(eventListener);


        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        chessboard.draw();
        if (player.isCheck() && !player.isCheckmate()) {
            drawText("Шах", check, HorizontalAlignment.CENTER, VerticalAlignment.TOP, 0, 0);
            if (player.isWhite()) {
                drawText("белым", forWhite, HorizontalAlignment.CENTER, VerticalAlignment.TOP, 0, 150);
            } else {
                drawText("чёрным", forBlack, HorizontalAlignment.CENTER, VerticalAlignment.TOP, 0, 150);
            }

        }
        if (player.isCheckmate()) {
            isGameFinished = true;
            Pixmap finishScreenPM = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
            finishScreenPM.setColor(1, 0, 0, 0.4f);
            finishScreenPM.fill();
            drawText("Конец игры", check, HorizontalAlignment.CENTER, VerticalAlignment.TOP, 0, 100);
            drawText("Нажмите на экран", infoText, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, 0, 400);
            Texture finishScreen = new Texture(finishScreenPM);
            batch.draw(finishScreen, 0, 0);
        }
        if (isDialog) {
            dialog.draw();
            Log.d("CHESS_PROMOTING", "Promoting dialog drawed");
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        check.dispose();
        infoText.dispose();
        chessboard.dispose();
        dialog.dispose();
    }

    private boolean isPromotingDialogCalled() {
        Cell promotedCell = player.getBoard().getPromotedCell();
        if (promotedCell != null) {
            if (isOnline) dialog.setWhite(player.isWhite());
            else dialog.setWhite(!player.isWhite());
            return true;
        }
        return false;
    }

    public ChessGame(DatabaseReference ref, boolean isCreating, boolean isWhite) {
        this.ref = ref;
        this.isThisPlayerWhite = isWhite;
        isOnline = true;
    }

    public ChessGame(boolean isWhite, GameActivity gameActivity) {
        this.isThisPlayerWhite = isWhite;
        this.gameActivity = gameActivity;
    }

    private void choosePromotingPiece(int screenX, int screenY) {

        isPromoting = true;
        Piece choosedPiece = dialog.getPiece(screenX, Gdx.graphics.getHeight() - screenY);

        if (choosedPiece != null) {
            isDialog = false;

            chessboard.pushToDB(choosedPiece.getName());
            player.getBoard().makePromotion(choosedPiece);
            Log.d("CHESS_PROMOTING", "Promotion maked");
            chessboard.redrawSquare(chessboard.getCurrentSquare());
        }
    }

    private Piece createPromotedPiece(String name, boolean isWhite) {
        Log.d("promoted_piece", name + (isWhite ? " white" : " black"));
        switch (name) {
            case "Knight":
                return new Knight(isWhite);
            case "Bishop":
                return new Bishop(isWhite);
            case "Queen":
                return new Queen(isWhite);
            case "Rook":
                return new Rook(isWhite);
        }
        return new King(isWhite);
    }

    private BitmapFont createTextStyle(String fontFileName, int textSize, Color textColor) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFileName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = RUS_LETTERS;

        parameter.size = textSize;
        parameter.color = textColor;
        BitmapFont style = generator.generateFont(parameter);
        generator.dispose();
        return style;
    }

    private void drawText(String text, BitmapFont textStyle, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, float marginX, float marginY) {
        GlyphLayout glyphLayout = new GlyphLayout(textStyle, text);
        float x = marginX;
        float y = marginY;
        switch (horizontalAlignment) {
            case CENTER:
                x = (Gdx.graphics.getWidth() - glyphLayout.width) / 2;
                break;
            case LEFT:
                x = marginX;
                break;
            case RIGHT:
                x = Gdx.graphics.getWidth() - glyphLayout.width - marginX;
                break;
        }
        switch (verticalAlignment) {
            case CENTER:
                y = (Gdx.graphics.getHeight() - glyphLayout.height) / 2;
                break;
            case TOP:
                y = Gdx.graphics.getHeight() - glyphLayout.height - marginY;
                break;
            case BOTTOM:
                y = marginY;
                break;
        }
        textStyle.draw(batch, glyphLayout, x, y);
    }

    enum HorizontalAlignment {
        CENTER,
        RIGHT,
        LEFT
    }

    enum VerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }
}
