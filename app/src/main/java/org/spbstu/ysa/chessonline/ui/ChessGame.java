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
import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChessGame extends ApplicationAdapter {
    private int startX;
    private int startY = 100;

    SpriteBatch batch;
    ChessboardSquare[][] boardArray;
    Pixmap boardPixmap;
    boolean isThisPlayerWhite = true;

    Map<Pieces, Pixmap> whitePiecesPM;
    Map<Pieces, Pixmap> blackPiecesPM;

    int fraps = 0;

    ChessboardSquare currentSquare;
    ChessboardSquare lastSquare;
    @Override
    public void create () {
        Log.d("LIFECYCLE", "CREATE");
        Gdx.graphics.setContinuousRendering(false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                selectPiece(screenX, Gdx.graphics.getHeight() - screenY);
                //нужна корректная реализация доступных ходов в модели
                /*if (currentSquare != null) {
                    selectAllowedSquares(getAllowedSquares(currentSquare.getCell()));
                }*/
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
        boardPixmap = getBoardPixmap(boardArray);

    }

    @Override
    public void render () {
        Log.d("LIFECYCLE", "RENDER " + fraps);
        fraps++;
        Gdx.gl.glClearColor(0.4f , 0.6f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(new Texture(boardPixmap), startX, startY);
        batch.end();


/*        if (Gdx.input.justTouched()) {
            x = Gdx.input.getX();
            y = Gdx.graphics.getHeight() - Gdx.input.getY();
            Log.d("Coords:", x + ";" + y);
        }*/
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

    private Pixmap getBoardPixmap(ChessboardSquare[][] boardArray) {
        Pixmap boardPixmap = new Pixmap(ChessboardSquare.sideLength * 8, ChessboardSquare.sideLength * 8, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardPixmap.drawPixmap(boardArray[i][j].getPixmap(), j * ChessboardSquare.sideLength, (7- i) * ChessboardSquare.sideLength);
            }
        }
        return boardPixmap;
    }

    private Pixmap changeSquarePixmap(Pixmap board, ChessboardSquare square) {
        int x = square.getCell().getY();
        int y = square.getCell().getX();
        board.drawPixmap(square.getPixmap(), x * ChessboardSquare.sideLength, (7 -y) * ChessboardSquare.sideLength);
        return board;
    }
    private ChessboardSquare[][] createBoardArray(SpriteBatch batch, int startX, int startY) {
        int nextX;
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

    private ChessboardSquare[] getAllowedSquares(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        Set<Pair<Integer, Integer>> pairs = cell.getPiece().getAllowedCells(x, y);
        ChessboardSquare[] result = new ChessboardSquare[pairs.size()];
        int k = 0;
        for (Pair<Integer, Integer> pair : pairs) {
            result[k] = boardArray[pair.getX()][pair.getY()];
            k++;
        }
        return result;
    }

    private void selectAllowedSquares(ChessboardSquare[] squares) {
        for (ChessboardSquare square: squares) {
            square.select();
        }
    }
    private void unselectAllowedSquares(ChessboardSquare[] squares) {
        for (ChessboardSquare square: squares) {
            square.unselect();
        }
    }

    private void selectPiece(int x, int y) {
        currentSquare = getCurrentSquare(boardArray, x, y);
        if (currentSquare != null && currentSquare.getCell().getPiece() == null) return;
       if (currentSquare != null) {
           if (!currentSquare.isSelected()) {
               currentSquare.select();
               changeSquarePixmap(boardPixmap, currentSquare);
               if (lastSquare != null )  {
                   lastSquare.unselect();
                   changeSquarePixmap(boardPixmap, lastSquare);
               }
               lastSquare = currentSquare;
           } else {
               currentSquare.unselect();
               changeSquarePixmap(boardPixmap, currentSquare);
               lastSquare = null;
           }

        } else {
           if (lastSquare != null) {
               lastSquare.unselect();
               changeSquarePixmap(boardPixmap, lastSquare);
               lastSquare = null;
           }
       }
       //return currentSquare != null ? currentSquare.getCell() : null;
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
