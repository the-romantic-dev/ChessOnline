package org.spbstu.ysa.chessonline.model.pieces;

import java.util.Set;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllowedCells(int x, int y) {

        return null;
    }

    @Override
    public String getName() {
        return "bishop";
    }
}
