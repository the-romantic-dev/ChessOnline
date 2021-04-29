package org.spbstu.ysa.chessonline.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Графическая обертка для Player
public class Chessboard {
    int startX;
    int startY;
    Player player;
    ChessboardSquare[][] squaresArray;
    Pixmap overallPixmap;
    SpriteBatch batch;

    ChessboardSquare currentSquare;
    ChessboardSquare[] currentAllowedSquares;

    ChessboardSquare lastSquare;
    ChessboardSquare[] lastAllowedSquares;


    Map<Pieces, Pixmap> whitePiecesPM;
    Map<Pieces, Pixmap> blackPiecesPM;

    public Chessboard (Player player, SpriteBatch batch, int startX, int startY) {
        this.batch = batch;
        this.player = player;
        this.startX = startX;
        this.startY = startY;
        whitePiecesPM = new HashMap<>();
        blackPiecesPM = new HashMap<>();

        initPiecesPixmaps();

        squaresArray = covertCellsToSquares();

        overallPixmap = makeStartPixmap();
    }

    private ChessboardSquare[][] covertCellsToSquares() {
        int nextX;
        int nextY = startY - ChessboardSquare.sideLength;
        ChessboardSquare[][] result = new ChessboardSquare[8][8];
        Cell addedCell;
        for (int i = 0; i < 8; i++) {
            nextX = startX;
            nextY += ChessboardSquare.sideLength;
            for (int j = 0; j < 8; j++) {
                addedCell = player.getBoard()[i][j];
                if (addedCell.getPiece() != null) result[i][j] = new ChessboardSquare(addedCell, nextX, nextY, getPiecePixmap(addedCell.getPiece().getName(), addedCell.getPiece().isWhite()));
                else result[i][j] = new ChessboardSquare(addedCell, nextX, nextY);
                nextX += ChessboardSquare.sideLength;
            }
        }
        return result;
    }

    private Pixmap makeStartPixmap() {
        Pixmap pixmap = new Pixmap(ChessboardSquare.sideLength * 8, ChessboardSquare.sideLength * 8, Pixmap.Format.RGBA8888);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pixmap.drawPixmap(squaresArray[i][j].getPixmap(), j * ChessboardSquare.sideLength, (7- i) * ChessboardSquare.sideLength);
            }
        }
        return pixmap;
    }

    private Pixmap getPiecePixmap(String name, boolean color) {
        Map<Pieces, Pixmap> pixmaps;
        if (color == true) pixmaps = whitePiecesPM;
        else pixmaps = blackPiecesPM;
        switch (name) {
            case "King": return pixmaps.get(Pieces.KING);
            case "Knight": return pixmaps.get(Pieces.KNIGHT);
            case "Rook": return pixmaps.get(Pieces.ROOK);
            case "Queen": return pixmaps.get(Pieces.QUEEN);
            case "Pawn": return pixmaps.get(Pieces.PAWN);
            case "Bishop": return pixmaps.get(Pieces.BISHOP);
        }
        return null;
    }

    public ChessboardSquare[][] getSquaresArray() {
        return squaresArray;
    }

    public void setSquaresArray(ChessboardSquare[][] squaresArray) {
        this.squaresArray = squaresArray;
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

    public Pixmap getOverallPixmap() {
        return overallPixmap;
    }

    public void redrawBoard(ChessboardSquare square) {
        int x = square.getCell().getX();
        int y = square.getCell().getY();
        overallPixmap.drawPixmap(square.getPixmap(), x * ChessboardSquare.sideLength, (7 - y) * ChessboardSquare.sideLength);
    }

    public void draw() {
        batch.draw(new Texture(overallPixmap), startX, startY);
    }

    public void selectPiece(int x, int y) {
        currentSquare = getCurrentSquare(squaresArray, x, y);
        currentAllowedSquares = getAllowedSquares(currentSquare);
        if (currentSquare != null && currentSquare.getCell().getPiece() == null) return;
        if (currentSquare != null && currentSquare.getCell().getPiece() == null) return;
        if (currentSquare != null) {
            if (!currentSquare.isSelected()) {
                selecetOrUnselectPieceAndMoves(currentSquare, currentAllowedSquares, true);
                if (lastSquare != null && lastAllowedSquares != null)  {
                    selecetOrUnselectPieceAndMoves(lastSquare, lastAllowedSquares, false);
                }
                lastSquare = currentSquare;
                lastAllowedSquares = currentAllowedSquares;
            } else {
                selecetOrUnselectPieceAndMoves(currentSquare, currentAllowedSquares, false);
                lastSquare = null;
                lastAllowedSquares = null;
            }

        } else {
            if (lastSquare != null && lastAllowedSquares != null) {
                selecetOrUnselectPieceAndMoves(lastSquare, lastAllowedSquares, false);
                lastSquare = null;
                lastAllowedSquares = null;
            }
        }
    }

    private void selecetOrUnselectPieceAndMoves(ChessboardSquare square, ChessboardSquare[] allowedSquares, boolean doSelect) {
        if (doSelect) square.select();
        else square.unselect();

        redrawBoard(square);
        selectOrUnselectAllowedSquares(allowedSquares, doSelect);

    }
    public ChessboardSquare[] getAllowedSquares(ChessboardSquare square) {
        if (square == null) return new ChessboardSquare[0];
        Set<Cell> cells = player.capturePiece(square.getCell());
        ChessboardSquare[] result = new ChessboardSquare[cells.size()];
        int k = 0;
        for (Cell cell : cells) {
            result[k] = squaresArray[cell.getY()][cell.getX()];
            k++;
        }
        return result;
    }

    private void selectOrUnselectAllowedSquares(ChessboardSquare[] allowedSquares, boolean doSelect) {
        for (ChessboardSquare square : allowedSquares) {
            if (doSelect) square.select();
            else square.unselect();
            redrawBoard(square);
        }
    }
    private ChessboardSquare getCurrentSquare(ChessboardSquare[][] boardArray, int x, int y) {
        for (ChessboardSquare[] line : boardArray) {
            for (ChessboardSquare square : line) {
                if (square.contains(x, y)) return square;
            }
        }
        return null;
    }

    public void makeMove() {

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
