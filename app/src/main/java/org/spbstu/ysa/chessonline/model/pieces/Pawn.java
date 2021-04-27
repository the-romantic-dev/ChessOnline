package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.Set;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }
}
