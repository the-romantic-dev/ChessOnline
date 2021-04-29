package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {

        int curX = x + 1;
        int curY = y + 1;
        Set<Cell> res = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                while (curX >= 0 && curX < 8 && curY >=0 && curY < 8 ){
                    Piece curPiece = board[curY][curX].getPiece();
                    if (curPiece == null) res.add(new Cell(curX, curY));
                    else {
                        if (curPiece.isWhite() != this.isWhite()) res.add(new Cell(curX, curY));
                        break;
                    }
                    curX = (int) (curX + 1 * Math.pow(-1.0, i));
                    curY = (int) (curY + 1 * Math.pow(-1.0, j));
                }
            }

        }

        return res;
    }


    @Override
    public String getName() {
        return "Bishop";
    }
}
