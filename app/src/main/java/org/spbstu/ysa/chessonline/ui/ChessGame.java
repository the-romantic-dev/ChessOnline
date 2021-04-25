package org.spbstu.ysa.chessonline.ui;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.pieces.Bishop;
import org.spbstu.ysa.chessonline.model.pieces.King;
import org.spbstu.ysa.chessonline.model.pieces.Knight;
import org.spbstu.ysa.chessonline.model.pieces.Pawn;
import org.spbstu.ysa.chessonline.model.pieces.Queen;
import org.spbstu.ysa.chessonline.model.pieces.Rook;

import java.util.HashMap;
import java.util.Map;

public class ChessGame extends ApplicationAdapter {
    private int startX;
    private int startY = 100;


    SpriteBatch batch;
    FileHandle chessImg;
    ChessboardSquare[][] boardArray;
    boolean isThisPlayerWhite = true;

    Map<Pieces, Pixmap> whitePiecesPM;
    Map<Pieces, Pixmap> blackPiecesPM;




    Map<Rectangle, Cell> collideToCell = ChessHelper.getCellToCollider();
    @Override
    public void create () {
        Gdx.gl.glClearColor(0.4f , 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                tapOnScreen(screenX, Gdx.graphics.getHeight() - screenY);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });

        startX = (Gdx.graphics.getWidth() - ChessboardSquare.sideLength * 8) / 2;
        batch = new SpriteBatch();

        boardArray = createBoardArray(batch, startX, startY);

