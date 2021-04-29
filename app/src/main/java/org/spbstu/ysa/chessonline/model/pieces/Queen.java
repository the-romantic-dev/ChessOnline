package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece{
    public Queen(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        Set<Cell> res = new HashSet<>();
        res.addAll(new Rook(this.isWhite()).getAllowedCells(x,y,board));
        res.addAll(new Bishop(this.isWhite()).getAllowedCells(x,y,board));
        return res;
    }

    @Override
    public String getName() {
        return "Queen";
    }
}
