package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Pair;

import java.util.Set;

public class Queen extends Piece{
    public Queen(boolean color) {
        super(color);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllowedCells(int x, int y) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
