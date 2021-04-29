package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        return new HashSet<>();
    }


    @Override
    public String getName() {
        return "Pawn";
    }
}
