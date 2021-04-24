package org.spbstu.ysa.chessonline.model;
//test
import org.spbstu.ysa.chessonline.model.pieces.Piece;

public class Player {
    private boolean color;

    public void captureChess(int x, int y){
        Piece capturePiece = Board.getData()[x][y].takePiece();
    }
}