        whitePiecesPM = new HashMap<>();
        blackPiecesPM = new HashMap<>();
        initPiecesPixmaps();
        startFilling();
        batch.begin();
        drawBoard(boardArray);
        batch.end();
    }

    @Override
    public void render () {
        int x = -1;
        int y = -1;
        if (Gdx.input.isTouched()) {
            x = Gdx.input.getX();
            y = Gdx.graphics.getHeight() - Gdx.input.getY();
            Log.d("Coords:", x + ";" + y);
        }
        /*ChessboardSquare test = new ChessboardSquare(batch, new Cell(0,0), 200, 200);
        test.setPiece(new Pixmap(chessImg));
        test.setSelected(true);
        test.draw();*/

    }

    @Override
    public void dispose () {
        batch.dispose();
       // img.dispose();
    }

    private void drawBoard(ChessboardSquare[][] boardArray) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardArray[i][j].draw();
            }
        }
    }
    private ChessboardSquare[][] createBoardArray(SpriteBatch batch, int startX, int startY) {
        int nextX = startX;
        int nextY = startY - ChessboardSquare.sideLength;
        ChessboardSquare[][] result = new ChessboardSquare[8][8];
        for (int i = 0; i < 8; i++) {
            nextX = startX;
            nextY += ChessboardSquare.sideLength;
            for (int j = 0; j < 8; j++) {
                result[i][j] = new ChessboardSquare(batch, new Cell(i, j), nextX, nextY);
                nextX += ChessboardSquare.sideLength;
            }
        }
        return result;
    }

    private ChessboardSquare getCurrentSquare(ChessboardSquare[][] boardArray, int x, int y) {
        for (ChessboardSquare[] line : boardArray) {
            for (ChessboardSquare square : line) {
                if (square.contains(x, y)) return square;
            }
        }
        return null;
    }

    ChessboardSquare lastSquare;
    private void tapOnScreen(int x, int y) {
        ChessboardSquare currentSquare = getCurrentSquare(boardArray, x, y);
       /* if (currentSquare == null && lastSquare != null) {
            lastSquare.unselect();
        }
        else if (currentSquare != null) {

            if (currentSquare.isSelected()) {
                currentSquare.unselect();
            }
            else {
                currentSquare.select();
                if (lastSquare != null) lastSquare.unselect();
            }
            batch.begin();
            currentSquare.draw();
            if (lastSquare != null) lastSquare.draw();
            batch.end();
            if (lastSquare != null && !lastSquare.equals(currentSquare)) lastSquare = currentSquare;
            else lastSquare = null;*/
       if (currentSquare != null) {
           if (!currentSquare.isSelected()) {
               currentSquare.select();
               batch.begin();
               currentSquare.draw();
               batch.end();
               if (lastSquare != null )  {
                   lastSquare.unselect();
                   batch.begin();
                   lastSquare.draw();
                   batch.end();
               }
               lastSquare = currentSquare;
           } else {
               currentSquare.unselect();
               batch.begin();
               currentSquare.draw();
               batch.end();
               lastSquare = null;
           }

        } else {
           if (lastSquare != null) {

               lastSquare.unselect();
               batch.begin();
               lastSquare.draw();
               batch.end();
               lastSquare = null;
           }


       }


    }

    private void initPiecesPixmaps() {
        whitePiecesPM.put(Pieces.BISHOP, getPixmapByImageName("white_bishop.png"));
        whitePiecesPM.put(Pieces.KING, getPixmapByImageName("white_king.png"));
        whitePiecesPM.put(Pieces.KNIGHT, getPixmapByImageName("white_knight.png"));
        whitePiecesPM.put(Pieces.ROOK, getPixmapByImageName("white_rook.png"));
        whitePiecesPM.put(Pieces.QUEEN, getPixmapByImageName("white_queen.png"));
        whitePiecesPM.put(Pieces.PAWN, getPixmapByImageName("white_pawn.png"));

        blackPiecesPM.put(Pieces.BISHOP, getPixmapByImageName("black_bishop.png"));
        blackPiecesPM.put(Pieces.KING, getPixmapByImageName("black_king.png"));
        blackPiecesPM.put(Pieces.KNIGHT, getPixmapByImageName("black_knight.png"));
        blackPiecesPM.put(Pieces.ROOK, getPixmapByImageName("black_rook.png"));
        blackPiecesPM.put(Pieces.QUEEN, getPixmapByImageName("black_queen.png"));
        blackPiecesPM.put(Pieces.PAWN, getPixmapByImageName("black_pawn.png"));

    }
    private Pixmap getPixmapByImageName(String name) {
        return new Pixmap(Gdx.files.internal(name));
    }

    private void startFilling () {
        boardArray[0][0].setPiece(new Rook(true), whitePiecesPM.get(Pieces.ROOK));
        boardArray[0][7].setPiece(new Rook(true), whitePiecesPM.get(Pieces.ROOK));
        boardArray[0][1].setPiece(new Knight(true), whitePiecesPM.get(Pieces.KNIGHT));
        boardArray[0][6].setPiece(new Knight(true), whitePiecesPM.get(Pieces.KNIGHT));
        boardArray[0][2].setPiece(new Bishop(true), whitePiecesPM.get(Pieces.BISHOP));
        boardArray[0][5].setPiece(new Bishop(true), whitePiecesPM.get(Pieces.BISHOP));
        boardArray[0][3].setPiece(new Queen(true), whitePiecesPM.get(Pieces.QUEEN));
        boardArray[0][4].setPiece(new King(true), whitePiecesPM.get(Pieces.KING));
        for (int i = 0; i < 8; i++) {
            boardArray[1][i].setPiece(new Pawn(true), whitePiecesPM.get(Pieces.PAWN));
        }

        boardArray[7][0].setPiece(new Rook(false), blackPiecesPM.get(Pieces.ROOK));
        boardArray[7][7].setPiece(new Rook(false), blackPiecesPM.get(Pieces.ROOK));
        boardArray[7][1].setPiece(new Knight(false), blackPiecesPM.get(Pieces.KNIGHT));
        boardArray[7][6].setPiece(new Knight(false), blackPiecesPM.get(Pieces.KNIGHT));
        boardArray[7][2].setPiece(new Bishop(false), blackPiecesPM.get(Pieces.BISHOP));
        boardArray[7][5].setPiece(new Bishop(false), blackPiecesPM.get(Pieces.BISHOP));
        boardArray[7][3].setPiece(new Queen(false), blackPiecesPM.get(Pieces.QUEEN));
        boardArray[7][4].setPiece(new King(false), blackPiecesPM.get(Pieces.KING));
        for (int i = 0; i < 8; i++) {
            boardArray[6][i].setPiece(new Pawn(false), blackPiecesPM.get(Pieces.PAWN));
        }
    }
    enum Pieces {
        BISHOP,
        KING,
        KNIGHT,
        PAWN,
        QUEEN,
        ROOK
    }
}
