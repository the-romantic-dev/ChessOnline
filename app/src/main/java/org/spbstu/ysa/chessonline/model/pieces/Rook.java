package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {

        Set<Cell> res = new HashSet<>();

        for (int i = x + 1; i < 8; i++) {
            Piece curPiece = board[i][y].getPiece();
            if (curPiece == null) res.add(new Cell(i, y)) ;
            else {
                if (curPiece.getColor() != this.getColor()) res.add(new Cell(i, y));
                break;
            }
        }


        for (int i = x - 1; i >= 0; i--) {
            Piece curPiece = board[i][y].getPiece();
            if (curPiece == null) res.add(new Cell(i, y)) ;
            else {
                if (curPiece.getColor() != this.getColor()) res.add(new Cell(i, y));
                break;
            }
        }


        for (int i = y + 1; i < 8; i++) {
            Piece curPiece = board[x][i].getPiece();
            if (curPiece == null) res.add(new Cell(x, i));
            else {
                if (curPiece.getColor() != this.getColor()) res.add(new Cell(x, i));
                break;
            }
        }


        for (int i = y - 1; i >= 0; i--) {
            Piece curPiece = board[x][i].getPiece();
            if (curPiece == null) res.add(new Cell(x, i)) ;
            else {
                if (curPiece.getColor() != this.getColor()) res.add(new Cell(x, i));
                break;
            }
        }


        return res;
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
