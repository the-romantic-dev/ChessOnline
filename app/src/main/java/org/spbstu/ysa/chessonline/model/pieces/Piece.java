package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;
import org.spbstu.ysa.chessonline.model.Pair;

import java.util.Set;
public abstract class Piece {
    private boolean color;

    public Piece(boolean color){
        this.color = color;
    }

    public boolean getColor(){
        return this.color;
    }

    public abstract Set<Pair<Integer, Integer>> getAllowedCells(int x, int y, Cell[][] board);

    public abstract String getName();

    @Override
    public String toString() {
        String pieceColor =  color ? "white" : "black";
        return pieceColor + "_" + getName();
    }
}
