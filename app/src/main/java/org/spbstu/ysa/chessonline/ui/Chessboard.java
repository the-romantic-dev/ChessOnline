package org.spbstu.ysa.chessonline.ui;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.Player;
import org.spbstu.ysa.chessonline.model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Графическая обертка для Player
public class Chessboard {
    private int startX;
    private int startY;
    private Player player;
    private ChessboardSquare[][] squaresArray;
    private Pixmap overallPixmap;
    private SpriteBatch batch;
    private Texture overallTexture;
    boolean isOnline;

    private ChessboardSquare currentSquare;
    private ChessboardSquare lastSquare;
    private ChessboardSquare[] currentAllowedSquares;




    private Map<Pieces, Pixmap> whitePiecesPM;
    private Map<Pieces, Pixmap> blackPiecesPM;

    public Chessboard(Player player, SpriteBatch batch, int startX, int startY, boolean isOnline) {
        this.batch = batch;
        this.player = player;
        this.startX = startX;
        this.startY = startY;
        whitePiecesPM = new HashMap<>();
        blackPiecesPM = new HashMap<>();
        this.isOnline = isOnline;

        initPiecesPixmaps();

        squaresArray = convertCellsToSquares();

        overallPixmap = makeStartPixmap();
    }

    public void draw() {
        if (overallTexture != null) overallTexture.dispose();
        overallTexture = new Texture(overallPixmap);
        batch.draw(overallTexture, startX, startY);
    }

    public void redrawSquare(ChessboardSquare square) {
        int x;
        int y;
        if (!isOnline) {
            x = square.getCell().getX();
            y = square.getCell().getY();
        } else {
            if (player.isWhite()) {
                x = square.getCell().getX();
                y = square.getCell().getY();

            } else {
                x = 7 - square.getCell().getX();
                y = 7 - square.getCell().getY();
            }
        }


        Piece piece = square.getCell().getPiece();
        if (piece != null) {
            Pixmap piecePM = getPiecePixmap(piece.getName(), piece.isWhite());
            square.setPiece(piece, piecePM);
        } else {
            square.setPiece(piece, null);
        }
        overallPixmap.drawPixmap(square.getPixmap(), x * ChessboardSquare.sideLength, (7 - y) * ChessboardSquare.sideLength);
    }

    private void selectPieceAndMoves() {
        currentAllowedSquares = getAllowedSquares(currentSquare);
        currentSquare.select();
        player.setCurrentCell(currentSquare.getCell());
        redrawSquare(currentSquare);
        selectAllowedSquares(currentAllowedSquares);
        lastSquare = currentSquare;
    }


    public ChessboardSquare setCurrentSquare(int x, int y) {
        for (ChessboardSquare[] line : squaresArray) {
            for (ChessboardSquare square : line) {
                if (square.contains(x, y)) {
                    currentSquare = square;
                    return square;
                }
            }
        }
        currentSquare = null;
        return null;
    }

    public void makeMove() {
        player.setTurn(false);
        Cell changedCell = player.putPiece(currentSquare.getCell());
        ChessboardSquare changedSquare = squaresArray[changedCell.getY()][changedCell.getX()];
        redrawSquare(lastSquare);
        redrawSquare(currentSquare);
        if (!changedSquare.equals(currentSquare)) redrawSquare(changedSquare);
        if (!isOnline) {
            player.setTurn(true);
            player.setColor(!player.isWhite());
        }


    }


    public void tap() {
        if (!player.getTurn()) return;
        boolean isSelected = false;
        if (currentSquare != null) isSelected = currentSquare.isSelected();
        unselectAll();
        if (currentSquare != null) {
            Log.d("currentSquare", currentSquare.toString());
            if (currentSquare.getCell().getPiece() != null && isItPLayersPiece()) {
                if (!isSelected) {
                    selectPieceAndMoves();
                }
            } else {
                if (isSelected) {
                    makeMove();
                }
            }
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

    private boolean isItPLayersPiece() {
        return currentSquare.getCell().getPiece().isWhite() == player.isWhite();
    }

    private void unselectAll() {
        for (ChessboardSquare[] line : squaresArray) {
            for (ChessboardSquare square : line) {
                if (square.isSelected()) {
                    square.unselect();
                    redrawSquare(square);
                }
            }
        }
    }

    private ChessboardSquare[] getAllowedSquares(ChessboardSquare square) {
        if (square == null) return new ChessboardSquare[0];
        Set<Cell> cells = player.capturePiece(square.getCell());
        ChessboardSquare[] result = new ChessboardSquare[cells.size()];
        int k = 0;
        for (Cell cell : cells) {
            if (!isOnline) {
                result[k] = squaresArray[cell.getY()][cell.getX()];
            } else {
                if (player.isWhite()) {
                    result[k] = squaresArray[cell.getY()][cell.getX()];
                } else {
                    result[k] = squaresArray[7 - cell.getY()][7 - cell.getX()];
                }
            }


            k++;
        }
        return result;
    }

    private void selectAllowedSquares(ChessboardSquare[] allowedSquares) {
        for (ChessboardSquare square : allowedSquares) {
            square.select();
            redrawSquare(square);
        }
    }

    private ChessboardSquare[][] convertCellsToSquares() {
        int nextX;
        int nextY = startY - ChessboardSquare.sideLength;
        ChessboardSquare[][] result = new ChessboardSquare[8][8];
        Cell addedCell;
        for (int i = 0; i < 8; i++) {
            nextX = startX;
            nextY += ChessboardSquare.sideLength;
            for (int j = 0; j < 8; j++) {
                if (!isOnline) {
                    addedCell = player.getBoard().getData()[i][j];
                } else {
                    if (player.isWhite()) {
                        addedCell = player.getBoard().getData()[i][j];
                    } else {
                        addedCell = player.getBoard().getData()[7 - i][7 - j];
                    }
                }

                if (addedCell.getPiece() != null)
                    result[i][j] = new ChessboardSquare(addedCell, nextX, nextY, getPiecePixmap(addedCell.getPiece().getName(), addedCell.getPiece().isWhite()));
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
                pixmap.drawPixmap(squaresArray[i][j].getPixmap(), j * ChessboardSquare.sideLength, (7 - i) * ChessboardSquare.sideLength);
            }
        }
        return pixmap;
    }

    private Pixmap getPiecePixmap(String name, boolean color) {
        Map<Pieces, Pixmap> pixmaps;
        if (color) pixmaps = whitePiecesPM;
        else pixmaps = blackPiecesPM;
        switch (name) {
            case "King":
                return pixmaps.get(Pieces.KING);
            case "Knight":
                return pixmaps.get(Pieces.KNIGHT);
            case "Rook":
                return pixmaps.get(Pieces.ROOK);
            case "Queen":
                return pixmaps.get(Pieces.QUEEN);
            case "Pawn":
                return pixmaps.get(Pieces.PAWN);
            case "Bishop":
                return pixmaps.get(Pieces.BISHOP);
        }
        return null;
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

    public void setCurrentSquare(ChessboardSquare currentSquare) {
        this.currentSquare = currentSquare;
    }

    public void setLastSquare(ChessboardSquare lastSquare) {
        this.lastSquare = lastSquare;
    }

    public ChessboardSquare getSquare(int x, int y) {
        return squaresArray[y][x];
    }
}
