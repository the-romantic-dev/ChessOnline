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
import org.w3c.dom.Text;

public class PromotingDialog {
    private SpriteBatch batch;
    private AvailablePiece[] availablePieces;
    private boolean isWhite;
    private Pixmap overallPixmap;

    private int startX;
    private int startY;


    private class AvailablePiece extends Rectangle {
        Piece piece;
        Pixmap piecePM;

        public AvailablePiece(PiecesEnum piecesEnum) {
            choosePiece(piecesEnum);
            choosePiecePM(piecesEnum);
            width = 128;
            height = 128;


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

    public PromotingDialog(SpriteBatch batch, int startX, int startY) {
        this.batch = batch;
        this.startX = startX;
        this.startY = startY;
        this.availablePieces = addAvailablePieces();
        makeOverallPixmap();

    }

    public void draw() {
        Texture texture = new Texture(overallPixmap);
        batch.draw(texture, startX, startY);
    }

    private void makeOverallPixmap() {
        overallPixmap = new Pixmap(128 * 4, 128, Pixmap.Format.RGBA8888);
        overallPixmap.setColor(0, 1, 0, 1);
        overallPixmap.fill();
        int k = 0;
        for (AvailablePiece piece : availablePieces) {
            overallPixmap.drawPixmap(piece.piecePM, k * 128, 0);
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
        result[0] = new AvailablePiece(PiecesEnum.QUEEN);
        result[1] = new AvailablePiece(PiecesEnum.ROOK);
        result[2] = new AvailablePiece(PiecesEnum.BISHOP);
        result[3] = new AvailablePiece(PiecesEnum.KNIGHT);
        return result;
    }

    enum PiecesEnum {
        BISHOP,
        QUEEN,
        KNIGHT,
        ROOK
    }


}
