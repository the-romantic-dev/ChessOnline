package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    private final int BORDER_COORDINATE = this.isWhite() ? 7 : 1;
    private boolean isMoved = false;

    public Pawn(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        Set<Cell> res = new HashSet<>();
        int i = this.isWhite() ? 1 : -1;
        int curY = y + i;
        if (curY >= 0 && curY < 8 && board[curY][x].getPiece() == null){
            res.add(new Cell(x, curY));
            if (!this.isMoved && board[curY + i][x].getPiece() == null)  res.add(new Cell(x, curY + i));
        }


        return res;
    }


    @Override
    public String getName() {
        return "Pawn";
    }
}
