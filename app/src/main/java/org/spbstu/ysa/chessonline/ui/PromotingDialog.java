package org.spbstu.ysa.chessonline.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import org.spbstu.ysa.chessonline.model.pieces.Bishop;
import org.spbstu.ysa.chessonline.model.pieces.Knight;
import org.spbstu.ysa.chessonline.model.pieces.Piece;
import org.spbstu.ysa.chessonline.model.pieces.Queen;
import org.spbstu.ysa.chessonline.model.pieces.Rook;

public class PromotingDialog {
    public static final int cellSize = 128;

    private SpriteBatch batch;
    private AvailablePiece[] availablePieces;
    private boolean isWhite;
    private Pixmap overallPixmap;

    private int startX;
    private int startY;


    private class AvailablePiece extends Rectangle {
        Piece piece;
        Pixmap piecePM;

        public AvailablePiece(PiecesEnum piecesEnum, float x, float y) {
            choosePiece(piecesEnum);
            choosePiecePM(piecesEnum);
            width = cellSize;
            height = cellSize;
            this.x = x;
            this.y = y;

        }

        private void choosePiece(PiecesEnum piecesEnum) {
            switch (piecesEnum) {
                case ROOK:
                    piece = new Rook(isWhite);
                    break;
                case QUEEN:
                    piece = new Queen(isWhite);
                    break;
                case BISHOP:
                    piece = new Bishop(isWhite);
                    break;
                case KNIGHT:
                    piece = new Knight(isWhite);
                    break;
            }
        }

        private void choosePiecePM(PiecesEnum piecesEnum) {
            String colorPart = "";
            String piecePart = "";
            if (isWhite) colorPart = "white";
            else colorPart = "black";
            switch (piecesEnum) {
                case QUEEN:
                    piecePart = "queen";
                    break;
                case ROOK:
                    piecePart = "rook";
                    break;
                case KNIGHT:
                    piecePart = "knight";
                    break;
                case BISHOP:
                    piecePart = "bishop";
                    break;
            }
            piecePM = new Pixmap(Gdx.files.internal(colorPart + "_" + piecePart + ".png"));
        }
    }

    public PromotingDialog(SpriteBatch batch, int startX, int startY, boolean isWhite) {
        this.batch = batch;
        this.startX = startX;
        this.startY = startY;
        this.isWhite = isWhite;

    }

    public void draw() {
        this.availablePieces = addAvailablePieces();
        makeOverallPixmap();
        Texture texture = new Texture(overallPixmap);
        batch.draw(texture, startX, startY);
    }

    private void makeOverallPixmap() {
        overallPixmap = new Pixmap(cellSize * 4, cellSize, Pixmap.Format.RGBA8888);
        overallPixmap.setColor(0, 1, 0, 1);
        overallPixmap.fill();
        int k = 0;
        for (AvailablePiece piece : availablePieces) {
            overallPixmap.drawPixmap(piece.piecePM, k * cellSize, 0);
            k++;
        }
    }

    public Piece getPiece(int x, int y) {
        for (AvailablePiece piece : availablePieces) {
            if (piece.contains((float) x, (float) y)) {
                return piece.piece;
            }
        }
        return null;
    }

    private AvailablePiece[] addAvailablePieces() {
        AvailablePiece[] result = new AvailablePiece[4];
        result[0] = new AvailablePiece(PiecesEnum.QUEEN, startX, startY);
        result[1] = new AvailablePiece(PiecesEnum.ROOK, startX + cellSize, startY);
        result[2] = new AvailablePiece(PiecesEnum.BISHOP, startX + cellSize * 2, startY);
        result[3] = new AvailablePiece(PiecesEnum.KNIGHT, startX + cellSize * 3, startY);
        return result;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    enum PiecesEnum {
        BISHOP,
        QUEEN,
        KNIGHT,
        ROOK
    }

    public void dispose() {
        if (overallPixmap != null) overallPixmap.dispose();
        if (availablePieces != null && availablePieces.length != 0) {
            for (AvailablePiece piece :
                    availablePieces) {
                piece.piecePM.dispose();
            }
        }
    }


}
